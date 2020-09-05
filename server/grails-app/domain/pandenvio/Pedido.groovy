package pandenvio

class Pedido {

    static transients = [ "estado" ] // No persiste el atributo estado

    Date fecha
    CuponDescuento cuponDeDescuento
    Cliente cliente
    EstadoPedido estado
    String nombreEstado
    ModalidadEntrega modalidadEntrega
    Restaurant restaurant

    static hasMany = [items: Item]

    static constraints = {
        cliente nullable: false
        modalidadEntrega nullable: false
        restaurant nullable: false
    }

    Pedido(cliente, modalidadEntrega, restaurant) {
        this.fecha = new Date()
        this.cliente = cliente
        this.cuponDeDescuento = new CuponDescuentoNulo(fecha: fecha, activo: true, codigo: 'NULO')
        this.setEstado(new EstadoRecibido())
        this.modalidadEntrega = modalidadEntrega
        this.items = []
        this.restaurant = restaurant
    }

    void agregar(Producto producto, Integer cantidad) {
        addToItems(new Item(producto, cantidad, this))
    }

    BigDecimal calcularPrecio() {
        return this.modalidadEntrega.calcularPrecioCon(cuponDeDescuento, items)
    }

    void setEstado(EstadoPedido nuevoEstado){
        this.estado = nuevoEstado
        setNombreEstado(nuevoEstado.nombre)
    }
    void siguienteEstado() {
        if (items.size() <= 0) {
            throw new PedidoNoTieneItemsException("El pedido no tiene items no se puede cambiar estado")
        }
        setEstado(this.estado.siguienteEstado(modalidadEntrega))
        this.estado.iniciarPara(this)
    }

    void cancelar() {
        setEstado(this.estado.cancelar())
    }

    Boolean tieneRepartidor(){
        modalidadEntrega.hayRepartidor()
    }

    void asignarA(Repartidor repartidor){
        this.modalidadEntrega.asignarRepartidor(repartidor)
    }

    // TODO: ver como mejorar ésto y si es necesario, es una asignación manual del estado al cargar el pedido
    def afterLoad() {
        setEstado(FabricaEstados.obtenerEstado(this.nombreEstado))
    }
}

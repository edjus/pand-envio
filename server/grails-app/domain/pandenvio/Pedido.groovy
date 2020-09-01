package pandenvio

class Pedido {

    static transients = [ "estado" ] // No persiste el atributo estado

    Date fecha
    CuponDescuento cuponDeDescuento
    Cliente cliente
    EstadoPedido estado
    String nombreEstado
    ModalidadEntrega modalidadEntrega
    static hasMany = [items: Item]

    static constraints = {
        cliente nullable: false
        modalidadEntrega nullable: false
    }

    Pedido(cliente, modalidadEntrega) {
        this.fecha = new Date()
        this.cliente = cliente
        this.cuponDeDescuento = new CuponDescuentoNulo(fecha: fecha, activo: true, codigo: 'NULO')
        this.cambiarEstado(new EstadoRecibido())
        this.modalidadEntrega = modalidadEntrega
        this.items = []
    }

    void agregar(Producto producto, Integer cantidad) {
        addToItems(new Item(producto, cantidad, this))
    }

    BigDecimal calcularPrecio() {
        return this.modalidadEntrega.calcularPrecioCon(cuponDeDescuento, items)
    }

    void cambiarEstado(EstadoPedido nuevoEstado) {
        setEstado(nuevoEstado)
        setNombreEstado(nuevoEstado.nombre)
    }

    void siguienteEstado() {
        this.cambiarEstado(this.estado.siguienteEstado(modalidadEntrega))
    }

    void cancelar() {
        this.cambiarEstado(this.estado.cancelar())
    }

    // TODO: ver como mejorar ésto y si es necesario, es una asignación manual del estado al cargar el pedido
    def afterLoad() {
        this.setEstado(FabricaEstados.obtenerEstado(this.nombreEstado))
    }
}

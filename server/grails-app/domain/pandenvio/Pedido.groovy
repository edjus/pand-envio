package pandenvio

class Pedido {

    static transients = [ "estado" ] // No persiste el atributo estado

    Date fecha
    CuponDescuento cuponDeDescuento
    Cliente cliente
    EstadoPedido estado
    String nombreEstado
    List<Item> items = []

    static constraints = {
        cliente nullable: false
    }

    Pedido(cliente){
        this.fecha = new Date()
        this.cliente = cliente
        this.cuponDeDescuento = new CuponDescuentoNulo(fecha: fecha, activo: true, codigo: 'NULO')
        this.setEstado(new EstadoRecibido())
    }

    void agregar(Producto producto, Integer cantidad){
        items.add(new Item(producto, cantidad))
    }

    BigDecimal calcularPrecio(){
        BigDecimal precioTotalParcial = items.sum(0) { it.calcularPrecio() }
        Boolean aplicaDescuento = items.inject(true) {resultado , item -> resultado && item.admiteA(cuponDeDescuento)}

        aplicaDescuento ? cuponDeDescuento.aplicarDescuento(precioTotalParcial) : precioTotalParcial
    }

    // TODO: ver como mejorar ésto, es una asignación manual del estado al cargar el pedido
    void setEstado(EstadoPedido nuevoEstado){
        this.estado = nuevoEstado
        this.nombreEstado = nuevoEstado.nombre
    }
}

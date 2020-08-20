package pandenvio

class Pedido {

    Date fecha
    CuponDescuento cuponDeDescuento
    Cliente cliente
    EstadoPedido estado
    List<Item> items = []

    static constraints = {
        cliente nullable: false
    }

    Pedido(cliente){
        this.fecha = new Date()
        this.cliente = cliente
        this.cuponDeDescuento = new CuponDescuentoNulo(fecha: fecha, activo: true, codigo: 'NULO')
        this.estado = new EstadoRecibido()
    }

    void agregar(Producto producto, Integer cantidad){
        items.add(new Item(producto, cantidad))
    }

    BigDecimal calcularPrecio(){
        BigDecimal precioTotalParcial = items.sum(0) { it.calcularPrecio() }
        Boolean aplicaDescuento = items.inject(true) {resultado , item -> resultado && item.admiteA(cuponDeDescuento)}

        aplicaDescuento ? cuponDeDescuento.aplicarDescuento(precioTotalParcial) : precioTotalParcial
    }
}

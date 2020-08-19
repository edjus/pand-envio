package pandenvio

class Pedido {

    Date fecha
    CuponDescuento cuponDeDescuento
    Cliente cliente
    List<Item> items

    static constraints = {
        cliente nullable: false
    }

    Pedido(cliente){
        this.fecha = new Date()
        this.cliente = cliente
        this.cuponDeDescuento = new CuponDescuentoNulo(fecha: fecha, activo: true, codigo: 'NULO')
    }

    void agregar(Producto producto, Integer cantidad){
        items.add(new Item(producto, cantidad))
    }

    BigDecimal calcularPrecio(){
        BigDecimal precioTotalParcial = items.sum() { it.getPrecio() }
        Boolean aplicaDescuento = items.inject(true) {resultado , item -> resultado && item.admiteA(cuponDeDescuento)}
        
        aplicaDescuento ? cuponDeDescuento.aplicarDescuento(precioTotalParcial) : precioTotalParcial
    }
}

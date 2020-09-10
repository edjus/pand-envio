package pandenvio

class Item {
    Producto producto
    Integer cantidad

    static belongsTo = [pedido: Pedido]

    static constraints = {
        producto nullable: false
        cantidad nullable: false, min: 1
    }

    static mapping = {
        producto lazy: false
    }

    Item(producto, cantidad, pedido) {
        this.producto = producto
        this.cantidad = cantidad
        this.pedido = pedido
    }

    BigDecimal calcularPrecio() {
        producto.precio * cantidad
    }

    Boolean admiteA(CuponDescuento descuento) {
        producto.admiteA(descuento)
    }
}

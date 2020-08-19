package pandenvio

class Item {
    Producto producto
    Integer cantidad
    BigDecimal precio

    static constraints = {
        producto nullable: false
        cantidad nullable: false, min: 1
    }

    Item(producto, cantidad){
        this.producto = producto
        this.cantidad = cantidad
    }

    BigDecimal calcularPrecio(){
        producto.getPrecio() * cantidad
    }

    Boolean admiteA(CuponDescuento descuento){
        producto.admiteA(descuento)
    }
}

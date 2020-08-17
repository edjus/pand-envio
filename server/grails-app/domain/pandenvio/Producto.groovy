package pandenvio

abstract class Producto {

    static belongsTo = [restaurant: Restaurant]

    BigDecimal getPrecio(){
        return precio;
    }

    String nombre
    BigDecimal precio

    abstract boolean admiteA(CuponDescuento descuento)
    abstract boolean admiteA(CuponDescuentoNulo descuento)
    abstract boolean admiteA(CuponDescuentoPorcentual descuento)

    static constraints = {
    }
}
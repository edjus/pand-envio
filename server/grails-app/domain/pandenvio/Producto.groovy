package pandenvio

abstract class Producto {

    String nombre
    BigDecimal precio

    static belongsTo = [restaurant: Restaurant]

    abstract boolean admiteA(CuponDescuento descuento)
    abstract boolean admiteA(CuponDescuentoNulo descuento)
    abstract boolean admiteA(CuponDescuentoPorcentual descuento)

    static constraints = {
    }
}
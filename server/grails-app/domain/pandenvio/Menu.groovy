package pandenvio

class Menu implements Producto {
    String nombre
    BigDecimal precio

    static hasMany = [productos: Plato]
    static belongsTo = [restaurant: Restaurant]

    BigDecimal getPrecio(){
        return precio;
    }

    boolean admiteA(CuponDescuento descuento) {
        return descuento.permitirEn(this)
    }

    boolean admiteA(CuponDescuentoNulo descuento) {
        return true
    }

    boolean admiteA(CuponDescuentoPorcentual descuento) {
        return false
    }
}

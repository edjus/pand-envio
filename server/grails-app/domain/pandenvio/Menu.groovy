package pandenvio

class Menu extends Producto {

    static hasMany = [platos: Plato,]

    Set<Plato> platos = []

    boolean admiteA(CuponDescuento descuento) {
        descuento.permitirEn(this)
    }

    boolean admiteA(CuponDescuentoNulo descuento) {
        true
    }

    boolean admiteA(CuponDescuentoPorcentual descuento) {
        false
    }
}

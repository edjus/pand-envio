package pandenvio

class Menu extends Producto {

    // TODO: Ver por qué no se guarda la relación en la base de datos
    static hasMany = [platos: Plato]

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

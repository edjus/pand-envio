package pandenvio

enum CategoriaPlato {
    ENTRADA, BEBIDA, PLATO, POSTRE
}


class Plato extends Producto {

    CategoriaPlato categoria;
    String descripcion;

    static belongsTo = Menu
    static hasMany = [menues: Menu,]

    Set<Menu> menues = []

    static constraints = {
        descripcion nullable: true
    }

    boolean admiteA(CuponDescuento descuento) {
        descuento.permitirEn(this)
    }

    boolean admiteA(CuponDescuentoNulo descuento) {
        true
    }

    boolean admiteA(CuponDescuentoPorcentual descuento) {
        true
    }
}
package pandenvio

enum CategoriaPlato {
    ENTRADA, BEBIDA, PLATO, POSTRE
}

import grails.rest.Resource

@Resource(uri = '/plato')
class Plato extends Producto {

    CategoriaPlato categoria;
    String descripcion;

    boolean admiteA(CuponDescuento descuento) {
        return descuento.permitirEn(this)
    }

    boolean admiteA(CuponDescuentoNulo descuento) {
        return true
    }

    boolean admiteA(CuponDescuentoPorcentual descuento) {
        return true
    }
}
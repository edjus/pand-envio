package pandenvio
import grails.rest.Resource

enum CategoriaPlato {
    ENTRADA, BEBIDA, PLATO, POSTRE
}


@Resource(uri = '/plato')
class Plato extends Producto {

    CategoriaPlato categoria;
    String descripcion;

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
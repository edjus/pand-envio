package pandenvio

enum CategoriaPlato {
    ENTRADA, BEBIDA, PLATO, POSTRE
}

class Plato implements Producto {
    String nombre;
    BigDecimal precio;
    CategoriaPlato categoria;
    String descripcion;

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
        return true
    }
}

package pandenvio

abstract class ModalidadEntrega {

    static constraints = {
    }

    abstract  calcularPrecioCon(CuponDescuento cuponDescuento, List<Item> items)
}

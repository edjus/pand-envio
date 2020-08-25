package pandenvio

abstract class ModalidadEntrega {

    static constraints = {
    }

    abstract BigDecimal calcularPrecioCon(CuponDescuento cuponDeDescuento, List<Item> items)
}

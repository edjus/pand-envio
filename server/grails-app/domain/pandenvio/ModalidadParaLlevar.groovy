package pandenvio

class ModalidadParaLlevar  extends ModalidadEntrega{
    Puntuacion puntuacion
    Repartidor repartidor

    static constraints = {
        puntuacion nullable: true
        repartidor nullable: true
    }

    @Override
    BigDecimal calcularPrecioCon(CuponDescuento cuponDeDescuento, List<Item> items) {
        BigDecimal precioTotalParcial = items.sum(0) { it.calcularPrecio() }
        Boolean aplicaDescuento = items.inject(true) {resultado , item -> resultado && item.admiteA(cuponDeDescuento)}

        aplicaDescuento ? cuponDeDescuento.aplicarDescuento(precioTotalParcial) : precioTotalParcial
    }
}

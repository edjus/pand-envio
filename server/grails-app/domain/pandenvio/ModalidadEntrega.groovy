package pandenvio

abstract class ModalidadEntrega {

    static constraints = {
    }

    BigDecimal calcularPrecioCon(CuponDescuento cuponDeDescuento, List<Item> items){
        BigDecimal precioTotalBase = items.sum(0) { it.calcularPrecio() }
        BigDecimal precioTotalParcial = this.aplicarAdicionales(precioTotalBase)

        Boolean aplicaDescuento = items.inject(true) {resultado , item -> resultado && item.admiteA(cuponDeDescuento)}

        aplicaDescuento ? cuponDeDescuento.aplicarDescuento(precioTotalParcial) : precioTotalParcial
    }
    abstract BigDecimal aplicarAdicionales(BigDecimal valorBase)
}

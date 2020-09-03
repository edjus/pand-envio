package pandenvio

abstract class ModalidadEntrega {

    String nombre

    BigDecimal calcularPrecioCon(CuponDescuento cuponDeDescuento, Set<Item> items) {
        BigDecimal precioTotalBase = items.sum(0) { it.calcularPrecio() }
        BigDecimal precioTotalParcial = this.aplicarAdicionales(precioTotalBase)

        Boolean aplicaDescuento = items.inject(true) { resultado , item -> resultado && item.admiteA(cuponDeDescuento) }

        aplicaDescuento ? cuponDeDescuento.aplicarDescuento(precioTotalParcial) : precioTotalParcial
    }

    abstract BigDecimal aplicarAdicionales(BigDecimal valorBase)

    abstract EstadoPedido siguienteEstadoListo()

    abstract boolean hayRepartidor()

    abstract void asignarRepartidor(Repartidor repartidor)
}

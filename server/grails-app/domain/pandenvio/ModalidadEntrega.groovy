package pandenvio

abstract class ModalidadEntrega {

    String nombre

    BigDecimal calcularPrecioCon(Pedido pedido, CuponDescuento cuponDeDescuento) {
        BigDecimal precioTotalBase = pedido.items.sum(0) { it.calcularPrecio() }
        BigDecimal precioTotalParcial = this.aplicarAdicionales(precioTotalBase)

        Boolean aplicaDescuento = pedido.items.inject(true) { resultado , item -> resultado && item.admiteA(cuponDeDescuento) }

        aplicaDescuento ? cuponDeDescuento.aplicarDescuento(precioTotalParcial, pedido) : precioTotalParcial
    }

    abstract BigDecimal aplicarAdicionales(BigDecimal valorBase)

    abstract EstadoPedido siguienteEstadoListo()

    abstract boolean hayRepartidor()

    abstract void asignarRepartidor(Repartidor repartidor)
}

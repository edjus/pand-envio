package pandenvio

class ModalidadParaRetirar extends ModalidadEntrega {

    static constraints = {
    }

    @Override
    BigDecimal aplicarAdicionales(BigDecimal valorBase) {
        valorBase
    }

    @Override
    EstadoPedido siguienteEstadoListo() {
        new EstadoEntregado()
    }

    @Override
    boolean hayRepartidor() {
        false
    }
}

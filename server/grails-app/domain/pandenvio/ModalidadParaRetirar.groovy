package pandenvio

class ModalidadParaRetirar extends ModalidadEntrega {

    static constraints = {
    }

    @Override
    BigDecimal aplicarAdicionales(BigDecimal valorBase) {
        // TODO: Aplicar adicionales si corresponde
        valorBase
    }
}

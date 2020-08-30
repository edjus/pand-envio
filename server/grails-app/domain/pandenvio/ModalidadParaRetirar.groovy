package pandenvio

class ModalidadParaRetirar extends ModalidadEntrega {

    ModalidadParaRetirar(){
        this.nombre = "para_retirar"
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

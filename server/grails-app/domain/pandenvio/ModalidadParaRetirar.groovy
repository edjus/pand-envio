package pandenvio

class ModalidadParaRetirar extends ModalidadEntrega {

    ModalidadParaRetirar(){
        this.nombre = "para_retirar"
    }

    @Override
    BigDecimal aplicarAdicionales(BigDecimal valorBase, Pedido pedido) {
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

    @Override
    ModalidadParaLlevar cambiarModalidad() {
        new ModalidadParaLlevar()
    }

    @Override
    void asignarRepartidor(Repartidor repartidor) {
    }

    @Override
    void entregarPedido() {
    }

    @Override
    EstadoPedido noEntregar(EstadoPedido estadoPedido) {
        throw new NoSePuedeMarcarComoNoEntregadoException("El pedido no puede marcarse como no entregado si es para retirar")
    }
}

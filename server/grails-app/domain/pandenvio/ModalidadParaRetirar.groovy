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

    @Override
    void agregarPuntuacion(Integer integer) {
        throw new CalificacionException('El servicio del pedido no puede puntuarse por tener una modalidad de retiro')
    }

    @Override
    Integer obtenerEstrellas() {
        throw  new CalificacionException('El tipo de pedido no se puede calificar por ende no tiene estrellas')
    }
}

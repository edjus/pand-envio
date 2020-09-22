package pandenvio

abstract class EstadoPedido {
    String nombre

    static constraints = {
        nombre nullable: false, blank: false
    }

    abstract EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega)

    abstract EstadoPedido cancelar()

    def iniciarPara(Pedido pedido) {}

    boolean puedeActualizarProductos() { false }
}
class EstadoEnArmado extends EstadoPedido {
    EstadoEnArmado() {
        this.nombre = 'en_armado'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        new EstadoRecibido()
    }

    @Override
    EstadoPedido cancelar() {
        new EstadoCancelado()
    }

    @Override
    boolean puedeActualizarProductos() { true }
}

class EstadoRecibido extends EstadoPedido {
    EstadoRecibido() {
        this.nombre = 'recibido'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        new EstadoEnPreparacion()
    }

    @Override
    EstadoPedido cancelar() {
        new EstadoCancelado()
    }
}

class EstadoEnPreparacion extends EstadoPedido {
    EstadoEnPreparacion() {
        this.nombre = 'en_preparacion'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        new EstadoListo()
    }

    @Override
    EstadoPedido cancelar() {
        new EstadoCancelado()
    }
}

class EstadoListo extends EstadoPedido {
    EstadoListo() {
        this.nombre = 'listo'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        modalidadEntrega.siguienteEstadoListo()
    }

    @Override
    EstadoPedido cancelar() {
        throw new PedidoNoSePuedeCancelarException("El pedido tiene estado ${this.nombre}, no se puede cancelar")
    }
}

class EstadoEnEntrega extends EstadoPedido {
    EstadoEnEntrega() {
        this.nombre = 'en_entrega'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        new EstadoEntregado()
    }

    @Override
    EstadoPedido cancelar() {
        throw new PedidoNoSePuedeCancelarException("El pedido tiene estado ${this.nombre}, no se puede cancelar")
    }

    @Override
    def iniciarPara(Pedido pedido) {
        AsignadorRepartidor.instance.asignarPara(pedido)
        pedido.estado = pedido.tieneRepartidor() ? this : new EstadoEnEspera()
    }
}

class EstadoEnEspera extends EstadoPedido {
    EstadoEnEspera() {
        this.nombre = 'en_espera'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        new EstadoEnEntrega()
    }

    @Override
    EstadoPedido cancelar() {
        throw new PedidoNoSePuedeCancelarException("El pedido tiene estado ${this.nombre}, no se puede cancelar")
    }
}

class EstadoEntregado extends EstadoPedido {
    EstadoEntregado() {
        this.nombre = 'entregado'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        throw new NoHaySiguienteEstadoException("El estado ${this.nombre} no tiene uno siguiente no se puede cambiar")
    }

    @Override
    EstadoPedido cancelar() {
        throw new PedidoNoSePuedeCancelarException("El pedido tiene estado ${this.nombre}, no se puede cancelar")
    }

    @Override
    def iniciarPara(Pedido pedido) {
        pedido.modalidadEntrega.entregarPedido()
    }

}

class EstadoNoEntregado extends EstadoPedido {
    EstadoNoEntregado() {
        this.nombre = 'no_entregado'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        throw new NoHaySiguienteEstadoException("El estado ${this.nombre} no tiene uno siguiente no se puede cambiar")
    }

    @Override
    EstadoPedido cancelar() {
        throw new PedidoNoSePuedeCancelarException("El pedido tiene estado ${this.nombre}, no se puede cancelar")
    }
}

class EstadoCancelado extends EstadoPedido {
    EstadoCancelado() {
        this.nombre = 'cancelado'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        throw new NoHaySiguienteEstadoException("El estado ${this.nombre} no tiene uno siguiente no se puede cambiar")
    }

    @Override
    EstadoPedido cancelar() {
        new EstadoCancelado()
    }
}
package pandenvio

abstract class EstadoPedido {
    String nombre

    static constraints = {
        nombre nullable: false, blank: false
    }

    abstract EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega)
}

class EstadoRecibido extends EstadoPedido {
    EstadoRecibido(){
        this.nombre = 'recibido'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        new EstadoEnPreparacion()
    }
}

class EstadoEnPreparacion extends EstadoPedido {
    EstadoEnPreparacion(){
        this.nombre = 'en_preparacion'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        new EstadoListo()
    }
}

class EstadoListo extends EstadoPedido {
    EstadoListo(){
        this.nombre = 'listo'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        modalidadEntrega.siguienteEstadoListo()
    }
}

class EstadoEnEntrega extends EstadoPedido {
    EstadoEnEntrega(){
        this.nombre = 'en_entrega'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        new EstadoEntregado()
    }
}

class EstadoEnEspera extends EstadoPedido {
    EstadoEnEspera(){
        this.nombre = 'en_espera'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        modalidadEntrega.hayRepartidor() ? new EstadoEnEntrega() : this;
    }
}

class EstadoEntregado extends EstadoPedido {
    EstadoEntregado(){
        this.nombre = 'entregado'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        throw new NoHaySiguienteEstadoException("El estado ${this.nombre} no tiene uno siguiente no se puede cambiar")
    }
}

class EstadoNoEntregado extends EstadoPedido {
    EstadoNoEntregado(){
        this.nombre = 'no_entregado'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        throw new NoHaySiguienteEstadoException("El estado ${this.nombre} no tiene uno siguiente no se puede cambiar")
    }
}

class EstadoCancelado extends EstadoPedido {
    EstadoCancelado(){
        this.nombre = 'cancelado'
    }

    @Override
    EstadoPedido siguienteEstado(ModalidadEntrega modalidadEntrega) {
        throw new NoHaySiguienteEstadoException("El estado ${this.nombre} no tiene uno siguiente no se puede cambiar")
    }
}
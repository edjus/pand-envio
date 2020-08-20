package pandenvio

abstract class EstadoPedido {
    String nombre

    static constraints = {
        nombre nullable: false, blank: false
    }
}

class EstadoRecibido extends EstadoPedido {
    EstadoRecibido(){
        this.nombre = 'recibido'
    }
}

class EstadoEnPreparacion extends EstadoPedido {
    EstadoEnPreparacion(){
        this.nombre = 'en_preparacion'
    }
}

class EstadoListo extends EstadoPedido {
    EstadoListo(){
        this.nombre = 'listo'
    }
}

class EstadoEnEntrega extends EstadoPedido {
    EstadoEnEntrega(){
        this.nombre = 'en_entrega'
    }
}

class EstadoEnEspera extends EstadoPedido {
    EstadoEnEspera(){
        this.nombre = 'en_espera'
    }
}

class EstadoEntregado extends EstadoPedido {
    EstadoEntregado(){
        this.nombre = 'entregado'
    }
}

class EstadoNoEntregado extends EstadoPedido {
    EstadoNoEntregado(){
        this.nombre = 'no_entregado'
    }
}

class EstadoCancelado extends EstadoPedido {
    EstadoCancelado(){
        this.nombre = 'cancelado'
    }
}
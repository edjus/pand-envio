package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class EstadoPedidoSpec extends Specification implements DomainUnitTest<EstadoPedido> {

    void "test estado recibido es correcto"() {
        when:
            EstadoPedido estadoPedido = new EstadoRecibido()
        then:
            estadoPedido.nombre == 'recibido'
    }

    void "test estado siguiente a 'recibido' es 'en preparación'"() {
        when:
        EstadoPedido estadoPedido = new EstadoRecibido()
        ModalidadEntrega modalidad = new ModalidadParaRetirar()
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoEnPreparacion.class
    }

    void "test estado en preparacion es correcto"() {
        when:
            EstadoPedido estadoPedido = new EstadoEnPreparacion()
        then:
            estadoPedido.nombre == 'en_preparacion'
    }

    void "test estado siguiente a 'en preparación' es 'listo'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnPreparacion()
        ModalidadEntrega modalidad = new ModalidadParaRetirar()
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoListo.class
    }

    void "test estado listo es correcto"() {
        when:
            EstadoPedido estadoPedido = new EstadoListo()
        then:
            estadoPedido.nombre == 'listo'
    }

    void "test estado siguiente a 'listo' es 'entregado' si la modalidad es para retirar"() {
        when:
        EstadoPedido estadoPedido = new EstadoListo()
        ModalidadEntrega modalidad = new ModalidadParaRetirar()
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoEntregado.class
    }

    void "test estado siguiente a 'listo' es 'en entrega' si la modalidad es para llevar y hay repartidor"() {
        when:
        EstadoPedido estadoPedido = new EstadoListo()
        ModalidadEntrega modalidad = new ModalidadParaLlevar(repartidor: new Repartidor())
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoEnEntrega.class
    }

    void "test estado siguiente a 'listo' es 'en espera' si la modalidad es para llevar y no hay repartidor"() {
        when:
        EstadoPedido estadoPedido = new EstadoListo()
        ModalidadEntrega modalidad = new ModalidadParaLlevar()
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoEnEspera.class
    }

    void "test estado en espera es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEspera()
        then:
        estadoPedido.nombre == 'en_espera'
    }

    void "test estado siguiente a 'en espera' es 'en entrega' si hay repartidor"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEspera()
        ModalidadEntrega modalidad = new ModalidadParaLlevar(repartidor: new Repartidor())
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoEnEntrega.class
    }

    void "test estado siguiente a 'en espera' sigue 'en espera' si no hay repartidor"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEspera()
        ModalidadEntrega modalidad = new ModalidadParaLlevar()
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoEnEspera.class
    }

    void "test estado en entrega es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEntrega()
        then:
        estadoPedido.nombre == 'en_entrega'
    }

    void "test estado siguiente a 'en entrega' es 'entregado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEntrega()
        ModalidadEntrega modalidad = new ModalidadParaLlevar()
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoEntregado.class
    }

    void "test estado entregado es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoEntregado()
        then:
        estadoPedido.nombre == 'entregado'
    }

    void "test estado siguiente a 'entregado' lanza excepción"() {
        when:
        EstadoPedido estadoPedido = new EstadoEntregado()
        ModalidadEntrega modalidad = new ModalidadParaLlevar()
        estadoPedido.siguienteEstado(modalidad)
        then:
        thrown NoHaySiguienteEstadoException
    }

    void "test estado no entregado es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoNoEntregado()
        then:
        estadoPedido.nombre == 'no_entregado'
    }

    void "test estado siguiente a 'no entregado' lanza excepción"() {
        when:
        EstadoPedido estadoPedido = new EstadoNoEntregado()
        ModalidadEntrega modalidad = new ModalidadParaLlevar()
        estadoPedido.siguienteEstado(modalidad)
        then:
        thrown NoHaySiguienteEstadoException
    }

    void "test estado cancelado es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoCancelado()
        then:
        estadoPedido.nombre == 'cancelado'
    }

    void "test estado siguiente a 'cancelado' lanza excepción"() {
        when:
        EstadoPedido estadoPedido = new EstadoEntregado()
        ModalidadEntrega modalidad = new ModalidadParaLlevar()
        estadoPedido.siguienteEstado(modalidad)
        then:
        thrown NoHaySiguienteEstadoException
    }
}

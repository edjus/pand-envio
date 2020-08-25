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

    void "test estado en preparacion es correcto"() {
        when:
            EstadoPedido estadoPedido = new EstadoEnPreparacion()
        then:
            estadoPedido.nombre == 'en_preparacion'
    }

    void "test estado listo es correcto"() {
        when:
            EstadoPedido estadoPedido = new EstadoListo()
        then:
            estadoPedido.nombre == 'listo'
    }

    void "test estado en espera es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEspera()
        then:
        estadoPedido.nombre == 'en_espera'
    }


    void "test estado en entrega es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEntrega()
        then:
        estadoPedido.nombre == 'en_entrega'
    }

    void "test estado entregado es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoEntregado()
        then:
        estadoPedido.nombre == 'entregado'
    }

    void "test estado no entregado es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoNoEntregado()
        then:
        estadoPedido.nombre == 'no_entregado'
    }

    void "test estado cancelado es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoCancelado()
        then:
        estadoPedido.nombre == 'cancelado'
    }
}

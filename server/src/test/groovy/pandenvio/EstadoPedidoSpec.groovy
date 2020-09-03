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
        estadoPedido.siguienteEstado(modalidad).class == EstadoEnPreparacion
    }

    void "test cancelar estado 'recibido'"() {
        when:
        EstadoPedido estadoPedido = new EstadoRecibido()
        then:
        estadoPedido.cancelar().class == EstadoCancelado
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
        estadoPedido.siguienteEstado(modalidad).class == EstadoListo
    }

    void "test cancelar estado 'en preparacion'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnPreparacion()
        then:
        estadoPedido.cancelar().class == EstadoCancelado
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
        estadoPedido.siguienteEstado(modalidad).class == EstadoEntregado
    }

    void "test cancelar estado 'listo' lanza excepción"() {
        when:
        EstadoPedido estadoPedido = new EstadoListo()
        estadoPedido.cancelar()
        then:
        thrown PedidoNoSePuedeCancelarException
    }

    void "test estado siguiente a 'listo' es 'en entrega' si la modalidad es para llevar"() {
        when:
        EstadoPedido estadoPedido = new EstadoListo()
        ModalidadEntrega modalidad = new ModalidadParaLlevar(repartidor: new Repartidor())
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoEnEntrega
    }

    void "test estado en espera es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEspera()
        then:
        estadoPedido.nombre == 'en_espera'
    }

    void "test estado siguiente a 'en espera' es 'en entrega'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEspera()
        ModalidadEntrega modalidad = new ModalidadParaLlevar(repartidor: new Repartidor())
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoEnEntrega
    }

    void "test cancelar estado 'en espera' lanza excepción"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEspera()
        estadoPedido.cancelar()
        then:
        thrown PedidoNoSePuedeCancelarException
    }

    void "test estado 'en entrega' es correcto"() {
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
        estadoPedido.siguienteEstado(modalidad).class == EstadoEntregado
    }

    void "test cancelar estado 'en entrega' lanza excepción"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEntrega()
        estadoPedido.cancelar()
        then:
        thrown PedidoNoSePuedeCancelarException
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

    void "test cancelar estado 'entregado' lanza excepción"() {
        when:
        EstadoPedido estadoPedido = new EstadoEntregado()
        estadoPedido.cancelar()
        then:
        thrown PedidoNoSePuedeCancelarException
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

    void "test cancelar estado 'no entregado' lanza excepción"() {
        when:
        EstadoPedido estadoPedido = new EstadoNoEntregado()
        estadoPedido.cancelar()
        then:
        thrown PedidoNoSePuedeCancelarException
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

    void "test cancelar estado 'cancelado' sigue siendo cancelado"() {
        when:
        EstadoPedido estadoPedido = new EstadoCancelado()
        then:
        estadoPedido.cancelar().class == EstadoCancelado
    }
}

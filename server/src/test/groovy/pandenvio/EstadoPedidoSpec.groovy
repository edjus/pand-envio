package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class EstadoPedidoSpec extends Specification implements DomainUnitTest<EstadoPedido> {

    void "test estado 'en_armado' es correcto"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnArmado()
        then:
        estadoPedido.nombre == 'en_armado'
    }


    void "test estado siguiente a 'en_armado' es 'recibido'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnArmado()
        ModalidadEntrega modalidad = new ModalidadParaRetirar()
        then:
        estadoPedido.siguienteEstado(modalidad).class == EstadoRecibido
    }

    void "test cancelar estado 'en_armado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnArmado()
        then:
        estadoPedido.cancelar().class == EstadoCancelado
    }

    void "test puede remover producto en estado 'en_armado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnArmado()
        then:
        estadoPedido.puedeActualizarProductos()
    }

    void "test no entregar lanza excepción en estado 'en_armado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnArmado()
        estadoPedido.noEntregar()
        then:
        thrown(NoSePuedeMarcarComoNoEntregadoException)
    }

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

    void "test no puede remover producto en estado 'recibido'"() {
        when:
        EstadoPedido estadoPedido = new EstadoRecibido()
        then:
        !estadoPedido.puedeActualizarProductos()
    }

    void "test no entregar lanza excepción en estado 'recibido'"() {
        when:
        EstadoPedido estadoPedido = new EstadoRecibido()
        estadoPedido.noEntregar()
        then:
        thrown(NoSePuedeMarcarComoNoEntregadoException)
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

    void "test no puede remover producto en estado 'en preparacion'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnPreparacion()
        then:
        !estadoPedido.puedeActualizarProductos()
    }

    void "test no entregar lanza excepción en estado 'en preparacion'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnPreparacion()
        estadoPedido.noEntregar()
        then:
        thrown(NoSePuedeMarcarComoNoEntregadoException)
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

    void "test no puede remover producto en estado 'listo'"() {
        when:
        EstadoPedido estadoPedido = new EstadoListo()
        then:
        !estadoPedido.puedeActualizarProductos()
    }

    void "test no entregar lanza excepción en estado 'listo'"() {
        when:
        EstadoPedido estadoPedido = new EstadoListo()
        estadoPedido.noEntregar()
        then:
        thrown(NoSePuedeMarcarComoNoEntregadoException)
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

    void "test no puede remover producto en estado 'en espera'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEspera()
        then:
        !estadoPedido.puedeActualizarProductos()
    }

    void "test no entregar lanza excepción en estado 'en espera'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEspera()
        estadoPedido.noEntregar()
        then:
        thrown(NoSePuedeMarcarComoNoEntregadoException)
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

    void "test no puede remover producto en estado 'en entrega'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEntrega()
        then:
        !estadoPedido.puedeActualizarProductos()
    }

    void "test no entregar lanza excepción en estado 'en entrega'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEntrega()
        estadoPedido.noEntregar()
        then:
        thrown(NoSePuedeMarcarComoNoEntregadoException)
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

    void "test no puede remover producto en estado 'entregado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEntregado()
        then:
        !estadoPedido.puedeActualizarProductos()
    }

    void "test no entregar correcto en estado 'entregado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEntregado()
        def estado = estadoPedido.noEntregar()
        then:
        estado.class == EstadoNoEntregado
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

    void "test no puede remover producto en estado 'no entregado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoNoEntregado()
        then:
        !estadoPedido.puedeActualizarProductos()
    }

    void "test no entregar lanza excepción en estado 'no entregado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoNoEntregado()
        estadoPedido.noEntregar()
        then:
        thrown(NoSePuedeMarcarComoNoEntregadoException)
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

    void "test no puede remover producto en estado 'cancelado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoCancelado()
        then:
        !estadoPedido.puedeActualizarProductos()
    }

    void "test no entregar lanza excepción en estado 'cancelado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoCancelado()
        estadoPedido.noEntregar()
        then:
        thrown(NoSePuedeMarcarComoNoEntregadoException)
    }

    void "test puntar lanza excepción en estado 'no entregado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoNoEntregado()
        estadoPedido.puntuar(new ModalidadParaLlevar(), 4)
        then:
        thrown(CalificacionException)
    }

    void "test puntar lanza excepción en estado 'cancelado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoCancelado()
        estadoPedido.puntuar(new ModalidadParaLlevar(), 4)
        then:
        thrown(CalificacionException)
    }

    void "test puntar lanza excepción en estado 'en_preparacion'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnPreparacion()
        estadoPedido.puntuar(new ModalidadParaLlevar(), 4)
        then:
        thrown(CalificacionException)
    }

    void "test puntar lanza excepción en estado 'recibido'"() {
        when:
        EstadoPedido estadoPedido = new EstadoRecibido()
        estadoPedido.puntuar(new ModalidadParaLlevar(), 4)
        then:
        thrown(CalificacionException)
    }

    void "test puntar lanza excepción en estado 'en_espera'"() {
        when:
        EstadoPedido estadoPedido = new EstadoRecibido()
        estadoPedido.puntuar(new ModalidadParaLlevar(), 4)
        then:
        thrown(CalificacionException)
    }

    void "test puntar lanza excepción en estado 'en_entrega'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnEntrega()
        estadoPedido.puntuar(new ModalidadParaLlevar(), 4)
        then:
        thrown(CalificacionException)
    }

    void "test puntar lanza excepción en estado 'en armado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEnArmado()
        estadoPedido.puntuar(new ModalidadParaLlevar(), 4)
        then:
        thrown(CalificacionException)
    }

    void "test puntar correctamente si el estado es 'entregado'"() {
        when:
        EstadoPedido estadoPedido = new EstadoEntregado()
        def modalidad = new ModalidadParaLlevar()
        estadoPedido.puntuar(modalidad, 4)
        then:
        modalidad.puntuacion.estrellas == 4
    }
}

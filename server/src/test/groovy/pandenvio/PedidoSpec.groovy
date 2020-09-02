package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class PedidoSpec extends Specification implements DomainUnitTest<Pedido> {

    void "test Pedido se crea con fecha actual y con descuento Nulo"() {
        when:
            Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar())
        then:
            pedido.validate()
            pedido.fecha.date.toString() == (new Date()).date.toString()
            pedido.cuponDeDescuento.class == CuponDescuentoNulo
            pedido.estado.class == EstadoRecibido
    }

    void "test cliente de un Pedido no puede ser null"() {
        when:
            Pedido pedido = new Pedido(null, new ModalidadParaRetirar())
        then:
            !pedido.validate()
    }

    void "test modalidad entrega de un Pedido no puede ser null"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), null)
        then:
        !pedido.validate()
    }

    void "test Pedido el siguiente estado es correcto según el estado el estado actual"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar())
        pedido.agregar(new Plato(), 3)
        pedido.siguienteEstado()
        then:
        pedido.estado.class == EstadoEnPreparacion.class
        pedido.nombreEstado == 'en_preparacion'
    }

    void "test Pedido sin items no actualiza su estado"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar())
        pedido.siguienteEstado()
        then:
        thrown PedidoNoTieneItemsException
    }

    void "test cancelar Pedido se cancela según estado"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar())
        pedido.cancelar()
        then:
        pedido.estado.class == EstadoCancelado.class
        pedido.nombreEstado == 'cancelado'
    }
}

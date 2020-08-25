package pandenvio

import grails.testing.gorm.DataTest
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


}

package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class PedidoSpec extends Specification implements DomainUnitTest<Pedido> {

    def setup() {
    }

    def cleanup() {
    }

    void "test Pedido se crea con fecha actual y con descuento Nulo"() {
        when:
            Pedido pedido = new Pedido(new Cliente())
        then:
            pedido.validate()
            pedido.fecha.toString() == (new Date()).toString()
            pedido.cuponDeDescuento.class == CuponDescuentoNulo
    }

    void "test cliente de un Pedido no puede ser null"() {
        when:
            Pedido pedido = new Pedido()
        then:
            !pedido.validate()
    }
}

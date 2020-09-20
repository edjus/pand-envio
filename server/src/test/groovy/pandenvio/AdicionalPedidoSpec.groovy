package pandenvio

import spock.lang.Specification

class AdicionalPedidoSpec extends Specification {

    void "test AdicionalClimatico cuando es no lluvioso es 0"() {
        when:
        AdicionalClimatico adicional = new AdicionalClimatico()
        def pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), new Restaurant())
        then:
        adicional.obtenerAdicional(100.0, pedido) == 0
    }

    void "test AdicionalClimatico cuando es lluvioso es 50"() {
        when:
        AdicionalClimatico adicional = new AdicionalClimatico()
        def pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), new Restaurant())
        pedido.clima = FabricaClima.crearPara('lluvioso')
        then:
        adicional.obtenerAdicional(100.0, pedido) == 50
    }

    void "test AdicionalDistancia cuando está en rango es 0"() {
        when:
        AdicionalDistancia adicional = new AdicionalDistancia()
        def pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), new Restaurant())
        then:
        adicional.obtenerAdicional(100.0, pedido) == 0
    }

    void "test AdicionalDistancia cuando no está en rango es el 10% del precio base"() {
        when:
        AdicionalDistancia adicional = new AdicionalDistancia()
        def pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), new Restaurant())
        pedido.enRango = false
        then:
        adicional.obtenerAdicional(100.0, pedido) == 10
    }
}

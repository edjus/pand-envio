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

    void "test precio de un pedido sin productos es 0"() {
        given:
            Pedido pedido = new Pedido(new Cliente())
        when:
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == 0
    }

    void "test precio de un pedido con productos"() {
        given:
            Pedido pedido = new Pedido(new Cliente())
            Plato plato = new Plato(nombre: 'Alto Guiso', precio: 15, categoria: CategoriaPlato.PLATO)
            Integer cantidad = 2
        when:
            pedido.agregar(plato, cantidad)
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == plato.getPrecio() * cantidad
    }
}

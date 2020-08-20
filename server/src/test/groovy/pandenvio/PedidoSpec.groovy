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

    void "test precio de un pedido sin productos con cupon activo es 0"() {
        given:
            Pedido pedido = new Pedido(new Cliente())
            CuponDescuento cupon = new CuponDescuentoPorcentual(activo: true, porcentaje: 10)
        when:
            pedido.cuponDeDescuento = cupon
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == 0
    }

    void "test precio de un pedido sin productos con cupon inactivo lanza error"() {
        given:
            Pedido pedido = new Pedido(new Cliente())
            CuponDescuento cupon = new CuponDescuentoPorcentual(activo: false, porcentaje: 10)
        when:
            pedido.cuponDeDescuento = cupon
            BigDecimal precio = pedido.calcularPrecio()
        then:
            thrown CuponYaUtilizadoException
    }

    void "test precio de un pedido con productos con cupon activo aplica descuento"() {
        given:
            Pedido pedido = new Pedido(new Cliente())
            CuponDescuento cupon = new CuponDescuentoPorcentual(activo: true, porcentaje: 10)
            Plato plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO)
            Plato plato2 = new Plato(nombre: 'Flan', precio: 100, categoria: CategoriaPlato.POSTRE)
            pedido.agregar(plato, 1)
            pedido.agregar(plato2, 2)
        when:
            pedido.cuponDeDescuento = cupon
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == 360 // (200*1 + 100*2)* (1 - 0.1)
    }
}

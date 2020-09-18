package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CuponDescuentoNuloSpec extends Specification implements DomainUnitTest<CuponDescuentoNulo> {

    void "test Cupon Descuento Nulo disponible no aplica ningun descuento"() {
        when:
        CuponDescuentoNulo cuponNuloActivo = new CuponDescuentoNulo(fecha: new Date(), codigo: 'ABC')
        Pedido pedido = new Pedido()
        BigDecimal precio = cuponNuloActivo.aplicarDescuento(100, pedido)
        then:
        precio == 100
        cuponNuloActivo.estaDisponible()
    }

    void "test Cupon Descuento Nulo no disponible tampoco aplica ningun descuento"() {
        when:
        CuponDescuentoNulo cuponNuloActivo = new CuponDescuentoNulo(fecha: new Date(), codigo: 'ABC')
        Pedido pedido = new Pedido()
        cuponNuloActivo.pedidoBeneficiado = pedido
        BigDecimal precio = cuponNuloActivo.aplicarDescuento(100, pedido)
        then:
        precio == 100
        !cuponNuloActivo.estaDisponible()
    }

    void "Cupon Descuento Nulo se guarda correctamente"() {
        when:
            def cupon = new CuponDescuentoNulo(fecha: new Date(), codigo: 'ABC').save(failOnError: true)
        then:
            CuponDescuentoNulo.count == 1
            cupon == CuponDescuentoNulo.findById(cupon.id)
    }

    void "Cupon Descuento Nulo es de cliente si no tiene cliente"() {
        when:
        def cupon = new CuponDescuentoNulo(fecha: new Date(), codigo: 'ABC').save(failOnError: true)
        Cliente cliente = new Cliente()
        then:
        cupon.esDe(cliente)
    }
}

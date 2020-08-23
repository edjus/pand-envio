package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CuponDescuentoNuloSpec extends Specification implements DomainUnitTest<CuponDescuentoNulo> {

    void "test Cupon Descuento Nulo activo no aplica ningun descuento"() {
        when:
        CuponDescuentoNulo cuponNuloActivo = new CuponDescuentoNulo(fecha: new Date(), activo: true, codigo: 'ABC')
        BigDecimal precio = cuponNuloActivo.aplicarDescuento(100)
        then:
        precio == 100
    }

    void "test Cupon Descuento Nulo inactivo tampoco aplica ningun descuento"() {
        when:
        CuponDescuentoNulo cuponNuloActivo = new CuponDescuentoNulo(fecha: new Date(), activo: true, codigo: 'ABC')
        BigDecimal precio = cuponNuloActivo.aplicarDescuento(100)
        then:
        precio == 100
    }

    void "Cupon Descuento Nulo se guarda correctamente"() {
        when:
            def cupon = new CuponDescuentoNulo(fecha: new Date(), activo: false, codigo: 'ABC').save(failOnError: true)
        then:
            CuponDescuentoNulo.count == 1
            cupon == CuponDescuentoNulo.findById(cupon.id)
    }
}

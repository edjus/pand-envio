package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class PlatoSpec extends Specification implements DomainUnitTest<Plato> {

    Plato plato = new Plato(nombre: 'Alto Guiso', precio: 15, categoria: CategoriaPlato.PLATO)


    void "test Plato admite un cupon de descuento Nulo "() {
        when:
        boolean admite = plato.admiteA(new CuponDescuentoNulo())
        then:
        admite
    }


    void "test Menu No admite un cupon de descuento Porcentual "() {
        when:
        boolean admite = plato.admiteA(new CuponDescuentoPorcentual(porcentaje: 15))
        then:
        admite
    }

    void "test Plato deberia tener precio fijo "() {
        when:
        BigDecimal precio = plato.getPrecio()
        then:
        precio == 15
    }
}

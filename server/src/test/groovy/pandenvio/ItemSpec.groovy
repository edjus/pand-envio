package pandenvio

import grails.testing.gorm.DomainUnitTest
import pandenvio.Item
import spock.lang.Specification

class ItemSpec extends Specification implements DomainUnitTest<Item> {

    def setup() {
    }

    def cleanup() {
    }

    void "test Item admite descuento si el producto lo admite"() {
        given:
            Item item = new Item(new Plato(), 1)
            CuponDescuento cupon = new CuponDescuentoPorcentual(activo: true, fecha: new Date())
        when:
            Boolean admite = item.admiteA(cupon)
        then:
            admite.equals(true)
    }

    void "test Item no admite descuento si el producto no lo admite"() {
        given:
            Item item = new Item(new Menu(), 1)
            CuponDescuento cupon = new CuponDescuentoPorcentual(activo: true, fecha: new Date())
        when:
            Boolean admite = item.admiteA(cupon)
        then:
            admite.equals(false)
    }
}

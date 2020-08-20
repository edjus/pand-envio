package pandenvio

import grails.testing.gorm.DomainUnitTest
import pandenvio.Item
import spock.lang.Specification

class ItemSpec extends Specification implements DomainUnitTest<Item> {

    Plato plato
    Menu menu
    CuponDescuento cupon
    
    def setup() {
        plato = new Plato(nombre: 'Alto Guiso', precio: 15, categoria: CategoriaPlato.PLATO)
        menu = new Menu(nombre: 'Viernes', precio: 300)
        cupon = new CuponDescuentoPorcentual(activo: true, fecha: new Date())
    }

    void "test item debe tener un producto"() {
        when:
            Item item = new Item(null, 1)
        then:
            !item.validate()
    }

    void "test item debe tener una cantidad positiva"() {
        when:
            Item item = new Item(plato, 0)
        then:
            !item.validate()
    }

    void "test Item admite descuento si el producto lo admite"() {
        given:
            Item item = new Item(plato, 1)
        when:
            Boolean admite = item.admiteA(cupon)
        then:
            admite.equals(true)
    }

    void "test Item no admite descuento si el producto no lo admite"() {
        given:
            Item item = new Item(menu, 1)
        when:
            Boolean admite = item.admiteA(cupon)
        then:
            admite.equals(false)
    }

    void "test el precio del Item es igual al precio del producto por la cantidad"() {
        given:
            BigDecimal precioPlato = 15
            Integer cantidad = 2
            Item item = new Item(new Plato(precio: precioPlato), cantidad)
        when:
            BigDecimal precioItem = item.calcularPrecio()
        then:
            precioItem == precioPlato * cantidad
    }
}

package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class MenuSpec extends Specification implements DomainUnitTest<Menu> {

    Menu menu = new Menu(nombre: 'Viernes', precio: 300)

    void "test Menu admite un cupon de descuento Nulo "() {
        when:
        boolean admite = menu.admiteA(new CuponDescuentoNulo())
        then:
        admite
    }


    void "test Menu No admite un cupon de descuento Porcentual "() {
        when:
        boolean admite = menu.admiteA(new CuponDescuentoPorcentual(porcentaje: 10))
        then:
        !admite
    }


    void "test Menu deberia tener precio fijo "() {
        when:
        BigDecimal precio = menu.precio
        then:
        precio == 300
    }

    void "test Menu tiene 2 platos, precio deberia seguir  fijo "() {
        when:
        Menu menuConPLatos = new Menu(nombre: 'Viernes', precio: 400)
        menuConPLatos.addToPlatos(new Plato(nombre:'Milanesa con pure', precio: 350, categoria: CategoriaPlato.PLATO))
        menuConPLatos.addToPlatos(new Plato(nombre:'Flan Mixto con pure', precio: 150, categoria: CategoriaPlato.POSTRE))
        BigDecimal precio = menuConPLatos.precio
        then:
        precio == 400
    }

    void "test Menu con 2 platos, se guarda bien"() {
        when:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina')
        Menu menuConPLatos = new Menu(nombre: 'Viernes', precio: 400, restaurant: restaurante)
        menuConPLatos.addToPlatos(new Plato(nombre:'Milanesa con pure', precio: 350, categoria: CategoriaPlato.PLATO, restaurant:  restaurante))
        menuConPLatos.addToPlatos(new Plato(nombre:'Flan Mixto con pure', precio: 150, categoria: CategoriaPlato.POSTRE, restaurant:  restaurante))
        then:
        menuConPLatos.save(failOnError: true)
        Menu.count == 1
        menuConPLatos == Menu.findById(menuConPLatos.id)
        Menu.findById(menuConPLatos.id).platos.size() == 2
    }
}

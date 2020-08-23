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

    void "Plato se guarda correctamente"() {
        when:
        Restaurant restaurante = new Restaurant(nombre: 'La esquina')
        Plato platoPrincipal = new Plato(nombre: 'Alto Guiso', precio: 15, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
        platoPrincipal.save(failOnError: true)
        then:
        Plato.count == 1
        def platoGuardado = Plato.findById(platoPrincipal.id)
        platoGuardado == platoPrincipal
        platoGuardado.restaurant == restaurante
    }
}

package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class RestaurantSpec extends Specification implements DomainUnitTest<Restaurant> {
    void "Restaurante se guarda correctamente"() {
        when:
        Restaurant restaurante = new Restaurant(nombre: 'La esquina', direccion: "Av Paseo Colón 123")
        restaurante.save()
        then:
        Restaurant.count == 1
        restaurante == Restaurant.findById(restaurante.id)
    }
}

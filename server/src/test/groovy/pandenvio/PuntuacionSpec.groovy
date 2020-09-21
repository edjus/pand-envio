package pandenvio

import grails.testing.gorm.DomainUnitTest
import pandenvio.Puntuacion
import spock.lang.Specification

class PuntuacionSpec extends Specification implements DomainUnitTest<Puntuacion> {

    void "test puntuacion no puede ser mayor a 5"() {
        when:
            Puntuacion puntuacion = new Puntuacion(6)
        then:
            !puntuacion.validate()
    }

    void "test puntuacion no puede ser menor a 1"() {
        when:
        Puntuacion puntuacion = new Puntuacion(0)
        then:
        !puntuacion.validate()
    }

    void "test puntuacion tiene que estar entre 1 y 5"() {
        when:
        Puntuacion puntuacion = new Puntuacion(3)
        then:
        puntuacion.validate()
    }

    void "puntuacion se guarda correctamente"() {
        when:
        Puntuacion puntuacion = new Puntuacion(3)
        puntuacion.save()
        then:
        Puntuacion.count == 1
        puntuacion == Puntuacion.findById(puntuacion.id)
    }
}

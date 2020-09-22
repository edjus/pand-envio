package pandenvio

import grails.testing.gorm.DomainUnitTest
import pandenvio.Puntuacion
import spock.lang.Specification

class PuntuacionSpec extends Specification implements DomainUnitTest<Puntuacion> {


    void "puntuacion se guarda correctamente"() {
        when:
        Puntuacion puntuacion = new Puntuacion(3)
        puntuacion.save()
        then:
        Puntuacion.count == 1
        puntuacion == Puntuacion.findById(puntuacion.id)
    }
}

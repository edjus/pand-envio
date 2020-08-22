package pandenvio

import grails.testing.gorm.DomainUnitTest
import pandenvio.Repartidor
import spock.lang.Specification

class RepartidorSpec extends Specification implements DomainUnitTest<Repartidor> {

    void "test repartidor no puede tener nombre null ni vacio"() {
        when:
            Repartidor repartidor = new Repartidor(null, '989798723')
            Repartidor repartidor2 = new Repartidor('', '989798723')
        then:
            !repartidor.validate()
            !repartidor2.validate()
    }

    void "test repartidor no puede tener dni null ni vacio"() {
        when:
            Repartidor repartidor = new Repartidor('Pepe', '')
            Repartidor repartidor2 = new Repartidor('Lucho', null)
        then:
            !repartidor.validate()
            !repartidor2.validate()
    }

    void "test repartidor tiene que tener nombre y dni"() {
        when:
            Repartidor repartidor = new Repartidor('Pepe', '987987')
        then:
            repartidor.validate()
    }
}
package pandenvio

import grails.testing.gorm.DomainUnitTest
import pandenvio.Repartidor
import spock.lang.Specification

class RepartidorSpec extends Specification implements DomainUnitTest<Repartidor> {

    void "test repartidor no puede tener nombre null ni vacio"() {
        when:
            Repartidor repartidor = new Repartidor(null, '989798723', new Restaurant())
            Repartidor repartidor2 = new Repartidor('', '989798723', new Restaurant())
        then:
            !repartidor.validate()
            !repartidor2.validate()
    }

    void "test repartidor no puede tener dni null ni vacio"() {
        when:
            Repartidor repartidor = new Repartidor('Pepe', '', new Restaurant())
            Repartidor repartidor2 = new Repartidor('Lucho', null, new Restaurant())
        then:
            !repartidor.validate()
            !repartidor2.validate()
    }

    void "test repartidor no puede tener restaurant null"() {
        when:
        Repartidor repartidor = new Repartidor('Lucho', '98798764', null)
        then:
        !repartidor.validate()
    }

    void "test repartidor tiene que tener nombre, dni y restaurante por defecto está disponible"() {
        when:
            Repartidor repartidor = new Repartidor('Pepe', '987987', new Restaurant())
        then:
            repartidor.validate()
            repartidor.disponible
    }


}

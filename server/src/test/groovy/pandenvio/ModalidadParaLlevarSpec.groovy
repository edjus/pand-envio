package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ModalidadParaLlevarSpec extends Specification implements DomainUnitTest<ModalidadParaLlevar> {

    void "test modalidad para llevar se guarda bien"() {
        when:
            def repartidor = new Repartidor('Lucas', '989764699')
            def puntuacion = new Puntuacion(3)
            def modalidad = new ModalidadParaLlevar(repartidor: repartidor, puntuacion: puntuacion)
        then:
            modalidad.save(failOnError: true)
            ModalidadParaLlevar.count == 1
            modalidad == ModalidadParaLlevar.findById(modalidad.id)
    }
}

package pandenvio

import grails.testing.gorm.DomainUnitTest
import pandenvio.ModalidadParaRetirar
import spock.lang.Specification

class ModalidadParaRetirarSpec extends Specification implements DomainUnitTest<ModalidadParaRetirar> {

    void "test modalidad para retirar se guarda bien"() {
        when:
            def modalidad = new ModalidadParaRetirar()
        then:
            modalidad.save(failOnError: true)
            ModalidadParaRetirar.count == 1
            modalidad == ModalidadParaRetirar.findById(modalidad.id)
    }
}

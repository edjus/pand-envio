package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UbicacionSpec extends Specification implements DomainUnitTest<Ubicacion> {

    void "Ubicacion de casa deberia mostrar calle y altura"() {
        when:
            Ubicacion casa = new Ubicacion(calle: 'Av. San Martin', altura: 1234)
            String ubicacionCompleta = casa.getUbicacionCompleta()
        then:
        ubicacionCompleta == 'Av. San Martin 1234'
    }

    void "Ubicacion de departamente deberia mostrar calle, altura y piso/depto"() {
        when:
            Ubicacion departamento = new Ubicacion(calle: 'Av. San Martin', altura: 1234, pisoYDepartamento: "5A")
            String ubicacionCompleta = departamento.ubicacionCompleta
        then:
            ubicacionCompleta == 'Av. San Martin 1234 - 5A'
    }

    void "Ubicacion se guarda correctamente"() {
        when:
            Ubicacion departamento = new Ubicacion(calle: 'Av. San Martin', altura: 1234, pisoYDepartamento: "5A")
            departamento.save()
        then:
            Ubicacion.count == 1
            departamento == Ubicacion.findById(departamento.id)
    }
}

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
            'para_retirar' == ModalidadParaRetirar.findById(modalidad.id).nombre
    }

    void "test modalidad para retirar no tiene repartidor"() {
        when:
        def modalidad = new ModalidadParaRetirar()
        then:
        !modalidad.hayRepartidor()
    }

    void "test siguiente a estado listo es listo"() {
        when:
        def modalidad = new ModalidadParaRetirar()
        then:
        modalidad.siguienteEstadoListo().class == EstadoEntregado.class
    }

    void "test asignar repartidor no asigna repartidor"() {
        when:
        def repartidor = new Repartidor('Lucas', '989764699', new Restaurant())
        def modalidad = new ModalidadParaRetirar()
        modalidad.asignarRepartidor(repartidor)
        then:
        !modalidad.hayRepartidor()
        repartidor.disponible
    }

    void "test no entregar lanza excepción"() {
        when:
        def modalidad = new ModalidadParaRetirar()
        modalidad.noEntregar(new EstadoEntregado())
        then:
        thrown(NoSePuedeMarcarComoNoEntregadoException)
    }

    void "test puntuar lanza excepción"() {
        when:
        def modalidad = new ModalidadParaRetirar()
        modalidad.agregarPuntuacion()
        then:
        thrown(CalificacionException)
    }

    void "test obtenerEstrellas lanza excepción"() {
        when:
        def modalidad = new ModalidadParaRetirar()
        modalidad.obtenerEstrellas()
        then:
        thrown(CalificacionException)
    }
}

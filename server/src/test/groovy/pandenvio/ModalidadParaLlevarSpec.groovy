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
            'para_llevar' == ModalidadParaLlevar.findById(modalidad.id).nombre
    }

    void "test modalidad para llevar tiene repartidor"() {
        when:
        def repartidor = new Repartidor('Lucas', '989764699')
        def puntuacion = new Puntuacion(3)
        def modalidad = new ModalidadParaLlevar(repartidor: repartidor, puntuacion: puntuacion)
        then:
        modalidad.hayRepartidor()
    }

    void "test modalidad para llevar no tiene repartidor"() {
        when:
        def puntuacion = new Puntuacion(3)
        def modalidad = new ModalidadParaLlevar(repartidor: null, puntuacion: puntuacion)
        then:
        !modalidad.hayRepartidor()
    }

    void "test siguiente a estado listo es 'en entrega'"() {
        when:
        def repartidor = new Repartidor('Lucas', '989764699')
        def puntuacion = new Puntuacion(3)
        def modalidad = new ModalidadParaLlevar(repartidor: repartidor, puntuacion: puntuacion)
        then:
        modalidad.siguienteEstadoListo().class == EstadoEnEntrega.class
    }

    void "test asignar repartidor tiene repartidor y repartidor deja de estar disponible"() {
        when:
        def repartidor = new Repartidor('Lucas', '989764699')
        def modalidad = new ModalidadParaLlevar()
        modalidad.asignarRepartidor(repartidor)
        then:
        modalidad.hayRepartidor()
        !repartidor.disponible
    }
}

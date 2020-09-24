package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ModalidadParaLlevarSpec extends Specification implements DomainUnitTest<ModalidadParaLlevar> {

    void "test modalidad para llevar se guarda bien"() {
        when:
            def repartidor = new Repartidor('Lucas', '989764699', new Restaurant())
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
        def repartidor = new Repartidor('Lucas', '989764699', new Restaurant())
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
        def repartidor = new Repartidor('Lucas', '989764699', new Restaurant())
        def puntuacion = new Puntuacion(3)
        def modalidad = new ModalidadParaLlevar(repartidor: repartidor, puntuacion: puntuacion)
        then:
        modalidad.siguienteEstadoListo().class == EstadoEnEntrega.class
    }

    void "test asignar repartidor tiene repartidor y repartidor deja de estar disponible"() {
        when:
        def repartidor = new Repartidor('Lucas', '989764699', new Restaurant())
        def modalidad = new ModalidadParaLlevar()
        modalidad.asignarRepartidor(repartidor)
        then:
        modalidad.hayRepartidor()
        !repartidor.disponible
    }

    void "test no entregar correcto si el estado es entregado"() {
        when:
        def modalidad = new ModalidadParaLlevar()
        def estado = modalidad.noEntregar(new EstadoEntregado())
        then:
        estado.class == EstadoNoEntregado
    }

    void "test no entregar lanza excepción si el estado no es entregado"() {
        when:
        def modalidad = new ModalidadParaLlevar()
        modalidad.noEntregar(new EstadoEnPreparacion())
        then:
        thrown(NoSePuedeMarcarComoNoEntregadoException)
    }

    void "test puntuar correcto si no tiene puntuación"() {
        when:
        def modalidad = new ModalidadParaLlevar()
        modalidad.agregarPuntuacion(4)
        then:
        modalidad.puntuacion.estrellas == 4
    }


    void "test puntuar lanza excepción si ya fue puntuado"() {
        when:
        def modalidad = new ModalidadParaLlevar()
        modalidad.agregarPuntuacion(4)
        assert modalidad.puntuacion.estrellas == 4
        modalidad.agregarPuntuacion(1)
        then:
        thrown(CalificacionException)
    }

    void "test puntuar lanza excepción si la puntuacion es inválida"() {
        when:
        def modalidad = new ModalidadParaLlevar()
        modalidad.agregarPuntuacion(8)
        then:
        thrown(CalificacionException)
    }

    void "test obtener estrellas devuelve 0 si no fue calificado"() {
        when:
        def modalidad = new ModalidadParaLlevar()
        def estrellas = modalidad.obtenerEstrellas()
        then:
        estrellas == 0
    }

    void "test obtener estrellas devuelve cantidad de estrellas correcta"() {
        when:
        def modalidad = new ModalidadParaLlevar()
        modalidad.agregarPuntuacion(3)
        def estrellas = modalidad.obtenerEstrellas()
        then:
        estrellas == 3
    }
}

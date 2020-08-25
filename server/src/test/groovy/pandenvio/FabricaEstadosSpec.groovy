package pandenvio

import grails.testing.gorm.DomainUnitTest
import pandenvio.FabricaEstados
import spock.lang.Specification

class FabricaEstadosSpec extends Specification implements DomainUnitTest<FabricaEstados> {

    void "test fabrica crea correctamente estado recibido"() {
        when:
            def estado = FabricaEstados.obtenerEstado('recibido')
        then:
            estado.class == EstadoRecibido.class
            estado.nombre == 'recibido'
    }

    void "test fabrica crea correctamente estado en preparacion"() {
        when:
        def estado = FabricaEstados.obtenerEstado('en_preparacion')
        then:
        estado.class == EstadoEnPreparacion.class
        estado.nombre == 'en_preparacion'
    }


    void "test fabrica crea correctamente estado listo"() {
        when:
        def estado = FabricaEstados.obtenerEstado('listo')
        then:
        estado.class == EstadoListo.class
        estado.nombre == 'listo'
    }

    void "test fabrica crea correctamente estado en_espera"() {
        when:
        def estado = FabricaEstados.obtenerEstado('en_espera')
        then:
        estado.class == EstadoEnEspera.class
        estado.nombre == 'en_espera'
    }

    void "test fabrica crea correctamente estado en_entrega"() {
        when:
        def estado = FabricaEstados.obtenerEstado('en_entrega')
        then:
        estado.class == EstadoEnEntrega.class
        estado.nombre == 'en_entrega'
    }

    void "test fabrica crea correctamente estado entregado"() {
        when:
        def estado = FabricaEstados.obtenerEstado('entregado')
        then:
        estado.class == EstadoEntregado.class
        estado.nombre == 'entregado'
    }

    void "test fabrica crea correctamente estado no entregado"() {
        when:
        def estado = FabricaEstados.obtenerEstado('no_entregado')
        then:
        estado.class == EstadoNoEntregado.class
        estado.nombre == 'no_entregado'
    }

    void "test fabrica crea correctamente estado cancelado"() {
        when:
        def estado = FabricaEstados.obtenerEstado('cancelado')
        then:
        estado.class == EstadoCancelado.class
        estado.nombre == 'cancelado'
    }

    void "test fabrica lanza excepción cuando no es un estado válido"() {
        when:
        FabricaEstados.obtenerEstado('fruta')
        then:
        thrown EstadoInvalidoException
    }
}

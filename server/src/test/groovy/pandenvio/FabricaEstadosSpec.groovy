package pandenvio

import grails.testing.gorm.DomainUnitTest
import pandenvio.FabricaEstados
import spock.lang.Specification

class FabricaEstadosSpec extends Specification implements DomainUnitTest<FabricaEstados> {

    void "test fabrica crea correctamente estado 'en_armado'"() {
        when:
        def estado = FabricaEstados.obtenerEstado('en_armado')
        then:
        estado.class == EstadoEnArmado
        estado.nombre == 'en_armado'
    }

    void "test fabrica crea correctamente estado recibido"() {
        when:
            def estado = FabricaEstados.obtenerEstado('recibido')
        then:
            estado.class == EstadoRecibido
            estado.nombre == 'recibido'
    }

    void "test fabrica crea correctamente estado en preparacion"() {
        when:
        def estado = FabricaEstados.obtenerEstado('en_preparacion')
        then:
        estado.class == EstadoEnPreparacion
        estado.nombre == 'en_preparacion'
    }


    void "test fabrica crea correctamente estado listo"() {
        when:
        def estado = FabricaEstados.obtenerEstado('listo')
        then:
        estado.class == EstadoListo
        estado.nombre == 'listo'
    }

    void "test fabrica crea correctamente estado en_espera"() {
        when:
        def estado = FabricaEstados.obtenerEstado('en_espera')
        then:
        estado.class == EstadoEnEspera
        estado.nombre == 'en_espera'
    }

    void "test fabrica crea correctamente estado en_entrega"() {
        when:
        def estado = FabricaEstados.obtenerEstado('en_entrega')
        then:
        estado.class == EstadoEnEntrega
        estado.nombre == 'en_entrega'
    }

    void "test fabrica crea correctamente estado entregado"() {
        when:
        def estado = FabricaEstados.obtenerEstado('entregado')
        then:
        estado.class == EstadoEntregado
        estado.nombre == 'entregado'
    }

    void "test fabrica crea correctamente estado no entregado"() {
        when:
        def estado = FabricaEstados.obtenerEstado('no_entregado')
        then:
        estado.class == EstadoNoEntregado
        estado.nombre == 'no_entregado'
    }

    void "test fabrica crea correctamente estado cancelado"() {
        when:
        def estado = FabricaEstados.obtenerEstado('cancelado')
        then:
        estado.class == EstadoCancelado
        estado.nombre == 'cancelado'
    }

    void "test fabrica lanza excepción cuando no es un estado válido"() {
        when:
        FabricaEstados.obtenerEstado('fruta')
        then:
        thrown EstadoInvalidoException
    }
}

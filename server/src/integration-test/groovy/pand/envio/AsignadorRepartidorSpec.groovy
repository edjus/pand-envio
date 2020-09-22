package pand.envio

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import pandenvio.AsignadorRepartidor
import pandenvio.Repartidor
import pandenvio.Restaurant
import spock.lang.Specification

@Integration
@Rollback
class AsignadorRepartidorSpec extends Specification {

    void "test obtener repartidor devuelve null si no hay repartidores"() {
        when:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        def repartidor = AsignadorRepartidor.instance.obtenerRepartidor(restaurant)
        then:
        repartidor == null
    }

    void "test obtener repartidor devuelve el que está disponible del restaurante"() {
        when:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Repartidor repartidor = new Repartidor("Juan", "98978654", restaurant).save(failOnError: true)
        def repartidorEncontrado = AsignadorRepartidor.instance.obtenerRepartidor(restaurant)
        then:
        repartidorEncontrado == repartidor
        repartidorEncontrado.disponible
        repartidorEncontrado.restaurant == restaurant
    }

    void "test obtener repartidor devuelve null si  el repartidor del restaurante no está disponible"() {
        when:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Repartidor repartidor = new Repartidor("Juan", "98978654", restaurant)
        repartidor.disponible = false
        repartidor.save(failOnError: true)
        def repartidorEncontrado = AsignadorRepartidor.instance.obtenerRepartidor(restaurant)
        then:
        repartidorEncontrado == null
        !repartidor.disponible
    }

    void "test obtener repartidor devuelve null si no hay repartidor disponible para ese restaurante pero si para otros"() {
        when:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Restaurant restaurant2 = new Restaurant(nombre: "Don Juan").save(failOnError: true)
        // El repartidor pertenece al restaurante 1
        Repartidor repartidor = new Repartidor("Juan", "98978654", restaurant).save(failOnError: true)
        // El repartidor2 pertenece al restaurante 2
        Repartidor repartidor2 = new Repartidor("Juan", "98978654", restaurant2)
        repartidor2.disponible = false
        repartidor2.save(failOnError: true)
        def repartidorEncontrado = AsignadorRepartidor.instance.obtenerRepartidor(restaurant2)

        then:
        repartidorEncontrado == null
        repartidor.disponible
        !repartidor2.disponible
    }

    void "test obtener repartidor es null si el repartidor está ocupado y hay otro de otro repartidor disponible"() {
        when:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Repartidor repartidor = new  Repartidor("Juan", "9798797", restaurante)
        repartidor.disponible = false
        repartidor.save(failOnError: true)
        def repartidorEncontrado = AsignadorRepartidor.instance.obtenerRepartidor(restaurante)
        then:
        repartidorEncontrado == null
    }
}

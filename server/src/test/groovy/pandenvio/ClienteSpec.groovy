package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Shared
import spock.lang.Specification

class ClienteSpec extends Specification implements DomainUnitTest<Cliente> {

    @Shared int id

    void "test basic persistence mocking"() {
        setup:
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234)
        def cliente1 =  new Cliente(nombre: 'Pepe', apellido:'Argento', mail: 'pepe.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433').save()
        new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433').save()
        expect:
        Cliente.count() == 2
        cliente1 == Cliente.findById(cliente1.id)
    }

    void "test domain instance"() {
        setup:
        id = System.identityHashCode(domain)

        expect:
        domain != null
        domain.hashCode() == id

        when:
        domain.nombre = 'Robert'

        then:
        domain.nombre == 'Robert'
    }

    void "test we get a new domain"() {
        expect:
        domain != null
        domain.nombre == null
        System.identityHashCode(domain) != id
    }

    void "test nombre completo del cliente"() {
        when:
        Cliente pepeArgento = new Cliente(nombre: 'Pepe', apellido: 'Argento')
        String nombreCompleto = pepeArgento.nombreCompleto()
        then:
        nombreCompleto.equals("Pepe Argento")
    }
}
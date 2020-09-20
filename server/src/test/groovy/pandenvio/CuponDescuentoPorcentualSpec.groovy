package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CuponDescuentoPorcentualSpec extends Specification implements DomainUnitTest<CuponDescuentoPorcentual> {

    Ubicacion juanPerezUbicacion = new Ubicacion(calle: "Paunero", altura: 2030, pisoYDepartamento: null)
    Cliente juanPerez = new Cliente(nombre: "Juan", apellido: "Perez", mail: "juanperez@yahoo.com.ar", ubicacion: juanPerezUbicacion, telefono: "1138465977",cupones:null)
    Restaurant restaurant = new Restaurant(nombre: 'La esquina')
    void "test Cupon Descuento Porcentual tiene pedido beneficiado y aplica descuento porcentual con pedido beneficiado correctamente"() {
        when:
        CuponDescuentoPorcentual cuponPorcentualActivo = new CuponDescuentoPorcentual(cliente:juanPerez, fecha: new Date(), activo: true, codigo: 'ABC', porcentaje: 10, restaurant: restaurant)
        Pedido pedido = new Pedido()
        cuponPorcentualActivo.pedidoBeneficiado = pedido
        BigDecimal precio = cuponPorcentualActivo.aplicarDescuento(100, pedido)
        then:
        precio == 90
        !cuponPorcentualActivo.estaDisponible()
    }

    void "test Cupon Descuento Porcentual no tiene pedido beneficiado y aplica descuento porcentual con pedido correctamente"() {
        when:
        CuponDescuentoPorcentual cuponPorcentualActivo = new CuponDescuentoPorcentual(cliente:juanPerez, fecha: new Date(), activo: true, codigo: 'ABC', porcentaje: 10, restaurant: restaurant)
        BigDecimal precio = cuponPorcentualActivo.aplicarDescuento(100, new Pedido())
        then:
        precio == 90
        cuponPorcentualActivo.estaDisponible()
    }


    void "test Cupon Descuento Porcentual disponible con pedido beneficado intenta aplicar descuento porcentual un pedido diferente y lanza excepcion"() {
        when:
        CuponDescuentoPorcentual cuponPorcentualActivo = new CuponDescuentoPorcentual(cliente:juanPerez, fecha: new Date(), codigo: 'ABC', porcentaje: 15, restaurant: restaurant)
        Pedido pedido = new Pedido()
        cuponPorcentualActivo.pedidoBeneficiado = pedido
        cuponPorcentualActivo.aplicarDescuento(100, new Pedido())
        then:
        thrown CuponYaUtilizadoException
    }


    void "test Cupon Descuento Porcentual NO puede tener poncertaje menor que 1"() {
        when:
        CuponDescuentoPorcentual cuponPorcentualActivo = new CuponDescuentoPorcentual(cliente:juanPerez, fecha: new Date(), odigo: 'ABC', porcentaje: -1, restaurant: restaurant)
        then:
        !cuponPorcentualActivo.validate()
    }

    void "test Cupon Descuento Porcentual NO puede tener poncertaje mayor que 99"() {
        when:
        CuponDescuentoPorcentual cuponPorcentualActivo = new CuponDescuentoPorcentual(cliente:juanPerez, fecha: new Date(), codigo: 'ABC', porcentaje: 100, restaurant: restaurant)
        then:
        !cuponPorcentualActivo.validate()
    }

    void "Cupon Descuento Porcentual se guarda correctamente"() {
        when:
            def cupon = new CuponDescuentoPorcentual(cliente:juanPerez, fecha: new Date(), codigo: 'ABC', porcentaje: 10, restaurant: restaurant)
        then:
            cupon.validate()
            cupon.save(failOnError)
            CuponDescuentoPorcentual.count == 1
            cupon == CuponDescuentoPorcentual.findById(cupon.id)
    }

    void "Cupon Descuento Porcentual no es de cliente"() {
        when:
        Cliente cliente = new Cliente()
        Cliente cliente2 = new Cliente()
        def cupon = new CuponDescuentoPorcentual(cliente: cliente, fecha: new Date(), codigo: 'ABC', porcentaje: 10, restaurant: restaurant)
        then:
        !cupon.perteneceA(cliente2)
    }

    void "Cupon Descuento Porcentual es de cliente"() {
        when:
        Cliente cliente = new Cliente()
        def cupon = new CuponDescuentoPorcentual(cliente: cliente, fecha: new Date(), codigo: 'ABC', porcentaje: 10, restaurant: restaurant)
        then:
        cupon.perteneceA(cliente)
    }

    void "Cupon Descuento Porcentual creado por restaurante cliente"() {
        when:
        Cliente cliente = new Cliente()
        def cupon = new CuponDescuentoPorcentual(cliente: cliente, fecha: new Date(), codigo: 'ABC', porcentaje: 10, restaurant: restaurant)
        then:
        cupon.creadoPor(restaurant)
    }

    void "Cupon Descuento Porcentual no creado por restaurante cliente"() {
        when:
        Cliente cliente = new Cliente()
        def cupon = new CuponDescuentoPorcentual(cliente: cliente, fecha: new Date(), codigo: 'ABC', porcentaje: 10, restaurant: restaurant)
        then:
        !cupon.creadoPor(new Restaurant())
    }
}

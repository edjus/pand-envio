package pand.envio

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import pandenvio.AsignadorRepartidor
import pandenvio.CategoriaPlato
import pandenvio.Cliente
import pandenvio.CreadorDeCupones
import pandenvio.CuponDescuento
import pandenvio.CuponDescuentoPorcentual
import pandenvio.EstadoEnArmado
import pandenvio.EstadoEntregado
import pandenvio.ModalidadEntrega
import pandenvio.ModalidadParaLlevar
import pandenvio.Pedido
import pandenvio.Plato
import pandenvio.Producto
import pandenvio.Restaurant
import pandenvio.Ubicacion
import spock.lang.Specification


@Integration
@Rollback
class CreadorCuponesSpec extends Specification {

    void "test no crea cupones si no tiene pedidos"() {
        given:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        def cantidadCupones = CuponDescuentoPorcentual.count

        when:
        CreadorDeCupones.instance.crearPosibleCupon(cliente, restaurant)

        then:
        CuponDescuentoPorcentual.count == cantidadCupones
    }

    void "test no crea cupones si tiene cupones disponibles para el restaurante"() {
        given:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        CuponDescuento cupon = new CuponDescuentoPorcentual(cliente: cliente, porcentaje: 10, codigo: 'TEST', fecha: new Date(), restaurant: restaurant)
                .save(failOnError: true)
        cliente.addToCupones(cupon).save(failOnError: true)
        def cantidadCupones = CuponDescuentoPorcentual.count

        when:
        CreadorDeCupones.instance.crearPosibleCupon(cliente, restaurant)

        then:
        CuponDescuentoPorcentual.count == cantidadCupones
    }

    void "test no crea cupones si no tiene cupones disponibles y tiene un pedido en el restaurante"() {
        given:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurant)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoEntregado estado = new EstadoEntregado()
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurant)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)

        def cantidadCupones = CuponDescuentoPorcentual.count

        when:
        CreadorDeCupones.instance.crearPosibleCupon(cliente, restaurant)

        then:
        CuponDescuentoPorcentual.count == cantidadCupones
    }

    void "test no crea cupones si no tiene cupones disponibles y tiene dos pedidos en el restaurante"() {
        given:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurant)
                .save(failOnError: true)

        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoEntregado estado = new EstadoEntregado()
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurant)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)

        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoEntregado estado2 = new EstadoEntregado()
        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurant)
        pedido2.agregar(plato, 2)
        pedido2.estado = estado2
        pedido2.save(failOnError: true)

        def cantidadCupones = CuponDescuentoPorcentual.count

        when:
        CreadorDeCupones.instance.crearPosibleCupon(cliente, restaurant)

        then:
        CuponDescuentoPorcentual.count == cantidadCupones
    }

    void "test crea cupones si no tiene cupones disponibles y tiene tres pedidos en el restaurante"() {
        given:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurant)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoEntregado estado = new EstadoEntregado()
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurant)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)

        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
        EstadoEntregado estado2 = new EstadoEntregado()
        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurant)
        pedido2.agregar(plato, 2)
        pedido2.estado = estado2
        pedido2.save(failOnError: true)

        ModalidadEntrega modalidadEntrega3 = new ModalidadParaLlevar()
        EstadoEntregado estado3 = new EstadoEntregado()
        Pedido pedido3 = new Pedido(cliente, modalidadEntrega3, restaurant)
        pedido3.agregar(plato, 2)
        pedido3.estado = estado3
        pedido3.save(failOnError: true)

        def cantidadCupones = CuponDescuentoPorcentual.count

        when:
        CreadorDeCupones.instance.crearPosibleCupon(cliente, restaurant)

        then:
        CuponDescuentoPorcentual.count == ++cantidadCupones
    }

    void "test no crea cupones si no tiene cupones disponibles y tiene dos pedidos en el restaurante y uno en otro"() {
        given:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Restaurant restaurant2 = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurant)
                .save(failOnError: true)
        Producto plato2 = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurant2)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoEntregado estado = new EstadoEntregado()
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurant)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)

        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
        EstadoEntregado estado2 = new EstadoEntregado()
        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurant2)
        pedido2.agregar(plato2, 2)
        pedido2.estado = estado2
        pedido2.save(failOnError: true)

        ModalidadEntrega modalidadEntrega3 = new ModalidadParaLlevar()
        EstadoEntregado estado3 = new EstadoEntregado()
        Pedido pedido3 = new Pedido(cliente, modalidadEntrega3, restaurant)
        pedido3.agregar(plato, 2)
        pedido3.estado = estado3
        pedido3.save(failOnError: true)

        def cantidadCupones = CuponDescuentoPorcentual.count

        when:
        CreadorDeCupones.instance.crearPosibleCupon(cliente, restaurant)

        then:
        CuponDescuentoPorcentual.count == cantidadCupones
    }


    void "test crea cupones si no tiene un cupón no disponible y tiene tres pedidos nuevos en el restaurante"() {
        given:
        Restaurant restaurant = new Restaurant(nombre: "Don Pepe").save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        CuponDescuento cupon = new CuponDescuentoPorcentual(cliente: cliente, porcentaje: 10, codigo: 'TEST', fecha: new Date(), restaurant: restaurant)
                .save(failOnError: true)
        cliente.addToCupones(cupon).save(failOnError: true)
        sleep(3000)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurant)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoEntregado estado = new EstadoEntregado()
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurant)
        pedido.agregar(plato, 2)
        pedido.agregarCupon(cupon)
        pedido.estado = estado
        pedido.save(failOnError: true)
        assert  !cupon.estaDisponible()
        sleep(3000)

        //Pedido 1 después de aplicar cupón
        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
        EstadoEntregado estado2 = new EstadoEntregado()
        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurant)
        pedido2.agregar(plato, 2)
        pedido2.estado = estado2
        pedido2.save(failOnError: true)
        sleep(3000)

        //Pedido 2 después de aplicar cupón
        ModalidadEntrega modalidadEntrega3 = new ModalidadParaLlevar()
        EstadoEntregado estado3 = new EstadoEntregado()
        Pedido pedido3 = new Pedido(cliente, modalidadEntrega3, restaurant)
        pedido3.agregar(plato, 2)
        pedido3.estado = estado3
        pedido3.save(failOnError: true)
        sleep(3000)

        //Pedido 3 después de aplicar cupón
        ModalidadEntrega modalidadEntrega4 = new ModalidadParaLlevar()
        EstadoEntregado estado4 = new EstadoEntregado()
        Pedido pedido4 = new Pedido(cliente, modalidadEntrega4, restaurant)
        pedido4.agregar(plato, 2)
        pedido4.estado = estado3
        pedido4.save(failOnError: true)

        def cantidadCupones = CuponDescuentoPorcentual.count

        when:
        CreadorDeCupones.instance.crearPosibleCupon(cliente, restaurant)

        then:
        CuponDescuentoPorcentual.count == ++cantidadCupones
    }
}

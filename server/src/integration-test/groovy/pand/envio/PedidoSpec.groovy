package pand.envio


import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import pandenvio.CategoriaPlato
import pandenvio.Cliente
import pandenvio.ClimaLluvioso
import pandenvio.ClimaNoLluvioso
import pandenvio.CuponDescuento
import pandenvio.CuponDescuentoPorcentual
import pandenvio.CuponInvalidoException
import pandenvio.CuponYaUtilizadoException
import pandenvio.CalificacionException
import pandenvio.EstadoEnEntrega
import pandenvio.EstadoEnEspera
import pandenvio.EstadoEnPreparacion
import pandenvio.EstadoEnArmado
import pandenvio.EstadoListo
import pandenvio.EstadoPedido
import pandenvio.FabricaClima
import pandenvio.Item
import pandenvio.Menu
import pandenvio.ModalidadEntrega
import pandenvio.ModalidadParaLlevar
import pandenvio.ModalidadParaRetirar
import pandenvio.Pedido
import pandenvio.Plato
import pandenvio.Producto
import pandenvio.ProductoNoPerteneceAlRestauranteException
import pandenvio.Repartidor
import pandenvio.Restaurant
import pandenvio.Ubicacion
import spock.lang.Specification

@Integration
@Rollback
class PedidoSpec extends Specification {

    Ubicacion juanPerezUbicacion = new Ubicacion(calle: "Paunero", altura: 2030, pisoYDepartamento: null)
    Cliente juanPerez = new Cliente(nombre: "Juan", apellido: "Perez", mail: "juanperez@yahoo.com.ar", ubicacion: juanPerezUbicacion, telefono: "1138465977",cupones:null)

    def setup() {
        Repartidor.executeUpdate('delete from Repartidor')
    }

    void "test precio de un pedido sin productos es 0"() {
        given:
            Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar(), new Restaurant())
        when:
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == 0
    }

    void "test precio de un pedido con productos del mismo restaurante"() {
        given:
        Restaurant restaurant = new Restaurant(nombre:  "Don Juan")
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar(), restaurant)
            Producto plato = new Plato(nombre: 'Alto Guiso', precio: 15, categoria: CategoriaPlato.PLATO, restaurant: restaurant)
            Integer cantidad = 2
        when:
            pedido.agregar(plato, cantidad)
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == plato.getPrecio() * cantidad
    }

    void "test no se puede agregar productos a pedido de un restaurante diferente"() {
        given:
        Restaurant restaurant = new Restaurant(nombre:  "Don Juan")
        Restaurant restaurant2 = new Restaurant(nombre:  "Don José")
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar(), restaurant)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 15, categoria: CategoriaPlato.PLATO, restaurant: restaurant2)
        when:
        pedido.agregar(plato, 2)
        then:
        thrown(ProductoNoPerteneceAlRestauranteException)
    }

    void "test precio de un pedido sin productos con cupon disponible es 0"() {
        given:
            Pedido pedido = new Pedido(juanPerez, new ModalidadParaRetirar(), new Restaurant())
            CuponDescuento cupon = new CuponDescuentoPorcentual(cliente: juanPerez, porcentaje: 10, restaurant: new Restaurant() )
        when:
            pedido.cuponDeDescuento = cupon
            BigDecimal precio = pedido.calcularPrecio()
        then:
            cupon.estaDisponible()
            precio == 0
    }

    void "test precio de un pedido sin productos con cupon con otro pedido beneficiado lanza error"() {
        given:
            Pedido pedido = new Pedido(juanPerez, new ModalidadParaRetirar(), new Restaurant())
            Pedido pedido2 = new Pedido(juanPerez, new ModalidadParaRetirar(), new Restaurant())
            CuponDescuento cupon = new CuponDescuentoPorcentual(cliente: juanPerez, porcentaje: 10, pedidoBeneficiado: pedido2, restaurant: new Restaurant())
        when:
            pedido.cuponDeDescuento = cupon
            pedido.calcularPrecio()
        then:
            thrown CuponYaUtilizadoException
    }

    void "test precio de un pedido con productos con cupon con pedido beneficiado correcto aplica descuento"() {
        given:
            Restaurant restaurant = new Restaurant(nombre:  "Don Juan")
            Pedido pedido = new Pedido(juanPerez, new ModalidadParaRetirar(), restaurant)
            CuponDescuento cupon = new CuponDescuentoPorcentual(cliente: juanPerez, porcentaje: 10, pedidoBeneficiado: pedido, restaurant: restaurant)
            Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurant)
            Producto plato2 = new Plato(nombre: 'Flan', precio: 100, categoria: CategoriaPlato.POSTRE, restaurant: restaurant)
            pedido.agregar(plato, 1)
            pedido.agregar(plato2, 2)
        when:
            pedido.cuponDeDescuento = cupon
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == 360 // (200*1 + 100*2) * (1 - 0.1)
    }

    void "test precio de un pedido con productos y cupon disponible  no aplica si hay menu"() {
        when:
            Restaurant restaurant = new Restaurant(nombre:  "Don Juan")
            Pedido pedido = new Pedido(juanPerez, new ModalidadParaRetirar(), restaurant)
            CuponDescuento cupon = new CuponDescuentoPorcentual(cliente: juanPerez, porcentaje: 10, restaurant: restaurant)
            Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurant)
            Producto menu = new Menu(nombre: 'Viernes', precio: 300, restaurant: restaurant)
            pedido.agregar(plato, 2)
            pedido.agregar(menu, 2)
        then:
            cupon.estaDisponible()
            pedido.agregarCupon(cupon)
            BigDecimal precio = pedido.calcularPrecio()
            precio == 1000 // (200*2 + 300*2)
            !cupon.estaDisponible()
    }

    void "test pedido actualiza bien el estado"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433', cupones: null)
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        CuponDescuento cupon = new CuponDescuentoPorcentual(cliente: cliente, activo: true, porcentaje: 10, codigo: 'ABC', fecha: new Date(), restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaRetirar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoEnPreparacion().save(failOnError: true)
        when:
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.agregarCupon(cupon)
        pedido.estado = estado
        pedido.save(failOnError: true)

        then:
        def pedidoGuardado = Pedido.findById(pedido.id)
        pedidoGuardado.nombreEstado == 'en_preparacion'
        pedidoGuardado.siguienteEstado()
        pedidoGuardado.nombreEstado == 'listo'
        pedidoGuardado.save(failOnError: true)
        def pedidoGuardado2 = Pedido.findById(pedido.id)
        pedidoGuardado2.nombreEstado == 'listo'
    }

    void "test Pedido para llevar asignado a repartidor"() {
        when:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433', cupones: null)
                .save()
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante).save(failOnError: true)
        Repartidor repartidor = new Repartidor("Juan", "9897954", restaurante).save(failOnError: true)
        pedido.asignarA(repartidor)
        then:
        !repartidor.disponible
        pedido.tieneRepartidor()
    }

    void "test pedido esta en modalidad para llevar y NO puedo cambiar a modalidad para entregar porque el pedido ya esta listo"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoListo().save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)
        when:
        pedido.cambiarModalidad()
        then:
        thrown pandenvio.NoSePuedeActualizarProductoException
    }

    void "test pedido esta en modalidad para llevar cambia modalidad a retirar"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoEnArmado estado = new EstadoEnArmado()
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.estado.siguienteEstado(modalidadEntrega)

        pedido.save(failOnError: true)
        when:
        pedido.cambiarModalidad()
        then:
        pedido.modalidadEntrega.class == ModalidadParaRetirar
    }

    void "test pedido esta en modalidad para retirar y cambia a modalidad para llevar"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaRetirar().save(failOnError: true)
        EstadoEnArmado estado = new EstadoEnArmado()
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.estado.siguienteEstado(modalidadEntrega)

        pedido.save(failOnError: true)
        when:
        pedido.cambiarModalidad()
        then:
        pedido.modalidadEntrega.class == ModalidadParaLlevar
    }

    void "test pedido estado siguiente a listo con modalidad para llevar y no hay repartidores es 'en espera'"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoListo().save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)
        when:
        pedido.siguienteEstado()
        then:
        pedido.estado.class == EstadoEnEspera
        pedido.nombreEstado == 'en_espera'
    }

    void "test pedido estado siguiente a listo con modalidad para llevar y hay repartidores es 'en entrega'"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Repartidor repartidor = new  Repartidor("Juan", "9798797", restaurante).save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoListo().save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)
        when:
        pedido.siguienteEstado()
        then:
        pedido.estado.class == EstadoEnEntrega
        pedido.nombreEstado == 'en_entrega'
        !repartidor.disponible
    }

    void "test pedido estado siguiente a listo con modalidad para llevar y no hay repartidores disponibles es 'en espera'"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Repartidor repartidor = new  Repartidor("Juan", "9798797", restaurante)
        repartidor.disponible = false
        repartidor.save(failOnError: true)

        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoListo().save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)
        when:
        pedido.siguienteEstado()
        then:
        pedido.estado.class == EstadoEnEspera
        pedido.nombreEstado == 'en_espera'
        !repartidor.disponible
        repartidor.listaDePedidos.empty
    }

    void "test pedido estado siguiente a listo con modalidad para llevar y hay repartidores en otro restaurante es 'en espera'"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La esquina').save(failOnError: true)
        Restaurant restaurante2 = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)

        Repartidor repartidor = new  Repartidor("Juan", "9798797", restaurante2).save(failOnError: true)

        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoListo().save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)
        when:
        pedido.siguienteEstado()
        then:
        pedido.estado.class == EstadoEnEspera
        pedido.nombreEstado == 'en_espera'
        repartidor.disponible
    }

    void "test pedido acepta cupón del mismo cliente"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La esquina').save(failOnError: true)

        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        CuponDescuento cupon = new CuponDescuentoPorcentual(cliente: cliente, activo: true, porcentaje: 10, codigo: 'ABC', fecha: new Date(), restaurant: restaurante)
                .save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.save(failOnError: true)
        when:
        pedido.agregarCupon(cupon)
        pedido.save(failOnError: true)
        then:
        def pedidoGuardado = Pedido.findById(pedido.id)
        pedidoGuardado.cuponDeDescuento == cupon
    }

    void "test pedido no acepta cupón de otro  cliente"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La esquina').save(failOnError: true)

        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Cliente cliente2 = new Cliente(nombre: 'Pepe', apellido: 'Argento',  mail: 'pepe.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        CuponDescuento cupon = new CuponDescuentoPorcentual(cliente: cliente2, activo: true, porcentaje: 10, codigo: 'ABC', fecha: new Date(), restaurant: restaurante)
                .save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.save(failOnError: true)
        when:
        pedido.agregarCupon(cupon)
        then:
        thrown CuponInvalidoException
    }

    void "test Precio para llevar dentro del rango sin lluvia"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La esquina').save(failOnError: true)

        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 150, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 1)
        pedido.save(failOnError: true)
        when:
        def pedidoGuardado = Pedido.findById(pedido.id)
        def precio = pedidoGuardado.calcularPrecio()
        then:
        pedidoGuardado.clima.class == ClimaNoLluvioso
        pedidoGuardado.enRango
        precio == 150
    }

    void "test Precio para llevar fuera del rango sin lluvia"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La esquina').save(failOnError: true)

        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 150, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.enRango = false
        pedido.agregar(plato, 1)
        pedido.save(failOnError: true)
        when:
        def pedidoGuardado = Pedido.findById(pedido.id)
        def precio = pedidoGuardado.calcularPrecio()
        then:
        pedidoGuardado.clima.class == ClimaNoLluvioso
        !pedidoGuardado.enRango
        precio == 165
    }

    void "test Precio para llevar dentro del rango con lluvia"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La esquina').save(failOnError: true)

        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 150, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante, FabricaClima.crearPara('lluvioso'))
        pedido.agregar(plato, 1)
        pedido.save(failOnError: true)
        when:
        def pedidoGuardado = Pedido.findById(pedido.id)
        def precio = pedidoGuardado.calcularPrecio()
        then:
        pedidoGuardado.clima.class == ClimaLluvioso
        pedidoGuardado.enRango
        precio == 200
    }

    void "test Precio para llevar fuera del rango con lluvia"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La esquina').save(failOnError: true)

        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 150, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante, FabricaClima.crearPara('lluvioso'))
        pedido.agregar(plato, 1)
        pedido.enRango = false
        pedido.save(failOnError: true)
        when:
        def pedidoGuardado = Pedido.findById(pedido.id)
        def precio = pedidoGuardado.calcularPrecio()
        then:
        pedidoGuardado.clima.class == ClimaLluvioso
        !pedidoGuardado.enRango
        precio == 215
    }

        void "test pedido para retirar no puede calificarse"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaRetirar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoListo().save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)
        pedido.siguienteEstado()
        when:
        pedido.setPuntuacionAModalidad(5)
        then:
        thrown CalificacionException
    }

        void "test pedido para llevar no puede calificarse antes de estar entregado"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Repartidor repartidor = new  Repartidor("Juan", "9798797", restaurante).save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoListo().save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)
        pedido.siguienteEstado()
        pedido.save(failOnError: true)
        when:
        pedido.setPuntuacionAModalidad(5)
        then:
        pedido.estado.class == EstadoEnEntrega
        pedido.nombreEstado == 'en_entrega'
        thrown CalificacionException
    }

    void "test pedido para llevar puede calificarse al estar entregado"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Repartidor repartidor = new Repartidor("Juan", "9798797", restaurante).save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle: 'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento', mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoListo().save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)
        pedido.siguienteEstado()
        pedido.siguienteEstado()

        when:
        pedido.setPuntuacionAModalidad(5)
        then:
        pedido.obtenerEstrellas() == 5
    }
    void "test pedido queda en espera si el repartidor está ocupado"() {
        when:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Repartidor repartidor = new  Repartidor("Juan", "9798797", restaurante).save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaLlevar().save(failOnError: true)
        EstadoPedido estado1 = new EstadoListo().save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.estado = estado1
        pedido.save(failOnError: true)

        EstadoPedido estado2 = new EstadoListo().save(failOnError: true)
        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar().save(failOnError: true)
        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurante)
        pedido2.agregar(plato, 2)
        pedido2.estado = estado2
        pedido2.save(failOnError: true)

        then:
        repartidor.disponible
        pedido.siguienteEstado() // EnEntrega
        Repartidor.count == 1
        !repartidor.disponible
        pedido.tieneRepartidor()
        pedido.save(failOnError: true)
        !repartidor.disponible
        repartidor.listaDePedidos.size() == 1
        repartidor.dirty
        pedido2.siguienteEstado() // En Espera
        Repartidor.count == 1
        pedido2.estado.class == EstadoEnEspera
        !pedido2.modalidadEntrega.hayRepartidor()
        !pedido2.tieneRepartidor()
        repartidor.listaDePedidos.size() == 1
    }
}

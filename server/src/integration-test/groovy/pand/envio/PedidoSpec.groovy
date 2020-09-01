package pand.envio


import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import pandenvio.CategoriaPlato
import pandenvio.Cliente
import pandenvio.CuponDescuento
import pandenvio.CuponDescuentoPorcentual
import pandenvio.CuponYaUtilizadoException
import pandenvio.EstadoEnPreparacion
import pandenvio.EstadoPedido
import pandenvio.Menu
import pandenvio.ModalidadEntrega
import pandenvio.ModalidadParaRetirar
import pandenvio.Pedido
import pandenvio.Plato
import pandenvio.Producto
import pandenvio.Restaurant
import pandenvio.Ubicacion
import spock.lang.Specification

@Integration
@Rollback
class PedidoSpec extends Specification {

    void "test precio de un pedido sin productos es 0"() {
        given:
            Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar())
        when:
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == 0
    }

    void "test precio de un pedido con productos"() {
        given:
            Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar())
            Producto plato = new Plato(nombre: 'Alto Guiso', precio: 15, categoria: CategoriaPlato.PLATO)
            Integer cantidad = 2
        when:
            pedido.agregar(plato, cantidad)
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == plato.getPrecio() * cantidad
    }

    void "test precio de un pedido sin productos con cupon activo es 0"() {
        given:
            Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar())
            CuponDescuento cupon = new CuponDescuentoPorcentual(activo: true, porcentaje: 10)
        when:
            pedido.cuponDeDescuento = cupon
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == 0
    }

    void "test precio de un pedido sin productos con cupon inactivo lanza error"() {
        given:
            Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar())
            CuponDescuento cupon = new CuponDescuentoPorcentual(activo: false, porcentaje: 10)
        when:
            pedido.cuponDeDescuento = cupon
            BigDecimal precio = pedido.calcularPrecio()
        then:
            thrown CuponYaUtilizadoException
    }

    void "test precio de un pedido con productos con cupon activo aplica descuento"() {
        given:
            Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar())
            CuponDescuento cupon = new CuponDescuentoPorcentual(activo: true, porcentaje: 10)
            Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO)
            Producto plato2 = new Plato(nombre: 'Flan', precio: 100, categoria: CategoriaPlato.POSTRE)
            pedido.agregar(plato, 1)
            pedido.agregar(plato2, 2)
        when:
            pedido.cuponDeDescuento = cupon
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == 360 // (200*1 + 100*2) * (1 - 0.1)
    }

    void "test precio de un pedido con productos y cupon activo  no aplica si hay menu"() {
        given:
            Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar())
            CuponDescuento cupon = new CuponDescuentoPorcentual(activo: true, porcentaje: 10)
            Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO)
            Producto menu = new Menu(nombre: 'Viernes', precio: 300)
            pedido.agregar(plato, 2)
            pedido.agregar(menu, 2)
        when:
            pedido.cuponDeDescuento = cupon
            BigDecimal precio = pedido.calcularPrecio()
        then:
            precio == 1000 // (200*2 + 300*2)
    }

    void "test pedido actualiza bien el estado"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                .save()
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        CuponDescuento cupon = new CuponDescuentoPorcentual(activo: true, porcentaje: 10, codigo: 'ABC', fecha: new Date())
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaRetirar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoEnPreparacion().save(failOnError: true)
        when:
        Pedido pedido = new Pedido(cliente, modalidadEntrega)
        pedido.agregar(plato, 2)
        pedido.cuponDeDescuento = cupon
        pedido.save(failOnError: true)

        then:
        def pedidoGuardado = Pedido.findById(pedido.id)
        pedidoGuardado.siguienteEstado()
        pedidoGuardado.nombreEstado == 'en_preparacion'
        pedidoGuardado.save(failOnError: true)
        def pedidoGuardado2 = Pedido.findById(pedido.id)
        pedidoGuardado2.nombreEstado == 'en_preparacion'
    }
}

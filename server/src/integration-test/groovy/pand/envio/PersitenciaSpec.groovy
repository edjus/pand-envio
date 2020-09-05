package pand.envio


import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import pandenvio.CategoriaPlato
import pandenvio.Cliente
import pandenvio.CuponDescuento
import pandenvio.CuponDescuentoPorcentual
import pandenvio.EstadoEnPreparacion
import pandenvio.EstadoPedido
import pandenvio.Item
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
class PersitenciaSpec extends Specification {

    Ubicacion ubicacion

    def setup() {
        Item.executeUpdate('delete from Item')
        Pedido.executeUpdate('delete from Pedido')
    }

    void "test Item se guarda bien"() {
        when:
            Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
            Menu menuConPLatos = new Menu(nombre: 'Viernes', precio: 400, restaurant: restaurante)
                    .addToPlatos(new Plato(nombre:'Milanesa con pure', precio: 350, categoria: CategoriaPlato.PLATO, restaurant:  restaurante))
                    .save(failOnError: true)
            Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com',
                    ubicacion: new Ubicacion(calle: "av alala", altura: 646), telefono: '11-5555-4433')
                .save()
            Pedido pedido = new Pedido(cliente, new ModalidadParaRetirar(), restaurante).save(failOnError: true)
            Item item = new Item(menuConPLatos, 2, pedido).save(failOnError: true)
        then:
            Item.count == 1
            def itemGuardado = Item.findById(item.id)
            itemGuardado ==  item
            itemGuardado.producto == menuConPLatos
    }


    void "test pedido se guarda bien"() {
        given:
            Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
            Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
            Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433')
                    .save()
            Producto menu = new Menu(nombre: 'Viernes', precio: 400, restaurant: restaurante)
                    .addToPlatos(new Plato(nombre:'Milanesa con pure', precio: 350, categoria: CategoriaPlato.PLATO, restaurant:  restaurante))
                    .save(failOnError: true)
            Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                        .save(failOnError: true)
            CuponDescuento cupon = new CuponDescuentoPorcentual(activo: true, porcentaje: 10, codigo: 'ABC', fecha: new Date())
                    .save(failOnError: true)
            ModalidadEntrega modalidadEntrega = new ModalidadParaRetirar()
                    .save(failOnError: true)
            EstadoPedido estado = new EstadoEnPreparacion().save(failOnError: true)
        when:
            Pedido pedido = new Pedido(cliente, modalidadEntrega, restaurante)
            pedido.agregar(plato, 2)
            pedido.agregar(menu, 2)
            pedido.cuponDeDescuento = cupon
            pedido.save(failOnError: true)
            Pedido pedido2 = new Pedido(cliente, modalidadEntrega, restaurante)
            pedido2.agregar(plato, 2)
            pedido2.estado = estado
            pedido2.save(failOnError: true)

        then:
            Pedido.count == 2
            def pedidoGuardado = Pedido.findById(pedido2.id)
            pedidoGuardado == pedido2
            pedidoGuardado.cliente == cliente
            pedidoGuardado.nombreEstado == estado.nombre
            pedidoGuardado.modalidadEntrega == modalidadEntrega
            pedidoGuardado.estado == estado
            pedidoGuardado.items.size() == 1
    }
}

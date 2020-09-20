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
import pandenvio.EstadoEnEntrega
import pandenvio.EstadoEnEspera
import pandenvio.EstadoEnPreparacion
import pandenvio.EstadoListo
import pandenvio.EstadoPedido
import pandenvio.FabricaClima
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
class RepartidorSpec extends Specification {

    Ubicacion juanPerezUbicacion = new Ubicacion(calle: "Paunero", altura: 2030, pisoYDepartamento: null)
    Cliente juanPerez = new Cliente(nombre: "Juan", apellido: "Perez", mail: "juanperez@yahoo.com.ar", ubicacion: juanPerezUbicacion, telefono: "1138465977",cupones:null)


    void "test repartidor tiene un pedido"() {
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
        pedido.siguienteEstado()
        pedido.save(failOnError: true)
        modalidadEntrega.save(failOnError: true)
        
        when:
                pedido.save(failOnError: true)
        then:
                (repartidor.listaDePedidos).size() == 1
    }

    void "test repartidor tiene un pedido entregado y su sueldo es su sueldo base mas adicional'"() {
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
        pedido.siguienteEstado()
        pedido.save(failOnError: true)
        modalidadEntrega.save(failOnError: true)
        
        when:
                pedido.save(failOnError: true)
        then:
                pedido.nombreEstado == "entregado"
                (repartidor.listaDePedidos).size() == 1
                repartidor.liquidarSueldoFinal()
                repartidor.sueldo.sueldoFinal = 30050
    }

}

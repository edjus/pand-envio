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
import pandenvio.EstadoNoEntregado
import pandenvio.EstadoEnPreparacion
import pandenvio.EstadoEntregado
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
        when:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.siguienteEstado() // EstadoEnEntrega
        then:
            !repartidor.disponible
            pedido.siguienteEstado() // EstadoEntregado
            pedido.save(failOnError: true)
            pedido.estado.class == EstadoEntregado
            repartidor.disponible
            (repartidor.listaDePedidos).size() == 1
    }

        void "test repartidor tiene dos pedidos"() {
        when:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
            !repartidor.disponible
            pedido.siguienteEstado() // Entregado
            pedido.save(failOnError: true)
            repartidor.disponible
            repartidor.listaDePedidos.size() == 1

            pedido2.siguienteEstado() // En Entrega
            !repartidor.disponible
            pedido2.siguienteEstado() // Entregado
            pedido.save(failOnError: true)
            repartidor.disponible
            (repartidor.listaDePedidos).size() == 2
    }

    void "test repartidor tiene un pedido entregado  NO LLUVIOSO y su sueldo es su sueldo base mas adicional'"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
                repartidor.sueldo.sueldoFinal == 30050
    }

    
        void "test repartidor tiene DOS pedidos entregados  NO LLUVIOSO y su sueldo es su sueldo base mas adicional"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.save(failOnError: true)

        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
                .save(failOnError: true)

        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurante)
        pedido2.agregar(plato, 2)
        pedido2.estado = estado
        pedido2.save(failOnError: true)
        pedido2.siguienteEstado()
        pedido2.siguienteEstado()
        pedido2.save(failOnError: true)

        modalidadEntrega.save(failOnError: true)
        
        when:
                pedido.save(failOnError: true)
        then:
                repartidor.liquidarSueldoFinal()
                repartidor.sueldo.sueldoFinal == 30100
                }


        void "test repartidor tiene un pedido entregado  LLUVIOSO y su sueldo es su sueldo base mas adicional'"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.setClima(FabricaClima.crearPara("lluvioso"))
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
                repartidor.sueldo.sueldoFinal == 30100 // 30000 sueldo base + 50 pedido completado + 50 adicional lluvia
    }


        void "test repartidor tiene DOS pedidos entregados uno NO LLUVIOSO y otro LLUVIOSO y su sueldo es su sueldo base mas adicional"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.setClima(FabricaClima.crearPara("lluvioso"))

        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)
        pedido.siguienteEstado()
        pedido.siguienteEstado()
        pedido.save(failOnError: true)
        modalidadEntrega.save(failOnError: true)
        pedido.save(failOnError: true)

        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
                .save(failOnError: true)

        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurante)
        pedido2.agregar(plato, 2)
        pedido2.estado = estado
        pedido2.save(failOnError: true)
        pedido2.siguienteEstado()
        pedido2.siguienteEstado()
        pedido2.save(failOnError: true)

        modalidadEntrega.save(failOnError: true)
        
        when:
                pedido.save(failOnError: true)
        then:
                repartidor.liquidarSueldoFinal()
                repartidor.sueldo.sueldoFinal == 30150
                }


        void "test repartidor tiene DOS pedidos entregados LLUVIOSOS y su sueldo es su sueldo base mas adicional"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.setClima(FabricaClima.crearPara("lluvioso"))

        pedido.agregar(plato, 2)
        pedido.estado = estado
        pedido.save(failOnError: true)
        pedido.siguienteEstado()
        pedido.siguienteEstado()
        pedido.save(failOnError: true)
        modalidadEntrega.save(failOnError: true)
        pedido.save(failOnError: true)

        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
                .save(failOnError: true)

        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurante)
        pedido2.setClima(FabricaClima.crearPara("lluvioso"))

        pedido2.agregar(plato, 2)
        pedido2.estado = estado
        pedido2.save(failOnError: true)
        pedido2.siguienteEstado()
        pedido2.siguienteEstado()
        pedido2.save(failOnError: true)

        modalidadEntrega.save(failOnError: true)
        
        when:
                pedido.save(failOnError: true)
        then:
                repartidor.liquidarSueldoFinal()
                repartidor.sueldo.sueldoFinal == 30200
        }

        void "test repartidor tiene tres pedidos entregados y todos con menos de 3 estrellas entonces cobra 50 pesos menos del adicional"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.save(failOnError: true)
        pedido.setPuntuacionAModalidad(2)

        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
                .save(failOnError: true)

        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurante)

        pedido2.agregar(plato, 2)
        pedido2.estado = estado
        pedido2.save(failOnError: true)
        pedido2.siguienteEstado()
        pedido2.siguienteEstado()
        pedido2.save(failOnError: true)
        pedido2.setPuntuacionAModalidad(2)

        modalidadEntrega.save(failOnError: true)
        pedido.save(failOnError: true)
        

        
        ModalidadEntrega modalidadEntrega3 = new ModalidadParaLlevar()
                .save(failOnError: true)

        Pedido pedido3 = new Pedido(cliente, modalidadEntrega3, restaurante)

        pedido3.agregar(plato, 2)
        pedido3.estado = estado
        pedido3.save(failOnError: true)
        pedido3.siguienteEstado()
        pedido3.siguienteEstado()
        pedido3.setPuntuacionAModalidad(2)

        pedido3.save(failOnError: true)

        modalidadEntrega.save(failOnError: true)

        when:
                pedido.save(failOnError: true)
        then:
                repartidor.liquidarSueldoFinal()
                repartidor.sueldo.sueldoFinal == 30100 //30000 sueldo base + 150 por 3 pedidos entregados - 50 por mala puntuacion en 3 pedidos
        }


        
        void "test repartidor tiene un pedido no entregado, se lo penaliza con 50 pesos en el adicional pero como no tiene, no se le resta nada"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.save(failOnError: true)
        pedido.setEstado(new EstadoNoEntregado())

        when:
                pedido.save(failOnError: true)
        then:
                repartidor.liquidarSueldoFinal()
                repartidor.sueldo.sueldoFinal == 30000 
        }

        void "test repartidor tiene un pedido no entregado, se lo penaliza con 50 pesos en el adicional"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.save(failOnError: true)
        pedido.setEstado(new EstadoNoEntregado())

        

        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
                .save(failOnError: true)

        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurante)

        pedido2.agregar(plato, 2)
        pedido2.estado = estado
        pedido2.save(failOnError: true)
        pedido2.siguienteEstado()
        pedido2.siguienteEstado()
        pedido2.save(failOnError: true)
        pedido2.setPuntuacionAModalidad(2)

        modalidadEntrega.save(failOnError: true)
        pedido.save(failOnError: true)
        
        ModalidadEntrega modalidadEntrega3 = new ModalidadParaLlevar()
                .save(failOnError: true)

        Pedido pedido3 = new Pedido(cliente, modalidadEntrega3, restaurante)

        pedido3.agregar(plato, 2)
        pedido3.estado = estado
        pedido3.save(failOnError: true)
        pedido3.siguienteEstado()
        pedido3.siguienteEstado()
        pedido3.setPuntuacionAModalidad(2)

        pedido3.save(failOnError: true)

        modalidadEntrega.save(failOnError: true)

        when:
                pedido.save(failOnError: true)
        then:
                repartidor.liquidarSueldoFinal()
                repartidor.sueldo.sueldoFinal == 30050 //30000 sueldo base + 50*2 de pedido entregado - 50 del adicional por pedido NO entregado 
        }

        void "test repartidor tiene mas de 3 entregas no entregadas, su sueldo baja un 20%"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.save(failOnError: true)
        pedido.setEstado(new EstadoNoEntregado())

        

        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
                .save(failOnError: true)

        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurante)

        pedido2.agregar(plato, 2)
        pedido2.estado = estado
        pedido2.save(failOnError: true)
        pedido2.siguienteEstado()
        pedido2.siguienteEstado()
        pedido2.save(failOnError: true)
        pedido2.setEstado(new EstadoNoEntregado())

        modalidadEntrega.save(failOnError: true)
        pedido.save(failOnError: true)
        
        Pedido pedido3 = new Pedido(cliente, modalidadEntrega2, restaurante)

        pedido3.agregar(plato, 2)
        pedido3.estado = estado
        pedido3.save(failOnError: true)
        pedido3.siguienteEstado()
        pedido3.siguienteEstado()
        pedido3.setEstado(new EstadoNoEntregado())
        pedido3.save(failOnError: true)

        Pedido pedido4 = new Pedido(cliente, modalidadEntrega2, restaurante)

        pedido4.agregar(plato, 2)
        pedido4.estado = estado
        pedido4.save(failOnError: true)
        pedido4.siguienteEstado()
        pedido4.siguienteEstado()
        pedido4.setEstado(new EstadoNoEntregado())
        pedido4.save(failOnError: true)


        when:
                pedido.save(failOnError: true)
        then:
                repartidor.liquidarSueldoFinal()
                repartidor.sueldo.sueldoFinal == 24000 //30000 sueldo base - 30000*0.2 
        }

        void "test repartidor tiene mas de 4 entregas perfectas entonces recibe el bono por pedidos perfectos"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.save(failOnError: true)
        pedido.setPuntuacionAModalidad(5)
        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurante)
        pedido2.agregar(plato, 2)
        pedido2.estado = estado
        pedido2.save(failOnError: true)
        pedido2.siguienteEstado()
        pedido2.siguienteEstado()
        pedido2.save(failOnError: true)
        pedido2.setPuntuacionAModalidad(5)
        modalidadEntrega.save(failOnError: true)
        pedido2.save(failOnError: true)
        ModalidadEntrega modalidadEntrega3 = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido3 = new Pedido(cliente, modalidadEntrega3, restaurante)
        pedido3.agregar(plato, 2)
        pedido3.estado = estado
        pedido3.save(failOnError: true)
        pedido3.siguienteEstado()
        pedido3.siguienteEstado()
        pedido3.save(failOnError: true)
        pedido3.setPuntuacionAModalidad(5)
         ModalidadEntrega modalidadEntrega4 = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido4 = new Pedido(cliente, modalidadEntrega4, restaurante)
        pedido4.agregar(plato, 2)
        pedido4.estado = estado
        pedido4.save(failOnError: true)
        pedido4.siguienteEstado()
        pedido4.siguienteEstado()
        pedido4.save(failOnError: true)
        pedido4.setPuntuacionAModalidad(5)
        when:
                pedido.save(failOnError: true)
        then:
                repartidor.liquidarSueldoFinal()
                repartidor.sueldo.sueldoFinal == 30275 //30000 sueldo base + 200 de adicional por pedido entregado + 75 bono calificacion perfecta
        }


        void "test repartidor tiene mas de 4 entregas perfectas entonces recibe el bono por pedidos perfectos pero tiene 4 pedidos sin entregar y es penalizado"() {
        given:
        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: "Av 123").save(failOnError: true)
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
        pedido.save(failOnError: true)
        pedido.setPuntuacionAModalidad(5)
        ModalidadEntrega modalidadEntrega2 = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido2 = new Pedido(cliente, modalidadEntrega2, restaurante)
        pedido2.agregar(plato, 2)
        pedido2.estado = estado
        pedido2.save(failOnError: true)
        pedido2.siguienteEstado()
        pedido2.siguienteEstado()
        pedido2.save(failOnError: true)
        pedido2.setPuntuacionAModalidad(5)
        modalidadEntrega.save(failOnError: true)
        pedido2.save(failOnError: true)
        ModalidadEntrega modalidadEntrega3 = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido3 = new Pedido(cliente, modalidadEntrega3, restaurante)
        pedido3.agregar(plato, 2)
        pedido3.estado = estado
        pedido3.save(failOnError: true)
        pedido3.siguienteEstado()
        pedido3.siguienteEstado()
        pedido3.save(failOnError: true)
        pedido3.setPuntuacionAModalidad(5)
        ModalidadEntrega modalidadEntrega4 = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido4 = new Pedido(cliente, modalidadEntrega4, restaurante)
        pedido4.agregar(plato, 2)
        pedido4.estado = estado
        pedido4.save(failOnError: true)
        pedido4.siguienteEstado()
        pedido4.siguienteEstado()
        pedido4.save(failOnError: true)
        pedido4.setPuntuacionAModalidad(5)
        ModalidadEntrega modalidadEntrega5 = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido5 = new Pedido(cliente, modalidadEntrega5, restaurante)
        pedido5.agregar(plato, 2)
        pedido5.estado = estado
        pedido5.save(failOnError: true)
        pedido5.siguienteEstado()
        pedido5.siguienteEstado()
        pedido5.save(failOnError: true)
        pedido5.setPuntuacionAModalidad(5)
        pedido5.setEstado(new EstadoNoEntregado())
        ModalidadEntrega modalidadEntrega6 = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido6 = new Pedido(cliente, modalidadEntrega6, restaurante)
        pedido6.agregar(plato, 2)
        pedido6.estado = estado
        pedido6.save(failOnError: true)
        pedido6.siguienteEstado()
        pedido6.siguienteEstado()
        pedido6.save(failOnError: true)
        pedido6.setPuntuacionAModalidad(5)
        pedido6.setEstado(new EstadoNoEntregado())
        ModalidadEntrega modalidadEntrega7 = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido7 = new Pedido(cliente, modalidadEntrega7, restaurante)
        pedido7.agregar(plato, 2)
        pedido7.estado = estado
        pedido7.save(failOnError: true)
        pedido7.siguienteEstado()
        pedido7.siguienteEstado()
        pedido7.save(failOnError: true)
        pedido7.setPuntuacionAModalidad(5)
        pedido7.setEstado(new EstadoNoEntregado())
        ModalidadEntrega modalidadEntrega8 = new ModalidadParaLlevar()
                .save(failOnError: true)
        Pedido pedido8 = new Pedido(cliente, modalidadEntrega8, restaurante)
        pedido8.agregar(plato, 2)
        pedido8.estado = estado
        pedido8.save(failOnError: true)
        pedido8.siguienteEstado()
        pedido8.siguienteEstado()
        pedido8.save(failOnError: true)
        pedido8.setPuntuacionAModalidad(5)
        pedido8.setEstado(new EstadoNoEntregado())

        when:
                pedido.save(failOnError: true)
        then:
                repartidor.liquidarSueldoFinal()
                repartidor.sueldo.sueldoFinal == 24075 //30000 sueldo base + 200 de adicional por pedido entregado + 75 bono calificacion perfecta - sueldo base * 0.2 - 200 de adicional
        }
}

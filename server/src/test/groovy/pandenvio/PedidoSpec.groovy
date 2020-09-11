package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class PedidoSpec extends Specification implements DomainUnitTest<Pedido> {

    void "test Pedido se crea con fecha actual y con descuento Nulo"() {
        when:
            Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), new Restaurant())
        then:
            pedido.validate()
            pedido.fecha.date.toString() == (new Date()).date.toString()
            pedido.cuponDeDescuento.class == CuponDescuentoNulo
            pedido.estado.class == EstadoEnArmado
    }

    void "test cliente de un Pedido no puede ser null"() {
        when:
            Pedido pedido = new Pedido(null, new ModalidadParaRetirar(), new Restaurant())
        then:
            !pedido.validate()
    }

    void "test modalidad entrega de un Pedido no puede ser null"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), null, new Restaurant())
        then:
        !pedido.validate()
    }


    void "test restaurant de un Pedido no puede ser null"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar(), null)
        then:
        !pedido.validate()
    }

    void "test Pedido el siguiente estado es correcto según el estado el estado actual"() {
        when:
        Restaurant restaurant = new Restaurant()
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), restaurant)
        pedido.agregar(new Plato(restaurant: restaurant), 3)
        pedido.siguienteEstado()
        then:
        pedido.estado.class == EstadoRecibido
        pedido.nombreEstado == 'recibido'
    }

    void "test Pedido sin items no actualiza su estado"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), new Restaurant())
        pedido.siguienteEstado()
        then:
        thrown PedidoNoTieneItemsException
    }

    void "test cancelar Pedido se cancela según estado"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), new Restaurant())
        pedido.cancelar()
        then:
        pedido.estado.class == EstadoCancelado.class
        pedido.nombreEstado == 'cancelado'
    }

    void "test Pedido para retirar no tiene repartidor"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar(), new Restaurant())
        then:
        !pedido.tieneRepartidor()
    }

    void "test Pedido para llevar sin repartidor"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), new Restaurant())
        then:
        !pedido.tieneRepartidor()
    }

    void "test Pedido para llevar con repartidor"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(repartidor: new Repartidor("Juan", "9897954", new Restaurant())), new Restaurant())
        then:
        pedido.tieneRepartidor()
    }

    void "test Pedido para llevar asignado a repartidor"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), new Restaurant())
        Repartidor repartidor = new Repartidor("Juan", "9897954", new Restaurant())
        pedido.asignarA(repartidor)
        then:
        pedido.tieneRepartidor()
        !repartidor.disponible
    }

    void "test Pedido para retirar asignado a repartidor no tiene repartidor"() {
        when:
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaRetirar(), new Restaurant())
        Repartidor repartidor = new Repartidor("Juan", "9897954", new Restaurant())
        pedido.asignarA(repartidor)
        then:
        !pedido.tieneRepartidor()
        repartidor.disponible
    }

    void "test Pedido agregar producto crea item con producto"() {
        when:
        Restaurant restaurant = new Restaurant()
        Plato plato = new Plato(restaurant: restaurant)
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), restaurant)
        pedido.agregar(plato, 3)
        then:
        pedido.items.size() == 1
        def item = pedido.items.find {i -> i.producto == plato}
        item.cantidad == 3
    }

    void "test Pedido para agregar varias veces el mismo producto actualiza cantidad en item"() {
        when:
        Restaurant restaurant = new Restaurant()
        Plato plato = new Plato(restaurant: restaurant)
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), restaurant)
        pedido.agregar(plato, 3)
        pedido.agregar(plato, 5)
        then:
        pedido.items.size() == 1
        def item = pedido.items.find {i -> i.producto == plato}
        item.cantidad == 8
    }

    void "test Pedido remover producto"() {
        when:
        Restaurant restaurant = new Restaurant()
        Plato plato = new Plato(restaurant: restaurant)
        Menu menu = new Menu(restaurant: restaurant)
        Pedido pedido = new Pedido(new Cliente(), new ModalidadParaLlevar(), restaurant)
        pedido.agregar(plato, 3)
        pedido.agregar(menu, 2)
        then:
        pedido.items.size() == 2
        def item = pedido.items.find {i -> i.producto == plato}
        item.cantidad == 3
        pedido.removerProducto(plato)
        pedido.items.size() == 1
        def item2 = pedido.items.find {i -> i.producto == plato}
        !item2
    }
}

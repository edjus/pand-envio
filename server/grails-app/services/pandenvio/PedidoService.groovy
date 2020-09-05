package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class PedidoService {

    ClienteService clienteService
    ModalidadEntregaService modalidadEntregaService
    RestaurantService restaurantService

    def crearPedido(Long clienteId, String modalidad, Long restauranteId) {
        Cliente cliente = clienteService.obtenerCliente(clienteId)
        if (!cliente) {
            throw new RuntimeException('El cliente es inválido')
        }

        ModalidadEntrega modalidadEntrega = modalidadEntregaService.obtenerModalidadPorNombre(modalidad)
        if (!modalidadEntrega) {
            throw new RuntimeException('La modalidad es inválida')
        }

        Restaurant restaurant = restaurantService.obtenerRestaurante(restauranteId)
        if (!restaurant) {
            throw new RuntimeException('El resturante es inválido')
        }

        new Pedido(cliente, modalidadEntrega, restaurant).save(failOnError: true)
    }

    def siguienteEstadoPedido(Long pedidoId) {
        Pedido pedido = Pedido.findById(pedidoId)
        if (!pedido){
            throw new RuntimeException('El pedido no es válido')
        }
        pedido.siguienteEstado()
        pedido.save(failOnError: true, flush: true)
    }

    def cancelar(Long pedidoId) {
        Pedido pedido = Pedido.findById(pedidoId)
        if (!pedido){
            throw new RuntimeException('El pedido no es válido')
        }
        pedido.cancelar()
        pedido.save(failOnError: true, flush: true)
    }

    def agregarItem(Long pedidoId, Long productoId, Integer cantidad) {
        Pedido pedido = Pedido.findById(pedidoId)
        if (!pedido){
            throw new RuntimeException('El pedido no es válido')
        }
        Producto producto = Producto.findById(productoId)
        if (!producto){
            throw new RuntimeException('El producto no es válido')
        }
        pedido.agregar(producto, cantidad)
        pedido.save(failOnError: true, flush: true)
    }
}

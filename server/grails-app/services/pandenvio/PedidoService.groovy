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
            throw new RuntimeException('El resturante es invalido')
        }

        if (tienePedidoEnArmado(cliente)){
            throw new RuntimeException('No puede agregar otro pedido ya tiene uno en armando')
        }

        Clima clima = ClimaConfigurableService.instance.climaActual
        new Pedido(cliente, modalidadEntrega, restaurant, clima).save(failOnError: true)
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

    def agregarProducto(Long pedidoId, Long productoId, Integer cantidad) {
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

    boolean tienePedidoEnArmado(Cliente cliente) {
        def pedidos = Pedido.findAllWhere(cliente: cliente, nombreEstado: 'en_armado')
        !pedidos.empty
    }

    List<Pedido> buscarPedidosPorRestaurante(Long restauranteId) {
        Restaurant restaurante = restaurantService.obtenerRestaurante(restauranteId)
        Pedido.findAllWhere(restaurant: restaurante)
    }

    int buscarPedidosPorRepartidor(Repartidor repartidor) {
        List<ModalidadParaLlevar> modalidades = ModalidadParaLlevar.findAllWhere(repartidor : repartidor)
        int cantidadPedidos = 0;
        modalidades.each { modalidad ->
            cantidadPedidos = cantidadPedidos + Pedido.findAllWhere(modalidadEntrega: modalidad).size()
        }
        return cantidadPedidos;
    }

    List<Pedido> pedidoActual(Long clienteId) {
        Cliente cliente = clienteService.obtenerCliente(clienteId)
        if (!cliente) {
            throw new RuntimeException('El cliente es inválido')
        }

        Pedido.findAllWhere(cliente: cliente, nombreEstado: 'en_armado')
    }

    def removerProducto(Long pedidoId, Long productoId) {
        Pedido pedido = Pedido.findById(pedidoId)
        if (!pedido){
            throw new RuntimeException('El pedido no es válido')
        }
        Producto producto = Producto.findById(productoId)
        if (!producto){
            throw new RuntimeException('El producto no es válido')
        }
        pedido.removerProducto(producto)
        pedido.save(failOnError: true, flush: true)
    }

    def actualizarProducto(Long pedidoId, Long productoId, Integer cantidad) {
        Pedido pedido = Pedido.findById(pedidoId)
        if (!pedido){
            throw new RuntimeException('El pedido no es válido')
        }
        Producto producto = Producto.findById(productoId)
        if (!producto){
            throw new RuntimeException('El producto no es válido')
        }

        pedido.actualizarCantidad(producto, cantidad)
        pedido.save(failOnError: true, flush: true)
    }

    def calificarPedido(Long pedidoId, Integer calificacionAIngresar) {
        Pedido pedido = Pedido.findById(pedidoId)
        if (!pedido){
            throw new RuntimeException('El pedido no es válido no existe')
        }

        ModalidadParaLlevar modalidad = (ModalidadParaLlevar)pedido.modalidadEntrega

        modalidad.agregarPuntuacion(calificacionAIngresar)


        Puntuacion puntuacion = modalidad.puntuacion


        puntuacion.estrellas = calificacionAIngresar

        puntuacion.save(failOnError: true, flush: true)
        pedido.save(failOnError: true, flush: true)
        return puntuacion
    }

    def obtenerPuntuacion(Long pedidoId) {
        Pedido pedido = Pedido.findById(pedidoId)
        Puntuacion puntuacion = Puntuacion.findById(pedidoId)
        return puntuacion

    }

    def cambiarRango(Long pedidoId) {
        Pedido pedido = Pedido.findById(pedidoId)
        if (!pedido){
            throw new RuntimeException('El pedido no es válido')
        }
        pedido.enRango = !pedido.enRango
        pedido.save(failOnError: true, flush: true)
    }

    def cambiarModalidad(Long pedidoId) {
        Pedido pedido = Pedido.findById(pedidoId)
        ModalidadEntrega modalidad = pedido.modalidadEntrega
        pedido.cambiarModalidad()
        modalidad.save(failOnError: true, flush: true)
        pedido.save(failOnError: true, flush: true)
    }

    def agregarCupon(Long pedidoId, String codigoCupon) {
        Pedido pedido = Pedido.findById(pedidoId)
        if (!pedido){
            throw new RuntimeException('El pedido no es válido')
        }
        def cupon = CuponDescuentoPorcentual.findByCodigo(codigoCupon)
        if (!cupon){
            throw new RuntimeException('El cupón no es válido')
        }
        pedido.agregarCupon(cupon)
        pedido.save(failOnError: true, flush: true)
    }
}

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

        if (tienePedidoEnArmado(cliente)){
            throw new RuntimeException('No puede agregar otro pedido ya tiene uno en armando')
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

    
    def cambiarModalidadDeProducto(Long pedidoId) {

        Pedido pedido = Pedido.findById(pedidoId)

        if(pedido.puedeCambiarModalidad()){
            if (pedido.modalidadEntrega.nombre == "para_retirar"){
                pedido.modalidadEntrega  = modalidadEntregaService.obtenerModalidadPorNombre("para_llevar")
            }
            else{
                pedido.modalidadEntrega  = modalidadEntregaService.obtenerModalidadPorNombre("para_retirar")
            }
        }
        else{
            throw new RuntimeException('El producto no puede cambiar la modalidad en este estado')
        }
        pedido.save(failOnError: true, flush: true)
    }

    def agregarCupon(Long pedidoId, String codigoCupon) {
        Pedido pedido = Pedido.findById(pedidoId)
        if (!pedido){
            throw new RuntimeException('El pedido no es válido')
        }
        CuponDescuento cupon = CuponDescuento.findByCodigo(codigoCupon)
        if (!cupon){
            throw new RuntimeException('El cupón no es válido')
        }
        println("CUPON: ${cupon.codigo} - ${cupon.activo}")
        pedido.agregarCupon(cupon)
        pedido.save(failOnError: true, flush: true)

        println("CUPON: ${pedido.cuponDeDescuento.codigo} - ${pedido.cuponDeDescuento.activo}")
        println("CUPON: ${cupon.codigo} - ${cupon.activo}")

        
    }
}

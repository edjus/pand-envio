package pandenvio

import grails.gorm.transactions.Transactional

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

@Transactional(readOnly = true)
class PedidoController {
	static responseFormats = ['json', 'xml']

    PedidoService pedidoService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Pedido.list(params), model:[pedidoCount: Pedido.count()]
    }

    def show(Pedido pedido) {
        if (pedido == null) {
            render status:404
        }
        else {
            return [pedido: pedido]
        }
    }





    /*
    POST: Crear un pedido
    - cliente_id: long con el id de un cliente existente
    - restaurant_id: long con el id de un restaurante existente
    - modalidad: string puede ser 'para_llevar', 'para_retirar'.
    */
    @Transactional
    def save() {
        try {
            Long clienteId = request.JSON.cliente_id
            String modalidad = request.JSON.modalidad
            Long resturanteId = request.JSON.restaurant_id
            Pedido pedido = pedidoService.crearPedido(clienteId, modalidad, resturanteId)
            respond([pedido: pedido], status: OK)
        } catch (RuntimeException e) {
            respond e.message, status: BAD_REQUEST
        }
    }

    @Transactional
    def actualizarEstado(Long pedidoId) {
        try {
            Pedido pedido = pedidoService.siguienteEstadoPedido(pedidoId)
            respond([pedido: pedido], status: OK)
        } catch (RuntimeException e) {
            respond e.message, status: BAD_REQUEST
        }
    }

    @Transactional
    def cancelar(Long pedidoId) {
        try {
            Pedido pedido = pedidoService.cancelar(pedidoId)
            respond([pedido: pedido], status: OK)
        } catch (RuntimeException e) {
            respond e.message, status: BAD_REQUEST
        }
    }

    @Transactional
    def buscarPedidosPorRestaurante(Long restaurantId) {
        try {
            List<Pedido> pedidos = pedidoService.buscarPedidosPorRestaurante(restaurantId)
            if(pedidos.size() == 0){
                respond([],status: OK)
            }
            else{
                respond(pedidos, status: OK)
            }
        } catch (RuntimeException e) {
            respond e.message, status: BAD_REQUEST
        }
    }


    /*
    POST: Agregar item a pedido
    - producto_id: long con el id de un pruducto existente del mismo restaurant del pedido
    - cantidad: número con la cantidad del producto a agregar al pedido (min 1)
    */
    @Transactional
    def agregarProducto(Long pedidoId) {
        try {
            Long productoId = request.JSON.producto_id
            Integer cantidad = request.JSON.cantidad
            Pedido pedido = pedidoService.agregarProducto(pedidoId, productoId, cantidad)
            respond([pedido: pedido], status: OK)
        } catch (RuntimeException e) {
            respond e.message, status: BAD_REQUEST
        }
    }

    def pedidoActual(Long clienteId){
        try {
            def pedidos = pedidoService.pedidoActual(clienteId)
            if (pedidos.empty){
                respond pedidos.empty, status: NOT_FOUND
            } else{
                respond([pedido: pedidos.first()], status: OK)
            }
        } catch (RuntimeException e) {
            respond e.message, status: BAD_REQUEST
        }
    }

    @Transactional
    def cambiarRango(Long pedidoId) {
        try {
            Pedido pedido = pedidoService.cambiarRango(pedidoId)

            respond([pedido: pedido], status: OK)
        } catch (RuntimeException e) {
            respond ([e.message], status: BAD_REQUEST)
        }
    }

    @Transactional
    def cambiarModalidad(Long pedidoId) {

            Pedido pedido = pedidoService.cambiarModalidad(pedidoId)

            respond([pedido: pedido], status: OK)

    }


    @Transactional
    def actualizarProducto(Long pedidoId, Long productoId) {
        try {
            Integer cantidad = request.JSON.cantidad
            Pedido pedido = pedidoService.actualizarProducto(pedidoId, productoId, cantidad)
            respond([pedido:  pedido], status: OK)
        } catch (RuntimeException e) {
            respond e.message, status: BAD_REQUEST
        }
    }

    @Transactional
    def removerProducto(Long pedidoId, Long productoId) {
        try {
            Pedido pedido = pedidoService.removerProducto(pedidoId, productoId)
            respond([pedido: pedido], status: OK)
        } catch (RuntimeException e) {
            respond e.message, status: BAD_REQUEST
        }
    }

    @Transactional
    def agregarCupon(Long pedidoId) {
        try {
            String codigo = request.JSON.codigo
            Pedido pedido = pedidoService.agregarCupon(pedidoId, codigo)
            respond([pedido: pedido], status: OK)
        } catch (RuntimeException e) {
            respond e.message, status: BAD_REQUEST
        }
    }

    def dominioException(final PedidoNoTieneItemsException exception) {
        log.error "Exception occurred. ${exception?.message}", exception
        respond exception.message, status: BAD_REQUEST
    }

    def handlerException(final ProductoNoPerteneceAlRestauranteException exception) {
        log.error "Exception occurred. ${exception?.message}", exception
        respond exception.message, status: BAD_REQUEST
    }

    def handlerException(final NoSePudeRemoverProductoException exception) {
        log.error "Exception occurred. ${exception?.message}", exception
        respond exception.message, status: BAD_REQUEST
    }

    def handlerException(final NoSePuedeActualizarProductoException exception) {
        log.error "Exception occurred. ${exception?.message}", exception
        respond exception.message, status: BAD_REQUEST
    }

    def handlerException(final CuponInvalidoException exception) {
        log.error "Exception occurred. ${exception?.message}", exception
        respond exception.message, status: BAD_REQUEST
    }
}
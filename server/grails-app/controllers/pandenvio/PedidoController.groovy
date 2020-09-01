package pandenvio

import grails.gorm.transactions.Transactional
import grails.rest.*
import grails.converters.*

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

@Transactional(readOnly = true)
class PedidoController {
	static responseFormats = ['json', 'xml']

    ModalidadEntregaService modalidadEntregaService
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

    @Transactional
    def save() {
        try {
            Long clienteId = request.JSON.cliente_id
            String modalidad = request.JSON.modalidad
            Pedido pedido = pedidoService.crearPedido(clienteId, modalidad)
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
}

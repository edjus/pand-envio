package pandenvio

import grails.gorm.transactions.Transactional
import grails.rest.*
import grails.converters.*

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CREATED

@Transactional(readOnly = true)
class PedidoController {
	static responseFormats = ['json', 'xml']

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
}

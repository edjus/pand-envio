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

        // TODO: Crear PedidoService que reciba Long clienteId y String modalidad y usarlo para crear pedido
        Cliente cliente = Cliente.findById(request.JSON.cliente_id)
        ModalidadEntrega modalidadEntrega = obtenerModadalidad(request.JSON.modalidad)
        if (!cliente || !modalidadEntrega){
            // TODO: Ver como enviar mensaje de error
            respond 'Parámetros inválidos', status: BAD_REQUEST
        } else {
            Pedido pedido= new Pedido(cliente, modalidadEntrega).save(failOnError: true)
            respond([pedido: pedido], status: OK)
        }
    }


    // TODO: Crear un ModalidadService que dado un string devuelva la modalidad
    private ModalidadEntrega obtenerModadalidad(String modalidad){
        if (modalidad == 'para_llevar'){
            return  new ModalidadParaLlevar();
        } else if (modalidad == 'para_retirar'){
            return  new  ModalidadParaRetirar()
        } else{
            return null
        }
    }
}

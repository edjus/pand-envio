package pandenvio

import grails.gorm.transactions.*

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ClienteController {

    ClienteService clienteService;

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Cliente.list(params), model:[clienteCount: Cliente.count()]
    }

    def show(Cliente cliente) {
        if (cliente == null) {
            render status:404
        }
        else {
            return [cliente: cliente]
        }
    }

    @Transactional
    def obtenerCupones(Long clienteId) {
        try {
            Set<CuponDescuentoPorcentual> cupones = clienteService.obtenerCupones(clienteId)
            if(cupones.size() == 0){
                respond([],status: OK)
            }
            else{
                respond(cupones, status: OK)
            }
        } catch (RuntimeException e) {
            render(text: e.message, status: BAD_REQUEST)
        }
    }

    @Transactional
    def save(Cliente cliente) {
        //Common controller validations: empty values, non-zero values, etc..
        if (cliente.hasErrors()) {
            respond cliente.errors, view:'create', status:BAD_REQUEST
        } else {
            try {
                clienteService.agregarCliente(cliente)
                respond([cliente:cliente], status: CREATED)
            } catch (DatosNoPuedenSerNulosException error) {
                render(text: error.message, status: BAD_REQUEST)
            }
        }
    }
}

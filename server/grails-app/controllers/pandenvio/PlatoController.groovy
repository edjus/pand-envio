package pandenvio

import grails.gorm.transactions.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

@Transactional(readOnly = true)
class PlatoController {

    PlatoService platoService;

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Plato.list(params), model:[platoCount: Plato.count()]
    }

    def show(Plato plato) {
        if (plato == null) {
            render status:404
        }
        else {
            return [plato: plato]
        }
    }

    @Transactional
    def save(Plato plato) {
        //Common controller validations: empty values, non-zero values, etc..
        if (plato.hasErrors()) {
            respond plato.errors, view:'create'
        } else {
            try {
                platoService.agregarPlato(plato)
                respond([plato:plato], status: CREATED)
            } catch (DatosNoPuedenSerNulosException error) {
                render status:BAD_REQUEST, message: error.message
            }
        }
    }

    
    @Transactional
    def obtenerPlatoAsociadosARestaurant(Long restaurantId) {
        try {
            List<Plato> platos = platoService.obtenerPlatoAsociadosARestaurant(restaurantId)
            if(platos.size() == 0){
                respond([],status: OK)
            }
            else{
                respond(platos, status: OK)
            }
        } catch (RuntimeException e) {
            respond e.message, status: BAD_REQUEST
        }
    }

}

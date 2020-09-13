package pandenvio

import grails.gorm.transactions.Transactional

import static org.springframework.http.HttpStatus.CREATED

@Transactional(readOnly = true)
class RepartidorController {

    RepartidorService repartidorService;

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Repartidor.list(params), model:[repartidorCount: Repartidor.count()]
    }

    def show(Repartidor repartidor) {
        if (repartidor == null) {
            render status:404
        }
        else {
            return [repartidor: repartidor]
        }
    }


    @Transactional
    def save(Repartidor repartidor) {

        //Common controller validations: empty values, non-zero values, etc..
        if (repartidor.hasErrors()) {
            respond repartidor.errors, view:'create'
        } else {
            try {
                repartidorService.agregarRepartidor(repartidor)
                respond([repartidor:repartidor], status: CREATED)
            } catch (DatosNoPuedenSerNulosException error) {
                render status:BAD_REQUEST, message: error.message
            }
        }
    }
}

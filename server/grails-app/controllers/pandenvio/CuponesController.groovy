package pandenvio

import grails.gorm.transactions.*

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class CuponesController {

    CuponesService cuponesService;

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond CuponDescuentoPorcentual.list(params), model:[cuponesCount: CuponDescuentoPorcentual.count()]
    }

    def show(CuponDescuentoPorcentual cupon) {
        if (cupon == null) {
            render status:404
        }
        else {
            return [cupon: cupon]
        }
    }

    @Transactional
    def save(CuponDescuentoPorcentual cupon) {
        //Common controller validations: empty values, non-zero values, etc..
        if (cupon.hasErrors()) {
            respond cupon.errors, view:'create', status:BAD_REQUEST
        } else {
            try {
                cuponesService.agregarCupon(cupon)
                respond([cupon:cupon], status: CREATED)
            } catch (DatosNoPuedenSerNulosException error) {
                render(text: error.message, status: BAD_REQUEST)
            }
        }
    }
}

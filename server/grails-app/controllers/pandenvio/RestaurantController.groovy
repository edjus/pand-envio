package pandenvio

import grails.gorm.transactions.*

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class RestaurantController {

    RestaurantService restaurantService;

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Restaurant.list(params), model:[restaurantCount: Restaurant.count()]
    }

    def show(Restaurant restaurant) {
        if (restaurant == null) {
            render status:404
        }
        else {
            return [restaurant: restaurant]
        }
    }

    @Transactional
    def save(Restaurant restaurant) {

        //Common controller validations: empty values, non-zero values, etc..
        if (restaurant.hasErrors()) {
            respond restaurant.errors, view:'create'
        } else {
            try {
                restaurantService.agregarRestaurant(restaurant)
                respond([restaurant:restaurant], status: CREATED)
            } catch (DatosNoPuedenSerNulos error) {
                render status:BAD_REQUEST, message: error.message
            }
        }
    }
}

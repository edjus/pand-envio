package pandenvio

import grails.gorm.transactions.Transactional
import static org.springframework.http.HttpStatus.CREATED

@Transactional
class PlatoService {

    RestaurantService restaurantService;


    def agregarPlato(Plato plato) {
        plato.save flush:true
    }


    List<Plato> obtenerPlatoAsociadosARestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantService.obtenerRestaurante(restaurantId)
        List<Plato> platos = Plato.findAllWhere(restaurant : restaurant)
        platos
    }

}
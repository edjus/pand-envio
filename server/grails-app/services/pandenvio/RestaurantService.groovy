package pandenvio

import grails.gorm.transactions.Transactional
import static org.springframework.http.HttpStatus.CREATED

@Transactional
class RestaurantService {
    def agregarRestaurant(Restaurant restaurante) {
        restaurante.save flush:true
    }
}
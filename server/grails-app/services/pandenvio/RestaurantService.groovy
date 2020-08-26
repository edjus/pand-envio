package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class RestaurantService {
    def agregarRestaurant(Restaurant restaurante) {
        restaurante.save flush:true
    }
}
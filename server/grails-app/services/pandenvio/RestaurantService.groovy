package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class RestaurantService {
    def agregarRestaurant(Restaurant restaurante) {
        restaurante.save flush:true
    }

    def borrar(Long id) {
        Restaurant restaurante = Restaurant.get(id)
        restaurante.delete flush:true
    }
}
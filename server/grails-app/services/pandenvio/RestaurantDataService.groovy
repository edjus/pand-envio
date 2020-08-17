package pandenvio

import grails.gorm.services.Service

@Service(Restaurant)
interface RestaurantDataService {
    Restaurant save(String nombre)
}
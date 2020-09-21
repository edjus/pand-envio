package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class RepartidorService {

    PedidoService pedidoService
    RestaurantService restaurantService

    def agregarRepartidor(Repartidor repartidor) {
        repartidor.save flush:true
    }

    def  obtenerRepartidor(Long id) {
        Repartidor.findById(id)
    }

    Sueldo obtenerSueldoRepartidor(Long id) {
        Repartidor repartidor = Repartidor.findById(id)
        this.liquidarSueldoFinal(repartidor);
        return repartidor.sueldo
    }

    List<Repartidor> obtenerRepartidoresAsociadosARestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantService.obtenerRestaurante(restaurantId)
        List<Repartidor> repartidores = Repartidor.findAllWhere(restaurant : restaurant)
        return repartidores
    }


    def liquidarSueldoFinal(Repartidor repartidor) {
        if (!repartidor) {
            throw new RuntimeException('El repartidor es inválido')
        }
        repartidor.liquidarSueldoFinal();
        repartidor.sueldo.save flush:true
        repartidor.save flush:true
    }
}
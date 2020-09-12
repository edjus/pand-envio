package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class RepartidorService {
    def agregarRepartidor(Repartidor repartidor) {
        repartidor.save flush:true
    }


    def  obtenerRepartidor(Long id) {
        Repartidor.findById(id)
    }
}
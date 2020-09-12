package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class RepartidorService {
    def agregarRepartidor(Repartidor repartidor) {
        repartidor.save flush:true
    }

    def borrar(String dni) {
        Repartidor repartidor = Repartidor.get(dni)
        repartidor.delete flush:true
    }

    def  obtenerRepartidor(String dni) {
        Repartidor.findById(dni)
    }
}
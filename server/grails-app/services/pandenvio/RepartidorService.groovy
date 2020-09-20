package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class RepartidorService {

    PedidoService pedidoService

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


    def liquidarSueldoFinal(Repartidor repartidor) {
        repartidor.liquidarSueldoFinal();
        repartidor.sueldo.save flush:true
        repartidor.save flush:true
    }
}
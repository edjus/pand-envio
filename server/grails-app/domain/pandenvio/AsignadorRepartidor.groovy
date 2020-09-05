package pandenvio

@Singleton(strict = false)
class AsignadorRepartidor {

    void asignarPara(Pedido pedido){
        Repartidor repartidor = obtenerRepartidor(pedido.restaurant)
        pedido.asignarA(repartidor)
    }

    Repartidor obtenerRepartidor(Restaurant restaurant){
        // TODO: Agregar lógica de repartidores disponibles
        def repartidores = Repartidor.findAllWhere(disponible: true, restaurant: restaurant)
        if (repartidores.empty){
            return null
        }
         log.info("REPARTIDORES HABILATOS: ${repartidores.count}")
        println("REPARTIDORES HABILATOS: ${repartidores.count}")
        repartidores.first()
    }
}

package pandenvio

@Singleton(strict = false)
class AsignadorRepartidor {

    void asignarPara(Pedido pedido){
        Repartidor repartidor = obtenerRepartidor(pedido.restaurant)
        pedido.asignarA(repartidor)
    }

    Repartidor obtenerRepartidor(Restaurant restaurant){
        def repartidores = Repartidor.findAllWhere(disponible: true, restaurant: restaurant)
        if (repartidores.empty){
            return null
        }
        repartidores.first()
    }
}
package pandenvio

@Singleton(strict = false)
class AsignadorRepartidor {

    void asignarPara(Pedido pedido){
        Repartidor repartidor = obtenerRepartidor()
        pedido.asignarA(repartidor)
    }

    Repartidor obtenerRepartidor(){
        // TODO: Agregar lógica de repartidores disponibles
        def repartidores = Repartidor.findAll()
        if (repartidores.empty){
            return null
        }
        repartidores.first()
    }
}

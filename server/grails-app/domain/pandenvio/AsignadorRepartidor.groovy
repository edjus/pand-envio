package pandenvio

@Singleton(strict = false)
class AsignadorRepartidor {

    void asignarPara(Pedido pedido){
        Repartidor repartidor = obtenerRepartidor()
        pedido.asignarA(repartidor)
    }

    Repartidor obtenerRepartidor(){
        // TODO: Agregar lógica de repartidores disponibles
        def repartidores = Repartidor.findAll { r -> r.disponible }
        if (repartidores.empty){
            return null
        }
         log.info("REPARTIDORES HABILATOS: ${repartidores.count}")
        println("REPARTIDORES HABILATOS: ${repartidores.count}")
        repartidores.first()
    }
}

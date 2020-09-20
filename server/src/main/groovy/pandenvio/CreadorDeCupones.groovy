package pandenvio

@Singleton(strict = false)
class CreadorDeCupones {

    void CrearPosibleCupon(Cliente cliente, Restaurant restaurant){
        if(cliente.cupones.disponibles){
            return;
        }
        def pedidos = Pedidos.findAllWhere(cliente: cliente, restaurant: restaurant, nombreEstado: "entregado")
        int cantidadDePedidosEntregados = pedidos.size()
        if (cantidadDePedidosEntregados % 3 == 0){
            //crea cupon
        }
    }
}

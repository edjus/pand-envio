package pandenvio

@Singleton(strict = false)
class AsignadorRepartidor {
    int cantidadDePedidosParaObtenerCupon = 3

    void calcularPosibleCupon(Pedido pedido){
        cantidadDePedidos = (Pedido.findAllWhere(cliente: pedido.cliente, restaurant: pedido.restaurant)).size()
        if((cantidadDePedidos + 1) % cantidadDePedidosParaObtenerCupon == 0){
            CuponDescuentoPorcentual cuponAcumulable = new CuponDescuentoPorcentual()
    }
}
}

package pandenvio

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        "/cliente"(resources:"cliente")
        "/restaurant"(resources:"restaurant")
        "/plato"(resources:"plato")
        "/menu"(resources:"menu")
        "/repartidor"(resources:"repartidor")
        "/cupones"(resources:"cupones")

        get "/repartidor/sueldo/$repartidorId"(controller: 'repartidor', action: 'obtenerSueldoFinal')
        get "/repartidor/restaurant/$restaurantId"(controller: 'repartidor', action: 'obtenerRepartidoresAsociadosARestaurant')


        get "/plato/restaurant/$restaurantId"(controller: 'plato', action: 'obtenerPlatoAsociadosARestaurant')


        // pedido
        "/pedido"(resources:"pedido")
        put "/pedido/$pedidoId/siguienteEstado"(controller: 'pedido', action: 'actualizarEstado')
        put "/pedido/$pedidoId/cancelar"(controller: 'pedido', action: 'cancelar')
        put "/pedido/$pedidoId/cupon"(controller: 'pedido', action: 'agregarCupon')
        post "/pedido/$pedidoId/producto"(controller: 'pedido', action: 'agregarProducto')
        get "/pedido/actual/$clienteId"(controller: 'pedido', action: 'pedidoActual')
        get "/pedido/restaurant/$restaurantId"(controller: 'pedido', action: 'buscarPedidosPorRestaurante')
        delete "/pedido/$pedidoId/producto/$productoId"(controller: 'pedido', action: 'removerProducto')
        put "/pedido/$pedidoId/producto/$productoId"(controller: 'pedido', action: 'actualizarProducto')
        put "/pedido/$pedidoId/cambiarRango"(controller: 'pedido', action: 'cambiarRango')
        post "/pedido/$pedidoId/calificarPedido"(controller: 'pedido', action: 'calificarPedido')
        get "/pedido/$pedidoId/calificacion"(controller: 'pedido', action: 'obtenerCalificacion')
        get "/pedido/cliente/$clienteId"(controller: 'pedido', action: 'obtenerPedidosAsociadosACliente')

        put "/pedido/$pedidoId/cambiarModalidad"(controller: 'pedido', action: 'cambiarModalidad')
        post "/clima"(controller: 'clima', action: 'actualizarClima')
        get "/clima"(controller: 'clima', action: 'mostrarClima')

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}

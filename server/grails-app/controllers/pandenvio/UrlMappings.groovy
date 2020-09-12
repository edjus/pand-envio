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




        // pedido
        "/pedido"(resources:"pedido")
        put "/pedido/$pedidoId/siguienteEstado"(controller: 'pedido', action: 'actualizarEstado')
        put "/pedido/$pedidoId/cancelar"(controller: 'pedido', action: 'cancelar')
        post "/pedido/$pedidoId/item"(controller: 'pedido', action: 'agregarItem')
        get "/pedido/actual/$clienteId"(controller: 'pedido', action: 'pedidoActual')
        delete "/pedido/$pedidoId/producto/$productoId"(controller: 'pedido', action: 'removerProducto')

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}

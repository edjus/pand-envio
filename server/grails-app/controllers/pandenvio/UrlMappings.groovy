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

        // pedido
        "/pedido"(resources:"pedido")
        put "/pedido/$pedidoId/siguienteEstado"(controller: 'pedido', action: 'actualizarEstado')


        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}

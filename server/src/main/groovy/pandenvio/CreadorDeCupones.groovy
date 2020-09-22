package pandenvio

@Singleton(strict = false)
class CreadorDeCupones {

    void crearPosibleCupon(Cliente cliente, Restaurant restaurant){
        def cuponesDisponibles = cliente.cupones.findAll { c -> c.estaDisponible() &&
                                                            c.restaurant == restaurant }
        if  (!cuponesDisponibles.empty){
            return
        }

        def pedidosEntregados = Pedido.list().findAll {
                                            p -> p.cliente == cliente &&
                                                    p.restaurant == restaurant &&
                                                    p.estado.class == EstadoEntregado
                                            }.sort {p -> p.fecha}

        def pedidosEntregadosConCupon = pedidosEntregados.findAll {
            p -> p.cuponDeDescuento.class == CuponDescuentoPorcentual
            }.sort {p -> p.fecha}

        Integer cantidadEntregados

        if (pedidosEntregadosConCupon.empty){
            cantidadEntregados = pedidosEntregados.size()
        }
        else {
            // pedidos entregados después del último pedido entregado con cupón
            cantidadEntregados = pedidosEntregados.findAll {
                p -> p.fecha > pedidosEntregadosConCupon.last().fecha
                }.size()
        }

        if (cantidadEntregados == 3){
            crearCupon(cliente, restaurant)
        }
    }

    void crearCupon(Cliente cliente, Restaurant restaurant) {
        Integer cantidadCuponesEnRestaurante = cliente.cupones.findAll {c -> c.restaurant == restaurant}.size()
        String codigo = "${cliente.id}-${restaurant.id}-${cantidadCuponesEnRestaurante}"
        CuponDescuentoPorcentual cupon = new CuponDescuentoPorcentual(cliente: cliente, codigo: codigo, fecha: new Date(), restaurant: restaurant, porcentaje: 10)
        cupon.save(failOnError: true)
        cliente.addToCupones(cupon)
        cliente.save(failOnError: true)
    }
}

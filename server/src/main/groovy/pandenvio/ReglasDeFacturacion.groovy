package pandenvio

abstract class ReglasDeFacturacion {
    abstract void calcularVariacion(Pedido pedido, def informacionDelMes)
}

class PedidosIncompletos extends ReglasDeFacturacion {

    @Override
    void calcularVariacion(Pedido pedido, def informacionDelMes) {
        if (pedido.estado.class == EstadoNoEntregado) {
            informacionDelMes["cantidadPedidosIncompletos"] += 1
        }
    }
}

class PedidosCompletados extends ReglasDeFacturacion {

    @Override
    void calcularVariacion(Pedido pedido, def informacionDelMes) {
        if (pedido.estado.class == EstadoEntregado) {
            informacionDelMes["cantidadPedidosCompletados"] += 1
        }
    }
}

class PedidosConLluvia extends ReglasDeFacturacion {

    @Override
    void calcularVariacion(Pedido pedido, def informacionDelMes) {
        if (pedido.estado.class == EstadoEntregado && pedido.esConLlluvia()) {
            informacionDelMes["cantidadPedidosConLluvia"] += 1
        }
    }
}

class PedidosConPuntuacionMenoresA3 extends ReglasDeFacturacion {

    @Override
    void calcularVariacion(Pedido pedido, def informacionDelMes) {
        if (pedido.estado.class == EstadoEntregado && pedido.tienePuntuacion() && pedido.obtenerEstrellas() < 3) {
            informacionDelMes["cantidadPedidosConPuntuacionMala"] += 1
        }
    }
}

class PedidosConPuntuacionIgual5 extends ReglasDeFacturacion {

    @Override
    void calcularVariacion(Pedido pedido, def informacionDelMes) {
        if (pedido.estado.class == EstadoEntregado && pedido.tienePuntuacion() && pedido.obtenerEstrellas() == 5) {
            informacionDelMes["cantidadPedidosConPuntuacionPerfecta"] += 1        }
    }
}

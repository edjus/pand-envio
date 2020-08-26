package pandenvio

class FabricaEstados {

    def static estados = ['recibido': EstadoRecibido, 'en_preparacion': EstadoEnPreparacion,
                          'listo': EstadoListo, 'en_espera': EstadoEnEspera,
                          'en_entrega': EstadoEnEntrega, 'entregado': EstadoEntregado,
                          'no_entregado': EstadoNoEntregado, 'cancelado': EstadoCancelado]

    static EstadoPedido obtenerEstado(String nombre) {
        if (!estados.containsKey(nombre)) {
            throw new EstadoInvalidoException("El estado: ${nombre} no es válido")
        }

        estados[nombre].getDeclaredConstructor().newInstance()
    }
}

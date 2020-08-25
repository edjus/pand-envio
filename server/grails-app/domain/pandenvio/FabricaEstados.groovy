package pandenvio

class FabricaEstados {

    def static estados = ['recibido': EstadoRecibido.class, 'en_preparacion': EstadoEnPreparacion.class,
                          'listo': EstadoListo.class, 'en_espera': EstadoEnEspera.class,
                          'en_entrega': EstadoEnEntrega.class, 'entregado': EstadoEntregado.class,
                          'no_entregado': EstadoNoEntregado.class, 'cancelado': EstadoCancelado.class]

    static EstadoPedido obtenerEstado(String nombre){
        if (!estados.containsKey(nombre))
            throw new EstadoInvalidoException("El estado: ${nombre} no es válido")
        return estados[nombre].getDeclaredConstructor().newInstance()
    }
}

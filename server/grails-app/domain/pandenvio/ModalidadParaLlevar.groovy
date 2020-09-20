package pandenvio

class ModalidadParaLlevar  extends ModalidadEntrega {
    Puntuacion puntuacion
    Repartidor repartidor
    //int Distancia 1 km , 5 km, 10 km 

    static constraints = {
        puntuacion nullable: true
        repartidor nullable: true
    }

    ModalidadParaLlevar(){
        this.nombre = "para_llevar"
    }

    @Override
    BigDecimal aplicarAdicionales(BigDecimal valorBase) {
        // TODO: Aplicar adicionales si corresponde

        //Checkear el clima del pedido y el clima le agrega el adicional adicional 
        valorBase
    }

    @Override
    EstadoPedido siguienteEstadoListo() {
        new EstadoEnEntrega()
    }

    @Override
    boolean hayRepartidor() {
        repartidor
    }

    @Override
    void asignarRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor
        this.repartidor?.disponible = false
    }
}

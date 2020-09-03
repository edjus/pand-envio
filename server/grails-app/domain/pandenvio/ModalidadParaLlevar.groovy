package pandenvio

class ModalidadParaLlevar  extends ModalidadEntrega {
    Puntuacion puntuacion
    Repartidor repartidor

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

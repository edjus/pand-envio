package pandenvio

class ModalidadParaLlevar  extends ModalidadEntrega {
    Puntuacion puntuacion
    Repartidor repartidor

    def adicionales = []

    static constraints = {
        puntuacion nullable: true
        repartidor nullable: true
    }

    ModalidadParaLlevar(){
        this.nombre = "para_llevar"
        this.adicionales << new AdicionalDistancia()
        this.adicionales << new AdicionalClimatico()
    }

    @Override
    BigDecimal aplicarAdicionales(BigDecimal valorBase, Pedido pedido) {
        BigDecimal adicional = this.adicionales.sum {a -> a.obtenerAdicional(valorBase, pedido)}
        valorBase + adicional
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

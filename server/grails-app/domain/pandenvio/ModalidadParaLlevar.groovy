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

    int cantidadEstrellas(){
        return this.puntuacion.estrellas
    }

    Puntuacion getPuntuacion(){
        return this.puntuacion
    }

    void agregarPuntuacion(Integer calificacion){
        this.puntuacion = new Puntuacion(calificacion)
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
    ModalidadParaRetirar cambiarModalidad() {
        new ModalidadParaRetirar()
    }

    @Override
    void asignarRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor
        this.repartidor?.disponible = false
    }
}

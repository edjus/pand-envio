package pandenvio

class ModalidadParaLlevar  extends ModalidadEntrega {
    Puntuacion puntuacion = new Puntuacion()

    Repartidor repartidor

    def adicionales = []

    static constraints = {
        puntuacion nullable: true
        repartidor nullable: true
    }

    ModalidadParaLlevar(){
        this.nombre = "para_llevar"
        this.puntuacion = new Puntuacion()
        this.adicionales << new AdicionalDistancia()
        this.adicionales << new AdicionalClimatico()
    }

    int cantidadEstrellas(){
        this.puntuacion.estrellas
    }

    Boolean tienePuntuacion(){
        this.puntuacion != null && this.puntuacion.estrellas != null
    }

    void agregarPuntuacion(Integer calificacion){
        this.puntuacion.estrellas = calificacion
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
        repartidor != null
    }

    @Override
    ModalidadParaRetirar cambiarModalidad() {
        new ModalidadParaRetirar()
    }

    @Override
    void asignarRepartidor(Repartidor repartidor) {
        setRepartidor(repartidor)
        this.repartidor?.disponible = false
    }

    @Override
    void entregarPedido() {
        this.repartidor?.disponible = true
        this.repartidor?.save(failOnError: true)
    }
}

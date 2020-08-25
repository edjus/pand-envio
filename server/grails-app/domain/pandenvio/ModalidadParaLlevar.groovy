package pandenvio

class ModalidadParaLlevar  extends ModalidadEntrega{
    Puntuacion puntuacion
    Repartidor repartidor

    static constraints = {
        puntuacion nullable: true
        repartidor nullable: true
    }

    @Override
    BigDecimal aplicarAdicionales(BigDecimal valorBase) {
        valorBase
    }
}

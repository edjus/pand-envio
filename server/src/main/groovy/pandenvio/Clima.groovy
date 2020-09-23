package pandenvio

abstract class Clima {
    String nombre

    abstract BigDecimal adicionalPrecio()
}
class ClimaLluvioso extends Clima {
    ClimaLluvioso(){
        this.nombre = 'lluvioso'
    }

    @Override
    BigDecimal adicionalPrecio() {
        50 // Adicional por lluvia
    }
}

class ClimaNoLluvioso extends Clima{

    ClimaNoLluvioso() {
        this.nombre = 'no_lluvioso'
    }

    @Override
    BigDecimal adicionalPrecio() {
        0
    }
}

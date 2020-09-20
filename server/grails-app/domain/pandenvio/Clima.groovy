package pandenvio

abstract class Clima {
    String nombre
}
class ClimaLluvioso extends Clima {
    ClimaLluvioso(){
        this.nombre = 'lluvioso'
    }
}

class ClimaNoLluvioso extends Clima{

    ClimaNoLluvioso() {
        this.nombre = 'no_lluvioso'
    }
}

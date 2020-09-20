package pandenvio

class FabricaClima {

    static Clima crearPara(String nombre) {
        if (nombre == 'lluvioso') {
            return new ClimaLluvioso()
        }

        new ClimaNoLluvioso()
    }
}

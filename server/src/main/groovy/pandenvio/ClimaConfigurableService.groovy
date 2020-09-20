package pandenvio

@Singleton(strict = false)
class ClimaConfigurableService {

    Clima climaActual

    ClimaConfigurableService() {
        this.climaActual = new ClimaNoLluvioso()
    }

    def lloviendo(lloviendo) {
        this.climaActual = lloviendo ? new ClimaLluvioso() : new ClimaNoLluvioso()
    }
}

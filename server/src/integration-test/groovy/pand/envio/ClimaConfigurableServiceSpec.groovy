package pand.envio

import pandenvio.ClimaConfigurableService
import pandenvio.ClimaLluvioso
import pandenvio.ClimaNoLluvioso
import spock.lang.Specification

class ClimaConfigurableServiceSpec extends Specification {



    void "test ClimaConfigurableServiceSpec Clima"() {
        expect:"devuelve ClimaNoLluvioso por defecto"
        ClimaConfigurableService.instance.climaActual.class == ClimaNoLluvioso
    }

    void "test ClimaConfigurableServiceSpec Lluvioso"() {
        expect:"devuelve Climalluvioso después de llamar lloviendo"
        ClimaConfigurableService.instance.climaActual.class == ClimaNoLluvioso
        ClimaConfigurableService.instance.lloviendo(true)
        ClimaConfigurableService.instance.climaActual.class == ClimaLluvioso
    }


    void "test ClimaConfigurableServiceSpec setar no Lluvioso"() {
        expect:"devuelve ClimaNolluvioso después de llamar lloviendo con false"
        ClimaConfigurableService.instance.lloviendo(false)
        ClimaConfigurableService.instance.climaActual.class == ClimaNoLluvioso
    }
}

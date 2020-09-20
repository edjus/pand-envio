package pand.envio

import pandenvio.ClimaLluvioso
import pandenvio.ClimaNoLluvioso
import pandenvio.FabricaClima
import spock.lang.Specification

class FabricaClimaSpec extends Specification {

    void "test fabrica crea correctamente estado 'no_lluvioso'"() {
        when:
        def clima = FabricaClima.crearPara('no_lluvioso')
        then:
        clima.class == ClimaNoLluvioso
        clima.nombre == 'no_lluvioso'
    }

    void "test fabrica crea correctamente estado 'en_armado'"() {
        when:
        def clima = FabricaClima.crearPara('lluvioso')
        then:
        clima.class == ClimaLluvioso
        clima.nombre == 'lluvioso'
    }

    void "test fabrica crea ClimaNoLluvioso por defecto"() {
        when:
        def clima = FabricaClima.crearPara('')
        then:
        clima.class == ClimaNoLluvioso
        clima.nombre == 'no_lluvioso'
    }
}

package pandenvio

import spock.lang.Specification

class ClimaSpec extends Specification {

    void "test Clima no lluvioso adicional precio es 0"() {
        when:
        def clima = FabricaClima.crearPara('no_lluvioso')
        then:
        clima.adicionalPrecio() == 0
    }

    void "test Clima lluvioso adicional precio es 50"() {
        when:
        def clima = FabricaClima.crearPara('lluvioso')
        then:
        clima.adicionalPrecio() == 50
    }
}

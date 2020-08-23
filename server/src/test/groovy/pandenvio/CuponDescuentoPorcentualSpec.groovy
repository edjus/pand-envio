package pandenvio

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CuponDescuentoPorcentualSpec extends Specification implements DomainUnitTest<CuponDescuentoPorcentual> {

    void "test Cupon Descuento Porcentual activo aplica descuento porcentual correctamente y deja de estar activo"() {
        when:
        CuponDescuentoPorcentual cuponPorcentualActivo = new CuponDescuentoPorcentual(fecha: new Date(), activo: true, codigo: 'ABC', porcentaje: 10)
        BigDecimal precio = cuponPorcentualActivo.aplicarDescuento(100)
        then:
        precio == 90
        !cuponPorcentualActivo.estoyActivo()
    }


    void "test Cupon Descuento Porcentual NO activo intenta aplicar descuento porcentual y lanza excepcion"() {
        when:
        CuponDescuentoPorcentual cuponPorcentualActivo = new CuponDescuentoPorcentual(fecha: new Date(), activo: false, codigo: 'ABC', porcentaje: 15)
        cuponPorcentualActivo.aplicarDescuento(100)
        then:
        thrown CuponYaUtilizadoException
    }


    void "test Cupon Descuento Porcentual NO puede tener poncertaje menor que 1"() {
        when:
        CuponDescuentoPorcentual cuponPorcentualActivo = new CuponDescuentoPorcentual(fecha: new Date(), activo: true, codigo: 'ABC', porcentaje: -1)
        then:
        !cuponPorcentualActivo.validate()
    }

    void "test Cupon Descuento Porcentual NO puede tener poncertaje mayor que 99"() {
        when:
        CuponDescuentoPorcentual cuponPorcentualActivo = new CuponDescuentoPorcentual(fecha: new Date(), activo: true, codigo: 'ABC', porcentaje: 100)
        then:
        !cuponPorcentualActivo.validate()
    }

    void "Cupon Descuento Porcentual se guarda correctamente"() {
        when:
            def cupon = new CuponDescuentoPorcentual(fecha: new Date(), activo: true, codigo: 'ABC', porcentaje: 10)
        then:
            cupon.save(failOnError)
            CuponDescuentoPorcentual.count == 1
            cupon == CuponDescuentoPorcentual.findById(cupon.id)
    }

}

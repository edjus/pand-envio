package pandenvio

import grails.gorm.transactions.Transactional

import static org.springframework.http.HttpStatus.OK

@Transactional(readOnly = true)
class ClimaController {

    @Transactional
    def actualizarClima() {
        ClimaConfigurableService.instance.lloviendo(request.JSON.lluvia)
        respond([clima: ClimaConfigurableService.instance.climaActual.nombre] ,status: OK)
    }

    def mostrarClima() {
        respond([clima: ClimaConfigurableService.instance.climaActual.nombre] ,status: OK)
    }
}

package pandenvio

import groovy.transform.CompileStatic

@CompileStatic
class BootStrap {

    PlatoDataService platoDataService
    RestaurantDataService restaurantDataService

    def init = { servletContext ->
        log.info "Loading database..."
        Restaurant restaurante1 = restaurantDataService.save("La esquina del AntiGourmet")
        Restaurant restaurante2 = restaurantDataService.save("El Palacio de la Pizza")

        platoDataService.save("Tortilla de papas", 350.0, CategoriaPlato.PLATO, "Tortilla de papas clasica", restaurante1)
        platoDataService.save("Sanguche de Milanesa", 450.0, CategoriaPlato.PLATO, "Milanga completo", restaurante1)

        platoDataService.save("Grande de Muzza", 700.0, CategoriaPlato.PLATO, "Tradional pizza muzzarella", restaurante2)
        platoDataService.save("Faina", 100.0, CategoriaPlato.PLATO, "1 porcion de faina", restaurante2)

    }
    def destroy = {
    }
}
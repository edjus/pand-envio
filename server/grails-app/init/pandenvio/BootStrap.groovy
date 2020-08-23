package pandenvio

import groovy.transform.CompileStatic

@CompileStatic
class BootStrap {

    PlatoDataService platoDataService
    RestaurantService restaurantService

    def init = { servletContext ->
        log.info "Loading database..."

        Restaurant restaurante1 = new Restaurant(nombre: "La esquina", productos: null)

        restaurantService.agregarRestaurant(restaurante1)

//        Plato plato = platoDataService.save("Tortilla de papas", 350.0, CategoriaPlato.PLATO, "Tortilla de papas clasica", restaurante1)
        //platoDataService.save("Sanguche de Milanesa", 450.0, CategoriaPlato.PLATO, "Milanga completo", restaurante1)
        //platoDataService.save("Buñuelos de acelga", 300.0, CategoriaPlato.ENTRADA, "6 buñuelos de acelga fritos", restaurante1)

        //Menu menu = new Menu(nombre: 'Menu', precio: 400, restaurant: restaurante1)
        //menu.addToPlatos(plato)
        //menu.save()
        //Item item1 = new Item(menu, 2)
        //item1.save()
        //platoDataService.save("Grande de Muzza", 700.0, CategoriaPlato.PLATO, "Tradional pizza muzzarella", restaurante2)
        //platoDataService.save("Faina", 100.0, CategoriaPlato.PLATO, "1 porcion de faina", restaurante2)

    }
    def destroy = {
    }
}
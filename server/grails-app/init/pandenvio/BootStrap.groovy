package pandenvio

import groovy.transform.CompileStatic

@CompileStatic
class BootStrap {

    PlatoService platoService
    RestaurantService restaurantService
    ClienteService clienteService

    def init = { servletContext ->
        log.info "Loading database..."

        Ubicacion juanPerezUbicacion = new Ubicacion(calle: "Paunero", altura: 2030, pisoYDepartamento: null)
        Cliente juanPerez = new Cliente(nombre: "Juan", apellido: "Perez", mail: "juanperez@yahoo.com.ar", ubicacion: juanPerezUbicacion, telefono: "1138465977")

        Ubicacion pepeArgentoUbicacion = new Ubicacion(calle: "Paseo Colon", altura: 850, pisoYDepartamento: null)
        Cliente pepeArgento = new Cliente(nombre: "Pepe", apellido: "Argento", mail: "pepeargento@fi.uba.com.ar", ubicacion: pepeArgentoUbicacion, telefono: "1125146958")

        clienteService.agregarCliente(juanPerez)
        clienteService.agregarCliente(pepeArgento)


        Restaurant restaurante1 = new Restaurant(nombre: "La esquina", productos: null)


        restaurantService.agregarRestaurant(restaurante1)



        platoService.agregarPlato("Tortilla de papas", 350.0, CategoriaPlato.PLATO, "Tortilla de papas clasica", restaurante1)
        platoService.agregarPlato("Sanguche de Milanesa", 450.0, CategoriaPlato.PLATO, "Milanga completo", restaurante1)
        platoService.agregarPlato("Buñuelos de acelga", 300.0, CategoriaPlato.ENTRADA, "6 buñuelos de acelga fritos", restaurante1)

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
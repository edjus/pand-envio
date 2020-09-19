package pandenvio

import groovy.transform.CompileStatic

@CompileStatic
class BootStrap {

    PlatoService platoService
    RestaurantService restaurantService
    ClienteService clienteService
    MenuService menuService

    def init = { servletContext ->
        log.info "Loading database..."

        Ubicacion juanPerezUbicacion = new Ubicacion(calle: "Paunero", altura: 2030, pisoYDepartamento: null)
        Cliente juanPerez = new Cliente(nombre: "Juan", apellido: "Perez", mail: "juanperez@yahoo.com.ar", ubicacion: juanPerezUbicacion, telefono: "1138465977",cupones:null)
        Ubicacion pepeArgentoUbicacion = new Ubicacion(calle: "Paseo Colon", altura: 850, pisoYDepartamento: null)
        Cliente pepeArgento = new Cliente(nombre: "Pepe", apellido: "Argento", mail: "pepeargento@fi.uba.com.ar", ubicacion: pepeArgentoUbicacion, telefono: "1125146958")

        clienteService.agregarCliente(juanPerez)
        clienteService.agregarCliente(pepeArgento)


        Restaurant restaurante1 = new Restaurant(nombre: "La esquina", productos: null)


        restaurantService.agregarRestaurant(restaurante1)

        Plato tortilla = new Plato(nombre: "Tortilla de papas", precio: 350.0, categoria: CategoriaPlato.PLATO, descripcion: "Tortilla de papas clasica", restaurant:restaurante1)
        platoService.agregarPlato(tortilla)
        Plato sanguche = new Plato(nombre: "Sanguche de Milanesa", precio: 450.0, categoria: CategoriaPlato.PLATO, descripcion: "Milanga completo", restaurant:restaurante1)
        platoService.agregarPlato(sanguche)
        Plato acelga = new Plato(nombre: "Buñuelos de acelga", precio: 225.75, categoria: CategoriaPlato.ENTRADA, descripcion: "6 buñuelos de acelga fritos", restaurant:restaurante1)
        platoService.agregarPlato(acelga)

        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)

        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, descripcion: '15 te hago alto guiso', restaurant: restaurante)
                .save(failOnError: true)
        Producto menu = new Menu(nombre: 'Viernes', precio: 400, restaurant: restaurante)
                .addToPlatos(plato)
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaRetirar()
                .save(failOnError: true)
        Pedido pedido = new Pedido(pepeArgento, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.save(failOnError: true)
        CuponDescuento cupon2 = new CuponDescuentoPorcentual(cliente: pepeArgento, porcentaje: 10, codigo: 'ABC', fecha: new Date())
                .save(failOnError: true)

    }
    def destroy = {
    }
}
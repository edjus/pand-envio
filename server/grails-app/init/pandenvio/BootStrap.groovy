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

        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)
        Cliente cliente = new Cliente(nombre: 'Moni', apellido: 'Argento',  mail: 'moni.argento@gmail.com', ubicacion: unaCasa, telefono: '11-5555-4433').save()
        Producto menu = new Menu(nombre: 'Viernes', precio: 400, restaurant: restaurante)
                .addToPlatos(new Plato(nombre:'Milanesa con pure', precio: 350, categoria: CategoriaPlato.PLATO, restaurant:  restaurante))
                .save(failOnError: true)
        Producto plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, restaurant: restaurante)
                .save(failOnError: true)
        CuponDescuento cupon = new CuponDescuentoPorcentual(activo: true, porcentaje: 10, codigo: 'ABC', fecha: new Date())
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaRetirar()
                .save(failOnError: true)
        EstadoPedido estado = new EstadoEnPreparacion().save(failOnError: true)
        Pedido pedido = new Pedido(cliente, modalidadEntrega)
        pedido.agregar(plato, 2)
        pedido.agregar(menu, 2)
        pedido.cuponDeDescuento = cupon
        pedido.save(failOnError: true)
        Pedido pedido2 = new Pedido(cliente, modalidadEntrega)
        pedido2.agregar(plato, 2)
        pedido2.estado = estado
        pedido2.save(failOnError: true)

    }
    def destroy = {
    }
}
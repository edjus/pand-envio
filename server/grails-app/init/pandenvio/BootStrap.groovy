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


        Restaurant restaurante1 = new Restaurant(nombre: "La esquina", productos: null,  direccion: 'Av. San Martin 456')
        Repartidor repartidor = new Repartidor(nombre: 'Carlitos', dni:123456,disponible: true ,restaurant: restaurante1)
        repartidor.save()

        restaurantService.agregarRestaurant(restaurante1)

        Plato tortilla = new Plato(nombre: "Tortilla de papas", precio: 350.0, categoria: CategoriaPlato.PLATO, descripcion: "Tortilla de papas clasica", restaurant:restaurante1, imagen: "https://cocina-casera.com/wp-content/uploads/2015/01/receta-tortilla-patatas-facil.jpg")
        platoService.agregarPlato(tortilla)
        Plato sanguche = new Plato(nombre: "Sanguche de Milanesa", precio: 450.0, categoria: CategoriaPlato.PLATO, descripcion: "Milanga completo", restaurant:restaurante1, imagen: "https://d35hsl9am8s2ta.cloudfront.net/public/images/2020/04/sanguche-de-milanesa-1024x683.jpg")
        platoService.agregarPlato(sanguche)
        Plato acelga = new Plato(nombre: "Buñuelos de acelga", precio: 225.75, categoria: CategoriaPlato.ENTRADA, descripcion: "6 buñuelos de acelga fritos", restaurant:restaurante1, imagen: "https://www.milrecetasvegetarianas.com/wp-content/uploads/2017/01/11-Bu%C3%B1uelos-de-acelga-portada.jpg")
        platoService.agregarPlato(acelga)

        Restaurant restaurante = new Restaurant(nombre: 'La otra esquina', direccion: 'Av. siempre viva 123').save(failOnError: true)
        Repartidor repartidor2 = new Repartidor(nombre: 'Pedrito', dni:455667, disponible: true, restaurant: restaurante)
        repartidor2.save(failOnError: true)
        Ubicacion unaCasa = new Ubicacion(calle:'Av. Siempre viva', altura: 1234).save(failOnError: true)

        Plato plato = new Plato(nombre: 'Alto Guiso', precio: 200, categoria: CategoriaPlato.PLATO, descripcion: '15 te hago alto guiso', restaurant: restaurante, imagen: "https://hoycocino.com.ar/wp-content/uploads/2020/01/guiso-de-lentejas-vegetariano.jpg")
                .save(failOnError: true)
        ModalidadEntrega modalidadEntrega = new ModalidadParaRetirar()
                .save(failOnError: true)
        Pedido pedido = new Pedido(pepeArgento, modalidadEntrega, restaurante)
        pedido.agregar(plato, 2)
        pedido.save(failOnError: true)
        CuponDescuento cupon = new CuponDescuentoPorcentual(cliente: pepeArgento, porcentaje: 10, codigo: 'ABC', fecha: new Date(), restaurant: restaurante)
                .save(failOnError: true)

        Plato.withTransaction {
            Producto menu = new Menu(nombre: 'Viernes', precio: 400, restaurant: restaurante, imagen: "https://bongourmetbysilven.com/211-large_default/menu-diario-viernes.jpg").save()
            plato.menues << menu
            menu.platos << plato
            Producto menu2 = new Menu(nombre:'Ejecutivo', precio: 500, restaurant: restaurante1, imagen: "https://i.ytimg.com/vi/grKMlRX1tQw/maxresdefault.jpg").save()
            tortilla.menues << menu2
            menu2.platos << tortilla
            sanguche.menues << menu2
            menu2.platos << sanguche
            return null
        }
        pepeArgento.addToCupones(cupon)
        pepeArgento.save(failOnError: true)
        assert  pepeArgento.cupones.size() == 1
    }
    def destroy = {
    }
}
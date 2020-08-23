package pandenvio

import grails.gorm.transactions.Transactional
import static org.springframework.http.HttpStatus.CREATED

@Transactional
class PlatoService {
    def agregarPlato(String nombre, BigDecimal precio, CategoriaPlato categoria, String descripcion, Restaurant restaurant) {

        Plato plato = new Plato(nombre: nombre, precio: precio, categoria: categoria, descripcion: descripcion, restaurant:restaurant)

        restaurant.save flush:true

        plato.save flush:true
    }
}
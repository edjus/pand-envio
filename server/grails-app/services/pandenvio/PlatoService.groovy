package pandenvio

import grails.gorm.transactions.Transactional
import static org.springframework.http.HttpStatus.CREATED

@Transactional
class PlatoService {
    def agregarPlato(String nombre, BigDecimal precio, CategoriaPlato categoria, String descripcion, Restaurant restaurant) {
        plato.save flush:true
    }
}
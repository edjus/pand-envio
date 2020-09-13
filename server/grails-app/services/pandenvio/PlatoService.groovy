package pandenvio

import grails.gorm.transactions.Transactional
import static org.springframework.http.HttpStatus.CREATED

@Transactional
class PlatoService {
    def agregarPlato(Plato plato) {
        plato.save flush:true
    }
}
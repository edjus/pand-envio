package pandenvio

import grails.gorm.transactions.Transactional
import static org.springframework.http.HttpStatus.CREATED

@Transactional
class MenuService {
    def agregarMenu(Menu menu) {
        menu.save flush:true
    }
}
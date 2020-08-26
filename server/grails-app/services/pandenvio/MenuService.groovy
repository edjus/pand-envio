package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class MenuService {
    def agregarMenu(Menu menu) {
        menu.save flush:true
    }
}
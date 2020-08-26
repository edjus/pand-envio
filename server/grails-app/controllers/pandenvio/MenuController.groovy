package pandenvio

import grails.gorm.transactions.*

import static org.springframework.http.HttpStatus.*


@Transactional(readOnly = true)
class MenuController {

    MenuService menuService;

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Menu.list(params), model:[menuCount: Menu.count()]
    }

    def show(Menu menu) {
        if (menu == null) {
            render status:404
        }
        else {
            return [menu: menu]
        }
    }

    @Transactional
    def save(Menu menu) {

        //Common controller validations: empty values, non-zero values, etc..
        if (menu.hasErrors()) {
            respond menu.errors, view:'create'
        } else {
            try {
                menuService.agregarMenu(menu)
                respond([menu:menu], status: CREATED)
            } catch (DatosNoPuedenSerNulos error) {
                render status:BAD_REQUEST, message: error.message
            }
        }
    }
}

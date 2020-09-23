package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class MenuService {

    RestaurantService restaurantService;

    def agregarMenu(Menu menu) {
        menu.save flush:true
    }

    List<Menu> obtenerMenuAsociadosARestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantService.obtenerRestaurante(restaurantId)
        List<Menu> menus = Menu.findAllWhere(restaurant : restaurant)
        menus
    }

}
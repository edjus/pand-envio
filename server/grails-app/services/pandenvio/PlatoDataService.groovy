package pandenvio

import grails.gorm.services.Service

@Service(Plato)
interface PlatoDataService {
    Plato save(String nombre, BigDecimal precio, CategoriaPlato categoria, String descripcion, Restaurant restaurant)
}
package pandenvio

class Repartidor {
    String nombre
    String dni
    boolean disponible
    Restaurant restaurant

    static constraints = {
        nombre nullable: false, blank: false
        dni nullable: false, blank: false
        restaurant nullable: false
    }

    Repartidor(String nombre, String dni, Restaurant restaurant) {
        this.nombre = nombre
        this.dni = dni
        this.disponible = true
        this.restaurant = restaurant
    }

    
}

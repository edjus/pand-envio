package pandenvio

class Repartidor {
    String nombre
    String dni
    boolean disponible

    static constraints = {
        nombre nullable: false, blank: false
        dni nullable: false, blank: false
    }

    Repartidor(String nombre, String dni) {
        this.nombre = nombre
        this.dni = dni
        this.disponible = true
    }
}

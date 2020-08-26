package pandenvio

class Puntuacion {
    Integer estrellas
    String comentario

    static constraints = {
        estrellas(range: 1..5)
        comentario nullable: true
    }

    Puntuacion(Integer estrellas) {
        this.estrellas = estrellas
    }
}

package pandenvio

class Puntuacion {
    Integer estrellas
    String comentario

    static constraints = {
        estrellas nullable: true
        comentario nullable: true
    }

    Puntuacion(Integer estrellas) {
        this.estrellas = estrellas
    }

    void setEstrellas(Integer estrellas){
        this.estrellas = estrellas
    }
}

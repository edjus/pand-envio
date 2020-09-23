package pandenvio

class Puntuacion {
    Integer estrellas

    static constraints = {
        estrellas nullable: true
    }

    Puntuacion(Integer estrellas) {
        this.estrellas = estrellas
    }

    void setEstrellas(Integer estrellas){
        this.estrellas = estrellas
    }
}

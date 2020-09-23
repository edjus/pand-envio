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
        if (estrellas > 5 || estrellas < 0) {
            throw new CalificacionException("La calificacion no puede ser inferior a 0 y superior a 5")
        }
        this.estrellas = estrellas
    }
}

package pandenvio

class Ubicacion {

    String calle
    Integer altura
    String pisoYDepartamento


    static constraints = {
        pisoYDepartamento nullable: true, blank: true
    }

    String getUbicacionCompleta() {
        String direccion = calle + " " + altura;
        if (pisoYDepartamento) {
            direccion += " - " + pisoYDepartamento
        }
        return direccion;
    }
}

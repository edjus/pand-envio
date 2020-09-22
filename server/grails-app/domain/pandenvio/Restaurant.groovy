package pandenvio

class Restaurant {
    String nombre

    String direccion

    static hasMany = [productos: Producto]

    static constraints = {
        productos nullable: true
    }
}

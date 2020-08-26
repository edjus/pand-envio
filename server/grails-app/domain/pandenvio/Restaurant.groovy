package pandenvio

class Restaurant {
    String nombre

    static hasMany = [productos: Producto]

    static constraints = {
        productos nullable: true
    }
}

package pandenvio

import grails.rest.Resource

class Restaurant {
    String nombre

    static hasMany = [productos: Producto]

    static constraints = {
        productos nullable: true
    }
}

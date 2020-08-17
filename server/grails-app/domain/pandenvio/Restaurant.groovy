package pandenvio

import grails.rest.Resource

@Resource(uri = '/restaurant')
class Restaurant {
    String nombre

    static hasMany = [productos: Plato]

    static constraints = {
        productos nullable: true
    }
}

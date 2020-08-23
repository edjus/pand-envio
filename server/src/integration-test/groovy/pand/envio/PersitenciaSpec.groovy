package pand.envio


import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import pandenvio.CategoriaPlato
import pandenvio.Item
import pandenvio.Menu
import pandenvio.Plato
import pandenvio.Restaurant
import spock.lang.Specification

@Integration
@Rollback
class PersitenciaSpec extends Specification {

    def setup() {
        Item.executeUpdate('delete from Item')
    }

    def cleanup() {
    }

    void "test Item se guarda bien"() {
        when:
            Restaurant restaurante = new Restaurant(nombre: 'La otra esquina').save(failOnError: true)
            Menu menuConPLatos = new Menu(nombre: 'Viernes', precio: 400, restaurant: restaurante)
                    .addToPlatos(new Plato(nombre:'Milanesa con pure', precio: 350, categoria: CategoriaPlato.PLATO, restaurant:  restaurante))
                    .save(failOnError: true)
            Item item = new Item(menuConPLatos, 2).save(failOnError: true)
        then:
            Item.count == 1
            def itemGuardado = Item.findById(item.id)
            itemGuardado ==  item
            itemGuardado.producto == menuConPLatos
    }
}

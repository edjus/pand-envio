package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class ClienteService {

    def agregarCliente(Cliente cliente) {
        if (cliente.esValido()) {
            cliente.save flush:true
        } else {
            throw new DatosNoPuedenSerNulosException("El nombre no puede estar vacio")
        }
    }

    def obtenerCliente(Long clienteId){
        Cliente.findById(clienteId)
    }
}
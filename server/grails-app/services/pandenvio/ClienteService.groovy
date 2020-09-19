package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class ClienteService {

    def agregarCliente(Cliente cliente) {
        if (cliente.esValidoElMail()) {
            cliente.save flush:true
        } else {
            throw new DatosNoPuedenSerNulosException("El mail es invalido")
        }
    }

    def obtenerCliente(Long clienteId){
        Cliente.findById(clienteId)
    }
}
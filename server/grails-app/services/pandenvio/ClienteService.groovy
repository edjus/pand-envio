package pandenvio

import grails.gorm.transactions.Transactional
import static org.springframework.http.HttpStatus.CREATED

@Transactional
class ClienteService {
    def agregarCliente(Cliente cliente) {
        if (cliente.esValido()) {
            cliente.save flush:true
        } else {
            throw new DatosNoPuedenSerNulos("El nombre no puede estar vacio")
        }
    }
}
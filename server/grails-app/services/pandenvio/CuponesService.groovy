package pandenvio

import grails.gorm.transactions.Transactional
import static org.springframework.http.HttpStatus.CREATED

@Transactional
class CuponesService {

    def agregarCupon(CuponDescuentoPorcentual cupon) {
        if (cupon.esValido()) {
            cupon.save flush:true
        } else {
            throw new DatosNoPuedenSerNulos("El nombre no puede estar vacio")
        }
    }

    def obtenerCupon(CuponDescuentoPorcentual cuponId){
        CuponDescuentoPorcentual.findById(cuponId)
    }
}
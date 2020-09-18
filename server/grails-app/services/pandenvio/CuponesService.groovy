package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class CuponesService {

    def agregarCupon(CuponDescuentoPorcentual cupon) {
        if (cupon.esValido()) {
            cupon.save flush:true
        } else {
            throw new DatosNoPuedenSerNulosException("El nombre no puede estar vacio")
        }
    }

    def obtenerCupon(CuponDescuentoPorcentual cuponId){
        CuponDescuentoPorcentual.findById(cuponId)
    }
}
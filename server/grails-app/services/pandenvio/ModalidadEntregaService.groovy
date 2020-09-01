package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class ModalidadEntregaService {

    def obtenerModalidadPorNombre(String modalidad) {
        if (modalidad == 'para_llevar'){
            return  new ModalidadParaLlevar();
        } else if (modalidad == 'para_retirar'){
            return  new  ModalidadParaRetirar()
        } else{
            return null
        }
    }
}

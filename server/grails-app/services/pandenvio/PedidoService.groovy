package pandenvio

import grails.gorm.transactions.Transactional

@Transactional
class PedidoService {

    ClienteService clienteService
    ModalidadEntregaService modalidadEntregaService

    def crearPedido(Long clienteId, String modalidad) {
        Cliente cliente = clienteService.obtenerCliente(clienteId)
        if (!cliente) {
            throw new RuntimeException('El cliente es inválido')
        }

        ModalidadEntrega modalidadEntrega = modalidadEntregaService.obtenerModalidadPorNombre(modalidad)
        if (!modalidadEntrega) {
            throw new RuntimeException('La modalidad es inválida')
        }

        new Pedido(cliente, modalidadEntrega).save(failOnError: true)
    }
}

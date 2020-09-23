package pandenvio

// Adicional para el precio del pedido
class AdicionalClimatico {

    BigDecimal obtenerAdicional(BigDecimal precioBase, Pedido pedido){
        pedido.clima.adicionalPrecio()
    }
}
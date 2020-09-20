package pandenvio

// Adicional para el precio del pedido
class AdicionalDistancia {

    BigDecimal obtenerAdicional(BigDecimal precioBase, Pedido pedido){
        pedido.enRango ? 0 : precioBase * 0.1 // Si esta fuera del rango el adicional al precio es 10%
    }
}
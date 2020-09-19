package pandenvio

class AdicionalClimatico {

    //adicional por pedido entregado en lluvia
    Float adicional = 100

    Float aplicarAdicional(Float precioBase){
        return precioBase +  adicional
    }

}
package pandenvio

class Adicional {

    //adicional por pedido entregado
    Float adicional = 50

    Float aplicarAdicional(Float precioBase){
        return precioBase +  adicional
    }

}
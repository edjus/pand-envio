package pandenvio

class AdicionalDistancia {

    //adicional por pedido entregado en lluvia
    Ubicacion centro
    Ubicacion destino
    int radio


    Float aplicarAdicional(Float precioBase){
        adicional = calcularAdicional()
        return precioBase +  adicional
    }

    Float calcularAdicional(){
        //Se fija en la modalidad de Llevar la distancia y en base a eso lo calcula
        distanciaCentroADestino = 5;
        if(radio < distanciaCentroADestino){
            adicional = adicional + adicional * 0.1
        }
        return adicional
    }

}
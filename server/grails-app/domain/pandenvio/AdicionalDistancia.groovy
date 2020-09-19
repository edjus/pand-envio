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


    //Api google? Llamar para obtener distancia
    Float calcularAdicional(){
        distanciaCentroADestino = 5;
        if(radio < distanciaCentroADestino){
            adicional = adicional + adicional * 0.1
        }
        return adicional
    }

}
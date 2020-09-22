package pandenvio

class Sueldo {
    int sueldoBase
    int adicionalPorPedidos
    int adicionalPorLluvia
    int sueldoFinal
    int montoAdicionalPorPedido = 50
    int montoAdicionalPorLluvia = 50
    int penalidadPorPedidoIncompleto = 50
    int penalidadesAcumuladas = 0
    int bonoPedidosPerfectos = 0


    Sueldo() {
        this.sueldoBase = 30000
        this.adicionalPorPedidos = 50
        this.adicionalPorLluvia = 50
        this.sueldoFinal = 0
        this.montoAdicionalPorPedido = 50
        this.montoAdicionalPorLluvia = 50
        this.penalidadPorPedidoIncompleto = 50
        this.penalidadesAcumuladas = 0
        this.bonoPedidosPerfectos = 0
    }
    
    void calcularAdicionales(int cantidadPedidosCompletos, int cantidadPedidosIncompletos,  int cantidadPedidosConLluvia, int cantidadPedidosConPuntuacionMala, int cantidadPedidosConPuntuacionPerfecta){
        this.adicionalPorPedidos = calcularAdicional(cantidadPedidosCompletos, this.montoAdicionalPorPedido)
        this.adicionalPorLluvia = calcularAdicional(cantidadPedidosConLluvia, this.montoAdicionalPorLluvia)
        calcularPenalidades(cantidadPedidosIncompletos,cantidadPedidosConPuntuacionMala)
        calcularBonoPorPedidosPerfectos(cantidadPedidosConPuntuacionPerfecta)
        calcularSueldoFinal()
    }

    int calcularAdicional(int cantidadPedidos, int montoAdicional){
        return cantidadPedidos * montoAdicionalPorPedido
    }

    void calcularPenalidades(int cantidadPedidosIncompletos, int cantidadPedidosConPuntuacionMala){
        checkearPenalidadPorPedidosIncompletos(cantidadPedidosIncompletos)
        checkearPenalidadPorPedidosMalCalificados(cantidadPedidosConPuntuacionMala)
    }

    void calcularBonoPorPedidosPerfectos(int cantidadPedidosConPuntuacionPerfecta){
        if(cantidadPedidosConPuntuacionPerfecta >= 4){
            this.bonoPedidosPerfectos = 75
        }
    }

    void checkearPenalidadPorPedidosIncompletos(int cantidadPedidosIncompletos){
        this.penalidadesAcumuladas = 0
        if(adicionalPorPedidos > cantidadPedidosIncompletos * penalidadPorPedidoIncompleto){
            this.adicionalPorPedidos -= cantidadPedidosIncompletos * penalidadPorPedidoIncompleto
        }
        else{
            adicionalPorPedidos = 0
        }
        if(cantidadPedidosIncompletos > 3){
             this.penalidadesAcumuladas += sueldoBase * 0.2
        }
    }

    void checkearPenalidadPorPedidosMalCalificados(int cantidadPedidosConPuntuacionMala){
        if(cantidadPedidosConPuntuacionMala >= 3 & this.adicionalPorPedidos > 50){
            this.adicionalPorPedidos -= 50
        }
    }


    void calcularSueldoFinal(){
        this.sueldoFinal = this.sueldoBase + this.adicionalPorPedidos + this.adicionalPorLluvia + this.bonoPedidosPerfectos - this.penalidadesAcumuladas
    }
}



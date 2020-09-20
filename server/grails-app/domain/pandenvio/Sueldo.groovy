package pandenvio

class Sueldo {
    int sueldoBase
    int adicionalPorPedidos
    int adicionalPorLluvia
    int sueldoFinal
    int montoAdicionalPorPedido = 50
    int montoAdicionalPorLluvia = 50


    Sueldo() {
        this.sueldoBase = 30000
    }
    
    void calcularAdicionales(int cantidadPedidosCompletos, int cantidadPedidosConLluvia){
        this.adicionalPorPedidos = calcularAdicional(cantidadPedidosCompletos, this.montoAdicionalPorPedido)
        this.adicionalPorLluvia = calcularAdicional(cantidadPedidosConLluvia, this.montoAdicionalPorLluvia)
        calcularSueldoFinal()
    }

    int calcularAdicional(int cantidadPedidos, int montoAdicional){
        return cantidadPedidos * montoAdicionalPorPedido
    }


    void calcularSueldoFinal(){
        this.sueldoFinal = this.sueldoBase + this.adicionalPorPedidos + this.adicionalPorLluvia
    }
}



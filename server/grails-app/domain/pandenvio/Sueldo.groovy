package pandenvio

class Sueldo {
    int sueldoBase
    int adicionalPorPedidos
    int sueldoFinal
    int montoAdicionalPorPedido = 50


    Sueldo() {
        this.sueldoBase = 30000
    }
    
    void calcularAdicionales(int cantidadPedidos){
        this.adicionalPorPedidos = cantidadPedidos * montoAdicionalPorPedido
        calcularSueldoFinal()
    }

    void calcularSueldoFinal(){
        this.sueldoFinal = this.sueldoBase + this.adicionalPorPedidos
    }




}

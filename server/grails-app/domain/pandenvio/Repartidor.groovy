package pandenvio

class Repartidor {
    String nombre
    String dni
    boolean disponible
    Restaurant restaurant
    Sueldo sueldo = new Sueldo()
    List<Pedido> listaDePedidos = new ArrayList<Pedido>()

    static constraints = {
        nombre nullable: false, blank: false
        dni nullable: false, blank: false
        restaurant nullable: false
        sueldo nullable: true
    }

    Repartidor(String nombre, String dni, Restaurant restaurant, sueldoBase = new Sueldo()) {
        this.nombre = nombre
        this.dni = dni
        this.disponible = true
        this.restaurant = restaurant
        setSueldo(sueldoBase)
        this.listaDePedidos = listaDePedidos;
    }   

    void setSueldo(Sueldo sueldo){
        this.sueldo = sueldo
    }

    void agregarPedido(Pedido pedido){
        this.listaDePedidos.add(pedido)
    }

    int cantidadPedidosCompletados(){
        int cantidadPedidosCompletados = 0
        for ( pedido in this.listaDePedidos ) {
            cantidadPedidosCompletados = cantidadPedidosCompletados + 1
        }        
        return cantidadPedidosCompletados
    }

    int cantidadPedidosConLluvia(){
        int cantidadPedidosConLluvia = 0
        for ( pedido in this.listaDePedidos ) {
            if(pedido.esConLlluvia()){
                cantidadPedidosConLluvia = cantidadPedidosConLluvia + 1
            }
        }        
        return cantidadPedidosConLluvia
    }

    void liquidarSueldoFinal(){
        int pedidosCompletados = cantidadPedidosCompletados();
        int cantidadPedidosConLluvia = cantidadPedidosConLluvia();
        this.sueldo.calcularAdicionales(pedidosCompletados,cantidadPedidosConLluvia);
    }

}

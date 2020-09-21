package pandenvio

class Repartidor {

    static transients = [ "informacionDelMes"] // No persiste el atributo estado


    String nombre
    String dni
    boolean disponible
    Restaurant restaurant
    Sueldo sueldo = new Sueldo()
    List<Pedido> listaDePedidos = new ArrayList<Pedido>()
    def informacionDelMes = ["cantidadPedidosCompletados": 0,
                             "cantidadPedidosIncompletos": 0,
                             "cantidadPedidosConLluvia": 0,
                             "cantidadPedidosConPuntuacionMala": 0,
                             "cantidadPedidosConPuntuacionPerfecta": 0]


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

    void calcularInformeDeSueldo(){
        //int cantidadPedidosCompletados = 0
        for ( pedido in this.listaDePedidos ) {
            print(pedido.nombreEstado)
            if(pedido.nombreEstado == "no_entregado"){
                this.informacionDelMes["cantidadPedidosIncompletos"]+= 1;
                print(this.informacionDelMes)
            }
            if(pedido.nombreEstado == "entregado"){
                informacionDelMes["cantidadPedidosCompletados"]+= 1;
                if(pedido.esConLlluvia()){
                    this.informacionDelMes["cantidadPedidosConLluvia"]+=1;
                    print(this.informacionDelMes)
                }
                if(pedido.tienePuntuacion()){
                    if(pedido.obtenerEstrellas() < 3){
                        this.informacionDelMes["cantidadPedidosConPuntuacionMala"]+=1;
                        print(this.informacionDelMes)
                    }
                    if(pedido.obtenerEstrellas() == 5){
                        this.informacionDelMes["cantidadPedidosConPuntuacionPerfecta"]+=1;
                        print(this.informacionDelMes)
                    }
                }
            }
        }        
    }

    void liquidarSueldoFinal(){
        this.calcularInformeDeSueldo()
        this.sueldo.calcularAdicionales(informacionDelMes["cantidadPedidosCompletados"], 
                                        informacionDelMes["cantidadPedidosIncompletos"],
                                         informacionDelMes["cantidadPedidosConLluvia"],
                                         informacionDelMes["cantidadPedidosConPuntuacionMala"],
                                         informacionDelMes["cantidadPedidosConPuntuacionPerfecta"]);
    }

}

package pandenvio

class Repartidor {



    static transients = [ "informacionDelMes"]

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


    def reglasDeFacturacion = []

    static mapping = {
        disponible defaultValue: true
    }

    static constraints = {
        nombre nullable: false, blank: false
        dni nullable: false, blank: false
        restaurant nullable: false
        sueldo nullable: true
    }

    Repartidor(String nombre, String dni, Restaurant restaurant, sueldoBase = new Sueldo()) {
        this.nombre = nombre
        this.dni = dni
        this.restaurant = restaurant
        this.disponible = true
        setSueldo(sueldoBase)
        this.listaDePedidos = listaDePedidos;
        cargarReglasDeFacturacion()
    }

    private void cargarReglasDeFacturacion() {
        this.reglasDeFacturacion << new PedidosIncompletos()
        this.reglasDeFacturacion << new PedidosCompletados()
        this.reglasDeFacturacion << new PedidosConLluvia()
        this.reglasDeFacturacion << new PedidosConPuntuacionMenoresA3()
        this.reglasDeFacturacion << new PedidosConPuntuacionIgual5()
    }


    void setSueldo(Sueldo sueldo){
        this.sueldo = sueldo
    }

    void agregarPedido(Pedido pedido){
        this.listaDePedidos.add(pedido)
    }

    void calcularInformeDeSueldo(){
        this.listaDePedidos.forEach {
            p -> this.reglasDeFacturacion.forEach { r -> r.calcularVariacion(p, this.informacionDelMes)}
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

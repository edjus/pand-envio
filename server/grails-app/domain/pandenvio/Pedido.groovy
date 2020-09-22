package pandenvio

class Pedido {

    static transients = [ "estado", "clima" ] // No persiste el atributo estado

    Date fecha
    CuponDescuento cuponDeDescuento
    Cliente cliente
    EstadoPedido estado
    String nombreEstado
    ModalidadEntrega modalidadEntrega
    Restaurant restaurant
    Clima clima
    String nombreClima
    Boolean enRango

    static hasMany = [items: Item]

    static constraints = {
        cliente nullable: false
        modalidadEntrega nullable: false
        restaurant nullable: false
        enRango nullable: false
    }

    static mapping = {
        cuponDeDescuento lazy: false
        modalidadEntrega lazy: false
        items cascade: "all-delete-orphan"
    }

    Pedido(cliente, modalidadEntrega, restaurant, clima = new ClimaNoLluvioso()) {
        this.fecha = new Date()
        this.cliente = cliente
        this.cuponDeDescuento = new CuponDescuentoNulo(fecha: fecha, activo: true, codigo: 'NULO')
        this.setEstado(new EstadoEnArmado())
        this.modalidadEntrega = modalidadEntrega
        this.items = []
        this.restaurant = restaurant
        this.setClima(clima)
        this.enRango = true
    }

    void agregar(Producto producto, Integer cantidad) {
        if (producto.restaurant != this.restaurant){
            throw new ProductoNoPerteneceAlRestauranteException("El producto no pertenece al restaurante")
        }
        Item item = items.find {i -> i.producto == producto}
        if (!item) {
            addToItems(new Item(producto, cantidad, this))
        } else {
            item.cantidad += cantidad
        }
    }


    void setenRango(Boolean rango){
        this.enRango = rango
    }

    BigDecimal calcularPrecio() {
        return this.modalidadEntrega.calcularPrecioCon(this, cuponDeDescuento)
    }

    void setEstado(EstadoPedido nuevoEstado){
        this.estado = nuevoEstado
        setNombreEstado(nuevoEstado.nombre)
    }

    void setPuntuacionAModalidad(Integer calificacionAIngresar){
        if(this.nombreEstado != "entregado"){
            throw new CalificacionException('El servicio del pedido no puede puntuarse, todavia no fue entregado')
        }
        if(this.modalidadEntrega.nombre == "para_retirar"){
            throw new CalificacionException('El servicio del pedido no puede puntuarse por tener una modalidad de retiro')
        }
        if (calificacionAIngresar > 5 || calificacionAIngresar < 0) {
            throw new CalificacionException("La calificacion no puede ser inferior a 0 y superior a 5")
        } 
        this.modalidadEntrega.agregarPuntuacion(calificacionAIngresar)
    }

    void setModalidadEntrega(ModalidadEntrega modalidadEntrega){
        this.modalidadEntrega = modalidadEntrega
    }

    void cambiarModalidad(){
        if (!this.estado.puedeActualizarProductos()) {
            throw  new NoSePuedeActualizarProductoException()
        } 
        this.setModalidadEntrega(this.modalidadEntrega.cambiarModalidad())

    }

    Puntuacion obtenerPuntuacion(){
        if(this.modalidadEntrega.nombre == "para_llevar"){
            ModalidadParaLlevar modalidadLlevar = (ModalidadParaLlevar)this.modalidadEntrega;
            return modalidadLlevar.puntuacion
        }
        
    } 

    Puntuacion setPuntuacion(Integer calificacion){
        if(this.modalidadEntrega.nombre == "para_llevar"){
            ModalidadParaLlevar modalidadLlevar = this.modalidadEntrega;
            modalidadLlevar.puntuacion.estrellas = calificacion
            return this.modalidadEntrega.puntuacion
        }  
    }

    Boolean tienePuntuacion(){
        return this.modalidadEntrega.puntuacion != null
    }


    int obtenerEstrellas(){
        if(this.modalidadEntrega.nombre == "para_llevar"){
            if(modalidadEntrega.tienePuntuacion()){
                return (int) this.modalidadEntrega.cantidadEstrellas()
            }
            return 0
        }   
    }

    void setClima(Clima nuevoClima){
        this.clima = nuevoClima
        setNombreClima(nuevoClima.nombre)
    }

    void siguienteEstado() {
        if (items.size() <= 0) {
            throw new PedidoNoTieneItemsException("El pedido no tiene items no se puede cambiar estado")
        }
        setEstado(this.estado.siguienteEstado(modalidadEntrega))
        this.estado.iniciarPara(this)
    }

    void cancelar() {
        setEstado(this.estado.cancelar())
    }

    Boolean tieneRepartidor(){
        modalidadEntrega.hayRepartidor()
    }

    Boolean puedeCambiarModalidad(){
        def estadosValidos = ["en_armado","recibido","en_preparacion"]
        return (this.nombreEstado in estadosValidos)
    }
    
    void asignarA(Repartidor repartidor){
        this.modalidadEntrega.asignarRepartidor(repartidor)
    }

    void removerProducto(Producto producto){
        if (!this.estado.puedeActualizarProductos()){
            throw  new NoSePudeRemoverProductoException()
        }
        def item = this.items.find {i -> i.producto == producto}
        removeFromItems (item)
    }

    void actualizarCantidad(Producto producto, Integer cantidad) {
        if (!this.estado.puedeActualizarProductos()){
            throw  new NoSePuedeActualizarProductoException()
        }
        def item = this.items.find {i -> i.producto == producto}
        item?.cantidad = cantidad
    }

    void agregarCupon(CuponDescuentoPorcentual cupon) {
        if (!this.estado.puedeActualizarProductos()) {
            throw  new NoSePuedeActualizarProductoException()
        }
        if (!cupon.estaDisponible() || !cupon.perteneceA(this.cliente) || !cupon.creadoPor(this.restaurant)) {
            throw new CuponInvalidoException("El cupón no puede ser agregado")
        }

        cupon.pedidoBeneficiado = this
        setCuponDeDescuento(cupon)
    }





    Boolean estaEntregado(){
        return this.nombreEstado == "entregado"
    }

    Boolean esConLlluvia(){
        return this.nombreClima == "lluvioso"
    }

    // TODO: ver como mejorar ésto y si es necesario, es una asignación manual del estado al cargar el pedido
    def afterLoad() {
        setEstado(FabricaEstados.obtenerEstado(this.nombreEstado))
        setClima(FabricaClima.crearPara(this.nombreClima))
    }
}

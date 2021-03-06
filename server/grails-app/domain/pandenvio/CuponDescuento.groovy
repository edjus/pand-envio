package pandenvio

abstract class CuponDescuento {
    Date fecha
    String codigo
    Pedido pedidoBeneficiado

    static constraints = {
        pedidoBeneficiado nullable: true
    }

    abstract BigDecimal aplicarDescuento(BigDecimal precio, Pedido pedidoBeneficiado)

    boolean permitirEn(Producto producto) {
        producto.admiteA(this)
    }

    boolean estaDisponible() {
        !pedidoBeneficiado
    }

    abstract boolean perteneceA(Cliente cliente)

    abstract boolean creadoPor(Restaurant restaurant)
}
package pandenvio

abstract class CuponDescuento {
    Date fecha;
    String codigo;
    Pedido pedidoBeneficiado;

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

    boolean esDe(Cliente cliente){
        true
    }
}
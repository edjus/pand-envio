package pandenvio

abstract class CuponDescuento {
    Date fecha;
    Boolean activo;
    String codigo;

    abstract BigDecimal aplicarDescuento(BigDecimal precio) throws CuponYaUtilizadoException

    void permitirEn(Producto producto) {
        producto.admiteA(this)
    }

    boolean estoyActivo() {
        activo
    }
}
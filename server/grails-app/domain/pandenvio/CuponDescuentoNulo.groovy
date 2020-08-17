package pandenvio

class CuponDescuentoNulo extends CuponDescuento {

    @Override
    BigDecimal aplicarDescuento(BigDecimal precio) throws CuponYaUtilizadoException {
        return precio
    }
}

package pandenvio

class CuponDescuentoPorcentual extends CuponDescuento {

    BigDecimal porcentaje

    static constraints = {
        porcentaje(range: 1..99)
    }

    @Override
    BigDecimal aplicarDescuento(BigDecimal precio) throws CuponYaUtilizadoException {
        if (!this.activo){
            throw new CuponYaUtilizadoException()
        }
        this.activo = false;
        precio * (1-(porcentaje/100))
    }
}

package pandenvio

class CuponDescuentoPorcentual extends CuponDescuento {

    static belongsTo = [cliente: Cliente]

    BigDecimal porcentaje


    static constraints = {
        porcentaje(range: 1..99)
    }

    boolean esValido() {
      return (this.porcentaje > 0)
   }

    @Override
    BigDecimal aplicarDescuento(BigDecimal precio, Pedido pedidoBeneficiado) {
        if (!estaDisponible() && this.pedidoBeneficiado != pedidoBeneficiado) {
            throw new CuponYaUtilizadoException()
        }
        precio * (1 - (porcentaje / 100))
    }

    @Override
    boolean esDe(Cliente cliente){
        this.cliente == cliente
    }
}

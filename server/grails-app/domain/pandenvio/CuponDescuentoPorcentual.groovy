package pandenvio

class CuponDescuentoPorcentual extends CuponDescuento {

    static belongsTo = [cliente: Cliente]

    BigDecimal porcentaje
    Restaurant restaurant

    static constraints = {
        porcentaje(range: 1..99)
        restaurant nullable: false
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
    boolean perteneceA(Cliente cliente){
        this.cliente == cliente
    }
}

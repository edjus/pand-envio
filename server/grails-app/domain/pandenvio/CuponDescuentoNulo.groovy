package pandenvio

class CuponDescuentoNulo extends CuponDescuento {

    @Override
    BigDecimal aplicarDescuento(BigDecimal precio, Pedido pedidoBeneficiado) {
        precio
    }

    @Override
    boolean perteneceA(Cliente cliente){
        true
    }

    @Override
    boolean creadoPor(Restaurant restaurant) {
        true
    }
}

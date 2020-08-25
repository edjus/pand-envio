package pandenvio

class ModalidadParaRetirar extends ModalidadEntrega {

    static constraints = {
    }

    @Override
    def calcularPrecioCon(CuponDescuento cuponDescuento, List<Item> items) {
        return null
    }
}

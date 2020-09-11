<template >
    <div class="jumbotron">
        <div class="card" v-if="pedido">
          <div class="card-header bg-dark text-light ml-0 row container">
            <div class="col-6">
                <i class="fas fa-shopping-cart"></i>&nbsp;
                Pedido #{{ pedido.id }}
            </div>
            <div class="col-6 text-right">
              <a href="/catalogo" class="btn btn-outline-info btn-sm">Continuar comprando</a>
            </div>
          </div>
          <div class="card-body">
            <item-pedido v-for='item in pedido.items' :item='item' :key='item.id'></item-pedido>
            <div class="row cupon-total">
              <div class="col-12 col-md-5">
                <!-- TODO: AGREGAR componente cupon (input y botón activar) -->
              </div>
              <div class="col-12 col-md-6 offset-md-1 col-lg-4 offset-lg-3 pr-4">
                <ul class="list-group">
                  <li class="list-group-item d-flex justify-content-between bg-light">
                    <span class="text-info">Total (Pesos)</span>
                    <strong class="text-info">$ <span id="txtOrderTotal">{{pedido.precio}}</span></strong>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div class="card-footer">
              <div class="col-12 col-lg-4 offset-lg-8 col-md-6 offset-md-6">
                  <a href=# class="btn btn-success form-control">
                    Confirmar pedido
                  </a>
              </div>
          </div>
        </div>
        <div v-else>
          <span>No tiene items agregado al pedido</span>
        </div>
      </div>
</template>

<script>
import * as AppService from '../../services/AppService'
import ItemPedido from './ItemPedido'

export default {
  name: 'Pedido',
  components: {ItemPedido},
  data () {
    return {
      pedido: null
    }
  },
  async created () {
    try {
      this.pedido = await AppService.obtenerPedidoActual()
      console.log(this.pedido)
    } catch (error) {
      console.log(`error: ${error}`)
    }
  }
}
</script>

<style scoped>
.cupon-total {
  padding-top: 10px;
}
</style>

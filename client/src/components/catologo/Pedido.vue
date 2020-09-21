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
            <item-pedido v-for='item in pedido.items' :item='item' :key='item.id'
            @sumarUno="sumarUno" @restarUno="restarUno" @removerProducto="removerProducto">
            </item-pedido>
             <form class="mt-4">
                <div class="form-group row">
                  <label for="modalidad" class="col-sm-2 col-form-label">Modalidad de Entrega</label>
                  <div class="col-sm-2">
                      <select name="modalidad" id="modalidad" class="form-control" v-model="pedido.modalidad" v-on:change="cambiarModalidadPedido">
                        <option value="para_llevar">Para LLevar</option>
                        <option value="para_retirar">Para Retirar</option>
                      </select>
                  </div>
                </div>
               <div class="form-group row">
                 <div class="col-sm-2">Distancia</div>
                 <div class="col-sm-10">
                   <div class="form-check">
                     <input class="form-check-input" type="checkbox" v-model="pedido.enRango" v-on:change="cambiarRangoPedido">
                     <label class="form-check-label">
                       Estoy dentro del radio de reparto gratuito del restaurante
                     </label>
                   </div>
                 </div>
               </div>
              </form>

            <div class="row cupon-total">
              <cupon-pedido :cupon="pedido.cupon" @aplicarCupon="aplicarCupon"></cupon-pedido>
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
                  <a href=# class="btn btn-success form-control" @click="confirmarPedido">
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
import CuponPedido from './CuponPedido'

export default {
  name: 'Pedido',
  components: {ItemPedido, CuponPedido},
  data () {
    return {
      pedido: null
    }
  },
  methods: {
    async confirmarPedido () {
      try {
        await AppService.confirmarPedido(this.pedido.id)
        this.pedido = null
      } catch (error) {
        console.log(`Error: ${error}`)
      }
    },
    async cambiarModalidadPedido () {
      await AppService.cambiarModalidadPedido(this.pedido.id)
      this.pedido = await AppService.obtenerPedidoActual()
    },
    async cambiarRangoPedido () {
      await AppService.cambiarRangoPedido(this.pedido.id)
      this.pedido = await AppService.obtenerPedidoActual()
    },
    async sumarUno (productoId, cantidad) {
      cantidad++
      if (cantidad > 0) { this.pedido = await AppService.editarProducto(this.pedido.id, productoId, cantidad) }
    },
    async restarUno (productoId, cantidad) {
      cantidad--
      if (cantidad > 0) { this.pedido = await AppService.editarProducto(this.pedido.id, productoId, cantidad) }
    },
    async removerProducto (productoId) {
      this.pedido = await AppService.removerProducto(this.pedido.id, productoId)
    },
    async aplicarCupon (codigoCupon) {
      try {
        this.pedido = await AppService.aplicarCupon(this.pedido.id, codigoCupon)
      } catch (error) {
        console.log(error.response.data.error)
      }
    }
  },
  async created () {
    this.pedido = await AppService.obtenerPedidoActual()
    console.log(this.pedido)
  }
}
</script>

<style scoped>
.cupon-total {
  padding-top: 10px;
}
</style>

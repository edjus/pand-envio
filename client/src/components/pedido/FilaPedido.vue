<template>
  <tr>
    <td>{{ item.id }}</td>
    <td>{{ item.fecha }}</td>
    <td>{{ item.cliente.nombre }}</td>
    <td>{{ item.estado}}</td>
    <td>{{ item.modalidad}}</td>
    <td>{{ '$' + item.precio}}</td>
    <td>{{ item.restaurant.nombre }}</td>
    <td><star-rating :star-size="20" v-model="rating" @rating-selected="setRating" :read-only="soloLectura"></star-rating></td>
    <td>
      <button class='btn btn-primary' v-on:click="avanzar"><i class='fas fa-arrow-circle-right'></i> Avanzar estado</button>
      <button class='btn btn-danger' v-on:click="cancelar"><i class='fas fa-window-close'></i> Cancelar</button>
      <button class="btn btn-warning" v-on:click="noEntregado" :disabled="soloLectura"><i class="fas fa-ghost"></i> No entregado</button>
    </td>
  </tr>
</template>

<script>

import StarRating from 'vue-star-rating'
import {obtenerPuntuacionPedido} from '../../services/PedidoService'
import {esRol} from '../../services/AutenticacionService'

export default {
  components: {StarRating},
  name: 'FilaPedido',
  props: ['item'],
  data: function () {
    return {
      rating: null,
      soloLectura: !esRol(['admin', 'cliente'])
    }
  },
  async created () {
    this.rating = await obtenerPuntuacionPedido(this.item.id)
  },
  methods: {
    setRating: function (rating) {
      this.rating = rating
      this.$emit('actualizarPuntuacion', {pedido: this.item, puntuacion: this.rating})
    },
    avanzar: function () {
      this.$emit('avanzarPedido', this.item)
    },
    cancelar: function () {
      this.$emit('cancelarPedido', this.item)
    },
    noEntregado: function () {
      this.$emit('noEntregado', this.item)
    }
  }

}
</script>

<style scoped>

</style>

<template>
  <div id="catalogo">
    <h4>Platos</h4>
    <br>
    <ul :style="gridStyle" class="card-list">
      <li v-for="(producto, index) in productos" :key="index" class="card-item">
        <card-producto :producto="producto" @agregarProducto="agregarItem"></card-producto>
      </li>
    </ul>
  </div>
</template>

<script>
import * as AppService from '../../services/AppService'
import CardProducto from './CardProducto'

export default {
  name: 'Catologo',
  components: {CardProducto},
  data () {
    return {
      productos: [],
      numeroDeColumnas: 3,
      serverURL: process.env.SERVER_URL
    }
  },
  created () {
    this.obtenerProductos()
  },
  computed: {
    gridStyle () {
      return {
        gridTemplateColumns: `repeat(${this.numeroDeColumnas}, minmax(100px, 1fr))`
      }
    }
  },
  methods: {
    async obtenerProductos () {
      const platos = await AppService.obtenerPlatos()
      this.productos = platos
    },

    async agregarItem (producto) {
      try {
        await AppService.agregarItem(producto.id, producto.restaurant.id)
        this.$emit('actualizarItems')
      } catch (error) {
        console.log(`error:  ${error.data}`)
      }
    }
  }
}
</script>

<style scoped>

.card-list {
  display: grid;
  grid-gap: 1em;
}

.card-item {
  background-color:#264d73;
  padding: 1em;
}

.card-item:hover {
  background-color: #669999;
}

body {
  background: #20262E;
  padding: 20px;
  font-family: Helvetica;
}

#catalogo {
  background: #fff;
  border-radius: 4px;
  padding: 20px;
  transition: all 0.2s;
}

ul {
  list-style-type: none;
}

</style>

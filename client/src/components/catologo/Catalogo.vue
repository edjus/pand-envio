<template>
  <div id="catalogo">
    <h4>Platos</h4>
    <br>
    <ul :style="gridStyle" class="card-list">
      <li v-for="(producto, index) in productos" v-bind:key="index" class="card-item">
        <!-- TODO: CREAR COMPONENTE pasandole el producto-->
        <div class="card" style="width: 18rem;">
          <div class="card-body">
            <h5 class="card-title">{{ producto.nombre }} - {{ producto.id }}</h5>
            <h6 class="card-subtitle mb-2 text-muted">{{ producto.descripcion }}</h6>
            <p class="card-text">Precio: {{ producto.precio }}</p>
            <a href="#" class="card-link">Card link</a>
            <a href="#" class="card-link">Another link</a>
          </div>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>
import * as AppService from '../../services/AppService'

export default {
  name: 'Catologo',
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

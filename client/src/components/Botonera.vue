<template>
  <nav class="col-2 d-none d-md-block bg-light sidebar">
    <div class="sidebar-sticky" v-if="esRol('admin')">
      <ul class="nav flex-column">
        <li class="nav-item">
          <router-link to="/" class="nav-link" v-bind:class="getClass('/')">
            <i class="fas fa-home mr-1"></i> Dashboard
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/restaurants" class="nav-link" v-bind:class="getClass('/restaurants')">
            <i class="fas fa-utensils mr-2"></i> Restaurants
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/pedidos" class="nav-link" v-bind:class="getClass('/pedidos')">
            <i class="fas fa-shopping-cart"></i> Pedidos
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/clientes" class="nav-link" v-bind:class="getClass('/clientes')">
            <i class="fas fa-users"></i> Clientes
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/repartidores" class="nav-link" v-bind:class="getClass('/repartidores')">
            <i class="fas fa-motorcycle"></i> Repartidores
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/cupones" class="nav-link" v-bind:class="getClass('/cupones')">
            <i class="fas fa-tags"></i> Cupones
          </router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import { esRol } from '../services/AutenticacionService'

export default {
  name: 'Botonera',
  methods: {
    getClass (property) {
      return property === this.$route.path ? 'active' : ''
    },

    esRol (rol) {
      return esRol(rol)
    }
  }
}
</script>

<style scoped>

</style>

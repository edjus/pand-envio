<template>
  <nav class="col-2 d-none d-md-block bg-light sidebar">
    <div class="sidebar-sticky">
      <ul class="nav flex-column">
        <li class="nav-item">
          <router-link to="/" class="nav-link" v-bind:class="getClass('/')">
            <i class="fas fa-home mr-1"></i> Dashboard
          </router-link>
        </li>
        <li class="nav-item" v-if="esRol('admin')">
          <router-link to="/restaurants" class="nav-link" v-bind:class="getClass('/restaurants')">
            <i class="fas fa-utensils mr-2"></i> Restaurants
          </router-link>
        </li>
        <li class="nav-item" v-if="esRol(['admin','duenio'])">
          <router-link to="/platos" class="nav-link" v-bind:class="getClass('/platos')">
            <i class="fas fa-hamburger mr-2"></i> Platos
          </router-link>
        </li>
        <li class="nav-item" v-if="esRol('admin')">
          <router-link to="/menues" class="nav-link" v-bind:class="getClass('/menues')">
            <i class="fas fa-layer-group mr-2"></i> Menues
          </router-link>
        </li>
        <li class="nav-item" v-if="esRol(['admin','duenio'])">
          <router-link to="/pedidos" class="nav-link" v-bind:class="getClass('/pedidos')">
            <i class="fas fa-shopping-cart"></i> Pedidos
          </router-link>
        </li>
        <li class="nav-item" v-if="esRol('admin')">
          <router-link to="/clientes" class="nav-link" v-bind:class="getClass('/clientes')">
            <i class="fas fa-users"></i> Clientes
          </router-link>
        </li>
        <li class="nav-item" v-if="esRol(['admin','duenio'])">
          <router-link to="/repartidores" class="nav-link" v-bind:class="getClass('/repartidores')">
            <i class="fas fa-motorcycle"></i> Repartidores
          </router-link>
        </li>
        <li class="nav-item" v-if="esRol(['repartidor'])">
          <router-link to="/verSueldo" class="nav-link" v-bind:class="getClass('/verSueldo')">
            <i class="fas fa-money-check-alt"></i> Ver sueldo
          </router-link>
        </li>
        <li class="nav-item" v-if="esRol('admin')">
          <router-link to="/cupones" class="nav-link" v-bind:class="getClass('/cupones')">
            <i class="fas fa-tags"></i> Cupones
          </router-link>
        </li>
        <li class="nav-item" v-if="esRol('admin')">
          <a href="#" v-on:click="invocarLLuvia" class="nav-link"><i class="fas fa-cloud-rain"></i> Invocar LLuvia</a>
        </li>
        <li class="nav-item" v-if="esRol('admin')">
          <a href="#" v-on:click="invocarSol" class="nav-link"><i class="fas fa-sun"></i> Llamar al Sol</a>
        </li>
        <li class="nav-item" v-if="esRol('cliente')">
          <router-link to="/catalogo" class="nav-link" v-bind:class="getClass('/catalogo')">
            <i class="fas fa-book-open"></i> Catálogo
          </router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import { esRol } from '../services/AutenticacionService'
import {cambiarAClimaSoleado, cambiarAClimaLLuvioso} from '../services/AppService'

export default {
  name: 'Botonera',
  methods: {
    getClass (property) {
      return property === this.$route.path ? 'active' : ''
    },

    esRol (rol) {
      return esRol(rol)
    },
    async invocarLLuvia () {
      const clima = await cambiarAClimaLLuvioso()
      this.$root.$emit('nuevoClima', clima)
    },
    async invocarSol () {
      const clima = await cambiarAClimaSoleado()
      this.$root.$emit('nuevoClima', clima)
    }
  }
}
</script>

<style scoped>

</style>

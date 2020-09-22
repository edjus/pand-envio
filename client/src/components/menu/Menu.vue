<template>
  <div>
    <div class='d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom'>
      <h1 class='h2'>Menus</h1>
      <div class='btn-toolbar mb-2 mb-md-0'>
        <button class='btn btn-lg btn-success' v-on:click='abrirFormulario'>
          <i class='fas fa-plus'></i> Agregar
        </button>
      </div>
    </div>
    <tabla-menu :menues='menues' @edicionMenu="editarMenu"></tabla-menu>
    <formulario-menu ref='modal' :menu="menuActual" :restaurantes="restaurantes" :platos="platos" @confirmacionMenu="guardarMenu"></formulario-menu>
  </div>
</template>

<script>
import TablaMenu from './TablaMenu'
import FormularioMenu from './FormularioMenu'
import {getRestauranteIdLogueado} from '../../services/AutenticacionService'
import {cargarMenu, cargarMenuRestaurant} from '../../services/MenuService'
import {cargarRestaurantes} from '../../services/RestaurantService'

export default {
  name: 'Menu',
  components: {FormularioMenu, TablaMenu},
  data: function () {
    return {
      menues: [],
      platos: [],
      restaurantes: [],
      menuActual: this.nuevoMenu(),
      serverURL: process.env.SERVER_URL
    }
  },
  async created () {
    this.cargarPlatos()
    this.cargarMenues()
    this.restaurantes = await cargarRestaurantes(getRestauranteIdLogueado())
  },
  methods: {
    clearNotifications: function () {
      this.$notify({
        group: 'notifications',
        clean: true
      })
    },
    editarMenu: function (menu) {
      this.menuActual = Object.assign({}, menu)
      this.$refs.modal.show()
    },
    nuevoMenu: function () {
      return {
        id: 0,
        nombre: '',
        precio: 0,
        categoria: null,
        descripcion: '',
        restaurant: null
      }
    },
    actualizarListadoMenus: function (menu) {
      const index = this.menues.findIndex(x => x.id === menu.id)
      if (index >= 0) {
        this.menues[index] = menu
        this.menues = this.menues.map(r => r)
      } else {
        this.menues.push(menu)
      }
    },
    guardarMenu: function (menu) {
      this.clearNotifications()
      if (menu.id === 0) {
        delete menu.id
      }
      fetch(`${this.serverURL}/menu`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(menu)
      }).then(r => r.json())
        .then(json => {
          if (!(json._embedded && json._embedded.errors)) {
            this.$notify({
              group: 'notifications',
              type: 'success',
              title: 'Menu guardado exitosamente'
            })
            this.actualizarListadoMenus(json.menu)
            this.menuActual = this.nuevoMenu()
          } else {
            console.error('Error cargando menues: ' + json._embedded.errors[0])
            this.$notify({
              group: 'notifications',
              type: 'error',
              duration: 5000,
              title: 'No se puede guardar el menu',
              text: json._embedded.errors[0]
            })
          }
        })
        .catch(error => {
          console.error(error)
        })
    },
    abrirFormulario: function () {
      this.menuActual = this.nuevoMenu()
      this.$refs.modal.show()
    },
    cargarMenues: async function () {
      this.clearNotifications()
      const restauranteId = getRestauranteIdLogueado()
      try {
        if (restauranteId) {
          this.menues = await cargarMenuRestaurant(restauranteId)
        } else {
          this.menues = await cargarMenu()
        }
        this.$notify({
          group: 'notifications',
          type: 'success',
          title: 'Carga de menues exitosa'
        })
      } catch (error) {
        console.error('Error cargando menues: ' + error)
        this.$notify({
          group: 'notifications',
          type: 'error',
          title: 'No se pueden cargar menues',
          text: error
        })
      }
    },
    cargarPlatos: async function () {
      const restauranteId = getRestauranteIdLogueado()
      let url = `${this.serverURL}/plato`
      if (restauranteId) {
        url += '/restaurant/' + restauranteId
      }
      fetch(url)
        .then(r => r.json())
        .then(json => {
          this.platos = json
        })
        .catch(error => console.error('Error cargando platos: ' + error))
    }
  }
}
</script>

<style scoped>

</style>

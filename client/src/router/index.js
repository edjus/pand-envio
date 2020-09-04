import Vue from 'vue'
import Router from 'vue-router'
import Restaurants from '../components/restaurant/Restaurants'
import Dashboard from '../components/Dashboard'
import Clientes from "../components/cliente/Clientes";

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Dashboard',
      component: Dashboard
    },
    {
      path: '/restaurants',
      name: 'Restaurants',
      component: Restaurants
    },
    {
      path: '/clientes',
      name: 'Clientes',
      component: Clientes
    }
  ]
})

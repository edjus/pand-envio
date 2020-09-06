import Vue from 'vue'
import Router from 'vue-router'
import Restaurants from '../components/restaurant/Restaurants'
import Dashboard from '../components/Dashboard'
import Clientes from '../components/cliente/Clientes'
import { esRol } from '../services/AutenticacionService'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Dashboard',
      component: Dashboard,
      meta: {
        publico: true
      }
    },
    {
      path: '/restaurants',
      name: 'Restaurants',
      component: Restaurants,
      meta: {
        publico: false,
        rol: 'admin'
      }
    },
    {
      path: '/clientes',
      name: 'Clientes',
      component: Clientes,
      meta: {
        publico: false,
        rol: 'admin'
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (!to.meta.publico && !esRol(to.meta.rol)) {
    next({
      path: '/'
    })
  } else {
    next()
  }
})

export default router

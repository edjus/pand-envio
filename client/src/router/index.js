import Vue from 'vue'
import Router from 'vue-router'
import Restaurants from '../components/restaurant/Restaurants'
import Dashboard from '../components/Dashboard'
import Clientes from '../components/cliente/Clientes'
import Catalogo from '../components/catologo/Catalogo'
import Pedido from '../components/catologo/Pedido'
import { esRol } from '../services/AutenticacionService'
import Repartidor from '../components/repartidor/Repartidor'
import Cupon from '../components/cupon/Cupon'
import Plato from '../components/plato/Plato'
import Menu from '../components/menu/Menu'
import ListadoPedido from '../components/pedido/Pedido'
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
        rol: ['admin']
      }
    },
    {
      path: '/clientes',
      name: 'Clientes',
      component: Clientes,
      meta: {
        publico: false,
        rol: ['admin']
      }
    },
    {
      path: '/catalogo',
      name: 'Catalogo',
      component: Catalogo,
      meta: {
        publico: false,
        rol: ['cliente']
      }
    },
    {
      path: '/pedido',
      name: 'Pedido',
      component: Pedido,
      meta: {
        publico: false,
        rol: ['cliente']
      }
    },
    {
      path: '/repartidores',
      name: 'Repartidor',
      component: Repartidor,
      meta: {
        publico: false,
        rol: ['admin']
      }
    },
    {
      path: '/cupones',
      name: 'Cupon',
      component: Cupon,
      meta: {
        publico: false,
        rol: ['admin']
      }
    },
    {
      path: '/platos',
      name: 'Plato',
      component: Plato,
      meta: {
        publico: false,
        rol: ['admin']
      }
    },
    {
      path: '/menues',
      name: 'Menu',
      component: Menu,
      meta: {
        publico: false,
        rol: ['admin']
      }
    },
    {
      path: '/pedidos',
      name: 'Pedido',
      component: ListadoPedido,
      meta: {
        publico: false,
        rol: ['admin','duenio']
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

import Vue from 'vue'
import Router from 'vue-router'
import Restaurants from '../components/Restaurants'
import Dashboard from '../components/Dashboard'

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
    }
  ]
})

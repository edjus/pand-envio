// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import 'bootstrap'
import 'jquery'
import 'popper.js'
import router from './router'
import './assets/css/bootstrap.css'
import './assets/css/main.css'
import Notifications from "vue-notification";
import { library, dom } from '@fortawesome/fontawesome-svg-core'
import { faSpinner, fas } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
library.add(fas)
library.add(faSpinner)
dom.watch()
Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.use(Notifications);

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

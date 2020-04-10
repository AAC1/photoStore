import Vue from 'vue'
import VueRouter from 'vue-router'

import App from './App.vue'
import GestProducto from './components/GestionProducto.vue';
import GestCostProducto from './components/GestionCostProducto.vue';
import Home from './components/Home.vue';
//import Menu from './components/Menu.vue';

const routes = [
  { path: '/gestion/producto', component: GestProducto },
  { path: '/gestion/costo-producto', component:GestCostProducto  },
  { path: '/reportes', component: Home  },
  { path: '/', component: Home  },
  { path: '/logout', component:Home  }
]
const router = new VueRouter({
  routes // short for `routes: routes`
})
//window.$ = require('./assets/js/jquery-4.2.1.min.js')
//window.JQuery = require('./assets/js/jquery-4.2.1.min.js')

Vue.config.productionTip = false
Vue.use(VueRouter)

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');

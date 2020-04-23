import Vue from 'vue';
import VueRouter from 'vue-router';
import GestProducto from './components/GestionProducto.vue';
import GestCostProducto from './components/GestionCostProducto.vue';
import Home from './components/Home.vue';
import Reporte from './components/Reporte.vue';


Vue.use(VueRouter);

const routes = [
    { path: '/gestion/producto', component: GestProducto },
    { path: '/gestion/costo-producto', component:GestCostProducto  },
    { path: '/reportes', component: Reporte  },
    { path: '/', component: Home  },
    { path: '/logout', component:Home  }
  ]
  
  
export default new VueRouter({
    routes
})
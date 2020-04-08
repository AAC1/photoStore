import Vue from 'vue'
import App from './App.vue'

window.$ = require('./assets/js/jquery-4.2.1.min.js')
window.JQuery = require('./assets/js/jquery-4.2.1.min.js')

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
}).$mount('#app')
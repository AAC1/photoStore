<template>
  <div id="app">
    
    <Menu />
  </div>
</template>

<script>
import Menu from './components/Menu.vue'
import { Component, Vue } from 'vue-property-decorator'
@Component({
  components: {
    Menu
  }
})
export default class MainApp extends Vue {

  /*name: 'MainApp',
  components: {
    Menu
  }
  */
}
</script>


<style>
@import './assets/css/bootstrap.min.css';
@import './assets/css/style.css';


#app {
  /*
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;*/
}

</style>

<template>
  <div id="app">
    
    <Menu />
  </div>
</template>

<script>
import Menu from './components/Menu.vue'

export default {
  name: 'MainApp',
  components: {
    Menu
  }
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

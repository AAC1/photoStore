<template>
  <div id="app">
    
    <Menu />
  </div>
</template>

<script>
import Menu from './components/Menu.vue'

export default {
  name: 'App',
  components: {
    Menu
  }
}
</script>

<!--
    <script src="./assets/js/angular.min.js"></script>
    <script src="./assets/js/angular-route.min.js"></script>
    <script src="./assets/js/angular-animate.min.js"></script>
    <script src="./assets/js/angular-messages.min.js"></script>
    <script src="./assets/js/angular-aria.min.js"></script>
    <script src="./assets/js/angular-touch.min.js"></script>
    <script src="./assets/js/angular-material.min.js"></script>
    -->

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

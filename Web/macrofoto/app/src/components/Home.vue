
<template>

<div id="app-home" >

  <h2>Esto es Home </h2><br/>
<v-container>
  <h2>Inputs </h2>
  <v-text-field label="TEXT1"  ></v-text-field>
  <v-text-field label="TEXT2"  ></v-text-field>
  <v-text-field label="TEXT3"  ></v-text-field>
  <v-text-field label="TEXT4"  ></v-text-field>
</v-container>
</div>

</template>

<script>


export default {
  name: 'app-home',
  props: {
  //    showBackgroundImg:Boolean
   // msg: String
  },
  methods: {

  }

}
/*
new Vue({
  el: '#app-home',
  data: {
    showBackgroundImg: true
  },
  methods: {
      toggleMenu:function(){

      }
  }
})
*/
</script>

<style scoped>

</style>

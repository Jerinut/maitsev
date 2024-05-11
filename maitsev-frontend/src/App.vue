<template>

<Header isAuthorized="false"/>
  <router-view/>
</template>

<script>

import Header from './components/Header.vue';

export default {
  name: 'App',
  components: {
    Header

  }
}
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

main, body{
  margin: 0;
  box-sizing: border-box;
  font-size: 16px;
}
</style>

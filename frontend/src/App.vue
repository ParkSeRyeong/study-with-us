<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  created() {
    this.$store.state.window_width = window.innerWidth
    this.$store.state.window_height = window.innerHeight
  }
}
</script>


<style>

body {
  margin: 0px;
}

</style>

<template>
  <div class="navbar">
    <a href="#home" class="active">Home</a>
    <a href="#news">News</a>
    <a href="#contact">Contact</a>
    <button @click="logout()">logout</button>

  </div>
</template>

<script>
export default {
  methods: {
    logout () {
      this.$store.dispatch('login/deleteJWT')
      this.$router.push({ name: 'EntrancePage' })
    } 
  }
}
</script>

<style>
.navbar {
  overflow: hidden;
  background-color: #333;
  position: fixed;
  bottom: 0;
  width: 100%;
}

.navbar a {
  float: left;
  display: block;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  font-size: 17px;
}

.navbar a:hover {
  background: #f1f1f1;
  color: black;
}

.navbar a.active {
  background-color: #04aa6d;
  color: white;
}
</style>

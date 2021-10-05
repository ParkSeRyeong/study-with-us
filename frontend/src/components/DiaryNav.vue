<template>
  <div class="navitem">
    <router-link
      :to="{ path: `/diary` }"
      routerLinkActive="active"
      class="turnblack"
      >일간</router-link
    >
    <router-link
      :to="{ path: `/weeklydiary` }"
      routerLinkActive="active"
      class="turnblack"
      >주간</router-link
    >
    <router-link
      :to="{ path: `/monthly` }"
      routerLinkActive="active"
      class="turnblack"
      >월간</router-link
    >
  </div>
</template>

<script>
export default {
  methods: {},
};
</script>

<style type="text/css">
.turnblack {
  color: black;
  text-decoration: none;
}

.router-link-active {
  color: #00b3b9;
  text-decoration: none;
  border-bottom: 2px solid #00b3b9;
  padding-bottom: 1vh;
  font-weight: 900;
}
.navitem {
  display: flex;
  justify-content: space-around;
  padding-top: 4vh;
  font-family: nanumsquare;
  font-size: 4vw;
}
</style>

<template>
  <section class="title-bar">
    <h1 class="container mx-auto">
      <slot></slot>
    </h1>
  </section>
</template>

<script>
export default {
  name: "TitleBar",
};
</script>

<style>
.title-bar {
  background-color: #bceef1;
  height: 7vh;
}

.container {
  font-size: 7vw;
  vertical-align: middle;
  font-family: "IM_Hyemin-Regular";
  font-weight: 600;
  line-height: 8vh;
}
</style>

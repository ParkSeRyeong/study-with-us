<template>
  <section class="title-bar-design">
    <h1 class="title-bar-contents mx-auto">
      <slot></slot>
    </h1>
  </section>
</template>

<script scoped>
export default {
  name: "TitleBar",
};
</script>

<style>
.title-bar-design {
  background-color: #bceef1;
  height: 7vh;
}

.title-bar-contents {
  font-size: 7vw;
  vertical-align: middle;
  font-family: "IM_Hyemin-Regular";
  font-weight: 600;
  line-height: 8vh;
}
</style>

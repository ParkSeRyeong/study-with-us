<template>
  <section class="title-bar">
    <h1 class="container mx-auto">
      <slot></slot>
    </h1>
  </section>
</template>

<script>
export default {
  name: 'TitleBar'
}

</script>

<style>
  .title-bar {
      background-color: #f5ff6971;
      height: 7vh;
  }

  .container {
    font-size: 25px;
    font-family: 'NanumBarunGothic-Bold';
    vertical-align: middle;
    line-height: 8vh;
  }

  
</style> 
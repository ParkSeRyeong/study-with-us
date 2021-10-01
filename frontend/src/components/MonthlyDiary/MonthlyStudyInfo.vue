<template>
  <div class="container-center-horizontal infoFont">
    <div class="">
      <div class="d-flex timeInfo">
        <span class="highlightFocusFont">세령</span>
        <span class="">님이 공부에 </span>
        <span class="focusFont">집중한 순공 시간</span>
        <span class="mt-3">은</span>
      </div>

      <!-- total focus time 넣기 -->
      <div class="flex justify-content-center avg-time timeInfo">
        15 : 23 : 21
      </div>

      <div class="d-flex timeInfo">
        <span class="highlightOtherFont">세령</span>
        <span class="">님이 공부에 </span>
        <span class="otherFont">집중하지 못한 시간</span>
        <span class="">은</span>
      </div>

      <!-- total other time 넣기 -->
      <div class="flex justify-content-center avg-time timeInfo">
        15 : 23 : 21
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style>
.infoFont {
  font-size: 3vw;
}
.focusFont {
  color: blue;
  font-weight: bold;
}
.otherFont {
  color: red;
  /* font-size: 3.5vw; */
  font-weight: bold;
}
.highlightFocusFont {
  font-size: 5vw;
  font-weight: bold;
}
.highlightOtherFont {
  font-size: 5vw;
  font-weight: bold;
}
.timeInfo {
  padding-bottom: 3vh;
}
</style>

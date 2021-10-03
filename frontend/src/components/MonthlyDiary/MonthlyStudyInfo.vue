<template>
  <div
    class="container-center-horizontal infoFont"
    style="font-family:'nanumsquare';"
  >
    <div class="">
      <div class="d-flex timeInfo">
        <span class="highlightFocusFont">세령</span>
        <span class="">님이 공부에 </span>
        <span class="focusFont">집중한 순공 시간</span>
        <span class="mt-3">은</span>
      </div>

      <!-- total focus time 넣기 -->
      <div class="avg-time timeInfo timeCenter">
        {{ this.$store.state.diary.focustime }}
      </div>

      <div class="d-flex timeInfo">
        <span class="highlightOtherFont">세령</span>
        <span class="">님이 공부에 </span>
        <span class="otherFont">집중하지 못한 시간</span>
        <span class="">은</span>
      </div>

      <!-- total other time 넣기 -->
      <div class="avg-time timeInfo timeCenter">
        15 : 23 : 21
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      focustime: "",
      othertime: "",
    };
  },
  mounted() {
    this.getTimes();
  },
  methods: {
    getTimes() {
      console.log(this.$store.state.diary.focustime);
      this.focustime = this.$store.state.diary.focustime;
      this.othertime = this.$store.state.diary.othertime;
    },
  },
};
</script>

<style>
.infoFont {
  font-size: 4vw;
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
  font-size: 6vw;
  font-weight: bold;
}
.highlightOtherFont {
  font-size: 6vw;
  font-weight: bold;
}
.timeInfo {
  padding-bottom: 3vh;
}
.timeCenter {
  text-align: center;
  font-size: 6vw;
  font-family: "IM_Hyemin-Regular";
}
</style>

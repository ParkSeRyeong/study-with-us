<template>
  <div style="width:100%; padding-bottom:12vh;">
    <div class="box infoFont" style="font-family:'nanumsquare';">
      <div>{{ test }}</div>

      <div class="">
        <div class="d-flex timeInfo box">
          <span class="highlightFocusFont">시은</span>
          <span class="">&nbsp;님이 공부에 </span>
          <span class="focusFont">&nbsp;집중한 순공 시간</span>
          <span>은</span>
        </div>

        <!-- total focus time 넣기 -->
        <div class="avg-time timeInfo timeCenter box">
          {{ focustime }}
        </div>

        <div class="d-flex timeInfo box">
          <span class="highlightOtherFont">시은</span>
          <span class="">&nbsp;님이 공부에 </span>
          <span class="otherFont">&nbsp;집중하지 못한 시간</span>
          <span class="">은</span>
        </div>

        <!-- total other time 넣기 -->
        <div class="avg-time timeInfo timeCenter">
          {{ othertime }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isWeekly: false,
    };
  },

  computed: {
    monthly: function() {
      return this.$store.state.monthlydiary.isWeekly;
    },
    weekly: function() {
      return this.$store.state.weeklydiary.isWeekly;
    },
    focustime: function() {
      if (this.isWeekly) {
        return this.$store.state.weeklydiary.focustime;
      } else {
        return this.$store.state.monthlydiary.focustime;
      }
    },
    othertime: function() {
      if (this.isWeekly) {
        return this.$store.state.weeklydiary.othertime;
      } else {
        return this.$store.state.monthlydiary.othertime;
      }
    },
  },
  watch: {
    monthly() {
      this.isWeekly = false;
    },
    weekly() {
      this.isWeekly = true;
    },
  },

  methods: {},
};
</script>

<style scoped type="text/css">
.box {
  display: flex;
  align-items: center;
  justify-content: center;
}
.calendarM {
  margin: 7%;
}

.monthHeader {
  font-size: 5vw;
  padding-bottom: 4vh;
  padding-top: 3vh;
  font-weight: bold;
}

.monthlyDays {
  font-size: 2.5vw;
  text-align: center;
  font-family: "nanumsquare";
}

.weekBolder {
  font-weight: bolder;
}

.mainColor {
  color: #48d9df;
}

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
.effect {
  transition: opacity 0.5s;
}
</style>

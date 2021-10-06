<template>
  <DiaryNav />
  <div style="width:100%; margin-bottom: 50px;">
    <div class="calendarM flex justify-content-center">
      <div class="study-diary-weekly screen" data-id="26:315">
        <h1 class="weekHeader" style="margin-top: -20px">
          <span v-on:click="onClickPrev(currentDay)" class="mainColor">◀</span>
          {{ DayAndWeek }}
          <span v-on:click="onClickNext(currentDay)" class="mainColor">▶</span>
        </h1>

        <div
            v-if="this.$store.state.weeklydiary.studyInfo[0] ===-1"
            class="notstudy"
        >
          <img src="../../assets/images/weekly_null.png" style="width: 100%; margin-top: 10px">
        </div>
        <Bar style="margin-top: -30px" v-else></Bar>
      </div>
    </div>
  </div>

  <BottomMenu />
</template>

<script>
import DiaryNav from "@/components/DiaryNav";
import BottomMenu from "@/components/BottomMenu";
import Bar from '../../components/diary/bar'
export default {

  components: {
    BottomMenu,
    DiaryNav,
    Bar,
  },
  data() {
    return {
      currentYear: new Date().getFullYear(),
      currentMonth: new Date().getMonth() + 1,
      currentDay: new Date().getDate(),
    };
  },
  mounted() {
    this.getWeeklyInfo();
  },
  computed: {
    DayAndWeek: function() {
      return this.$store.state.weeklydiary.DayAndWeek;
    },
  },
  methods: {
    init: function() {
      console.log("init");
    },
    getEndOfDay: function(year, month) {
      switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
          return 31;
        case 4:
        case 6:
        case 9:
        case 11:
          return 30;
        case 2:
          if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return 29;
          } else {
            return 28;
          }
        default:
          console.log("unknown month " + month);
          return 0;
      }
    },
    onClickPrev: function(day) {
      let endDay = this.getEndOfDay(this.currentYear, this.currentMonth);
      if (day <= 7) {
        this.currentDay = endDay - (7 - day);
        this.currentMonth -= 1;
      } else {
        this.currentDay -= 7;
      }
      if (this.currentMonth < 1) {
        this.currentMonth = 12;
        this.currentYear -= 1;
      }
      this.getWeeklyInfo();
      this.init();
    },
    onClickNext: function(day) {
      let endDay = this.getEndOfDay(this.currentYear, this.currentMonth);
      if (day + 7 > endDay) {
        this.currentDay = 7 - (endDay - day);
        this.currentMonth += 1;
      } else {
        this.currentDay += 7;
      }
      if (this.currentMonth > 12) {
        this.currentMonth = 1;
        this.currentYear += 1;
      }
      this.getWeeklyInfo();
      this.init();
    },
    getWeeklyInfo() {
      console.log("getWeeklyInfo - component");
      let date =
        this.currentYear + "-" + this.currentMonth + "-" + this.currentDay;
      this.$store.dispatch("weeklydiary/getWeekly", {
        day: date,
        token: this.$store.state.login.userToken,
      });
    },
  },
};
</script>

<style scoped>
.calendarM {
  margin: 7%;
}
.weekHeader {
  font-size: 5vw;
  padding-bottom: 4vh;
  padding-top: 3vh;
  font-weight: bold;
  font-family: "nanumsquare";
  text-align: center;
}
.mainColor {
  color: #48d9df;
}
</style>

<template>
  <div class="container-center-horizontal mt-3 calendarM" style="width=100%">
    <div class="calendar flex justify-content-center" style="text-align:center">
      <h1 class="monthHeader">
        <!-- <div v-for="(days, index) in studyDay" v-bind:key="index">
          <span>{{ days }}</span>
        </div> -->

        <span v-on:click="onClickPrev(currentMonth)" class="mainColor">◀</span>
        {{ currentYear }}년 {{ currentMonth }}월
        <span v-on:click="onClickNext(currentMonth)" class="mainColor">▶</span>
      </h1>
      <table class="table table-hover pb-3">
        <thead>
          <tr>
            <td v-for="(weekName, index) in weekNames" v-bind:key="index">
              {{ weekName }}
            </td>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, index) in currentCalendarMatrix" :key="index">
            <td
              v-for="(day, index2) in row"
              :key="index2"
              style="padding:2rem;"
            >
              <span v-if="isStudy(day) && day != ''" class="rounded">
                {{ day }}
              </span>
              <span v-else>
                {{ day }}
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: "Calendar",
  data() {
    return {
      // calendar 데이터
      weekNames: ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"],
      rootYear: 1904,
      rootDayOfWeekIndex: 4, // 2000년 1월 1일은 토요일
      currentYear: new Date().getFullYear(),
      currentMonth: new Date().getMonth() + 1,
      currentDay: new Date().getDate(),
      currentMonthStartWeekIndex: null,
      currentCalendarMatrix: [],
      endOfDay: null,
      memoDatas: [],

      // study 데이터
      studyDay: [],
    };
  },
  mounted() {
    this.init();
    this.getMonthlyInfo();
  },
  methods: {
    init: function() {
      this.currentMonthStartWeekIndex =
        this.getStartWeek(this.currentYear, this.currentMonth) + 1;
      this.endOfDay = this.getEndOfDay(this.currentYear, this.currentMonth);
      this.initCalendar();
    },
    initCalendar: function() {
      this.currentCalendarMatrix = [];
      let day = 1;
      for (let i = 0; i < 6; i++) {
        let calendarRow = [];
        for (let j = 0; j < 7; j++) {
          if (i == 0 && j < this.currentMonthStartWeekIndex) {
            calendarRow.push("");
          } else if (day <= this.endOfDay) {
            calendarRow.push(day);
            day++;
          } else {
            calendarRow.push("");
          }
        }
        this.currentCalendarMatrix.push(calendarRow);
      }
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
    getStartWeek: function(targetYear, targetMonth) {
      let year = this.rootYear;
      let month = 1;
      let sumOfDay = this.rootDayOfWeekIndex;
      // eslint-disable-next-line no-constant-condition
      while (true) {
        if (targetYear > year) {
          for (let i = 0; i < 12; i++) {
            sumOfDay += this.getEndOfDay(year, month + i);
          }
          year++;
        } else if (targetYear == year) {
          if (targetMonth > month) {
            sumOfDay += this.getEndOfDay(year, month);
            month++;
          } else if (targetMonth == month) {
            return sumOfDay % 7;
          }
        }
      }
    },
    onClickPrev: function(month) {
      month--;
      if (month <= 0) {
        this.currentMonth = 12;
        this.currentYear -= 1;
      } else {
        this.currentMonth -= 1;
      }
      this.init();
      this.getMonthlyInfo();
      console.log(this.$store.state.diary.isStudy);
    },
    onClickNext: function(month) {
      month++;
      if (month > 12) {
        this.currentMonth = 1;
        this.currentYear += 1;
      } else {
        this.currentMonth += 1;
      }
      this.init();
      this.getMonthlyInfo();
      console.log(this.$store.state.diary.isStudy);
    },
    isToday: function(year, month, day) {
      let date = new Date();
      return (
        year == date.getFullYear() &&
        month == date.getMonth() + 1 &&
        day == date.getDate()
      );
    },
    isStudy: function(day) {
      return this.$store.state.diary.isStudy[day - 1] != 0;
    },
    getMonthlyInfo() {
      console.log("getMonthlyInfo - component");
      let date =
        this.currentYear + "-" + this.currentMonth + "-" + this.currentDay;
      this.$store.dispatch("diary/getMonthly", {
        day: date,
        token: this.$store.state.login.userToken,
      });
    },
  },
};
</script>

<style type="text/css">
.rounded {
  -moz-border-radius: 20px 20px 20px 20px;
  border-radius: 20px 20px 20px 20px;
  background-color: #48d9df;
  padding: 10px;
  color: #ffffff;
}

.calendarM {
  padding: 5%;
}

.monthHeader {
  font-size: 2rem;
  padding-bottom: 3rem;
}

.mainColor {
  color: #48d9df;
}
</style>

<template>
  <div class="container-center-horizontal">
    <div class="study-diary-daily screen" data-id="61:3" :style="{ width: this.$store.state.window_width + 'px', height: this.$store.state.window_height + 'px' }">
      <div class="flex-row" data-id="an|0GvgCTye">
        <div class="daily" data-id="61:28">
          <div class="daily-1 nanumbarungothic-bold-bondi-blue-18px" data-id="61:30">일간</div>
          <img
              class="lyline"
              data-id="61:29"
              src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/daily-line@2x.svg"
          />
        </div>
        <div class="weekly" data-id="61:25">
          <div class="weekly-1 nanumbarungothic-ultra-light-black-18px" data-id="61:27">주간</div>
          <img
              class="lyline"
              data-id="61:26"
              src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/monthly-line@2x.svg"
          />
        </div>
        <div class="monthly" data-id="61:22">
          <div class="monthly-1 nanumbarungothic-ultra-light-black-18px" data-id="61:24">월간</div>
          <img
              class="lyline"
              data-id="61:23"
              src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/monthly-line@2x.svg"
          />
        </div>
      </div>

      <datepicker-lite
          :value-attr="currentDateTime()"
          :placeholder-attr="dplaceholder"
          :is-button-type="disButtonType"
          :year-minus="dyearMinus"
          :years-range="100"
          :to="dtoDate"
          :locale="dlocale"
          :disableInput="false"
          :show-bottom-button="false"
          style="margin-top: 20px"
          @value-changed="changeEvent"
      ></datepicker-lite>

      <div>
        <span v-on:click="onClickPrev(currentMonth)" class="mainColor">◀</span>
        <Donut style="margin-left: 10px"></Donut>
        <span v-on:click="onClickNext(currentMonth)" class="mainColor">▶</span>
      </div>

      <div class="navibar" data-id="61:31">
        <div class="overlap-group nanumbarungothic-regular-normal-black-15px" data-id="an|cuSnhCeW">
          <img
              class="x3"
              data-id="61:35"
              src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/--3@2x.png"
          />
          <div class="x2" data-id="61:36">기록</div>
          <div class="x4" data-id="61:34">스터디</div>
          <div class="x5" data-id="61:33">마이페이지</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import DatepickerLite from 'vue3-datepicker-lite'
import Donut from '../components/diary/donut'
export default {
  components : { Donut, DatepickerLite },
    data() {
      return {
        dclass: "myDateInput",
        dplaceholder: "Select",
        disButtonType: true,
        dyearMinus: 0,
        dtoDate: "2030/12/10",
        dlocale: {
          format: "YYYY/MM/DD",
          weekday: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
          months: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"],
          startsWeeks: 0
        },
        changeEvent: (value) => {
          console.log(value + " selected!");
        },
      }
    },
  methods: {
    currentDateTime() {
      const current = new Date();
      let nmonth = '';
      var month = current.getMonth()+1;
      if(month<10) {nmonth = `0${current.getMonth()+1}`;}
      else nmonth = `${current.getMonth()+1}`;
      const date = `${current.getFullYear()}/${nmonth}/${current.getDate()}`;
      return date;
    }
  }
  }

</script>

<style>
.study-diary-daily {
  align-items: center;
  background-color: White;
  display: flex;
  flex-direction: column;
  height: 812px;
  overflow: hidden;
  padding: 33px 0;
  width: 375px;
}

.study-diary-daily .flex-row {
  align-items: flex-start;
  display: flex;
  height: 26px;
  margin-left: 3.0px;
  min-width: 298px;
}

.study-diary-daily .daily {
  align-items: center;
  display: flex;
  flex-direction: column;
  min-height: 26px;
  width: 47px;
}

.study-diary-daily .daily-1 {
  letter-spacing: 0;
  min-height: 21px;
  min-width: 35px;
  text-align: center;
}

.study-diary-daily .lyline {
  height: 2px;
  margin-right: 2.0px;
  margin-top: 4px;
  width: 45px;
}

.study-diary-daily .weekly {
  align-items: center;
  display: flex;
  flex-direction: column;
  margin-left: 78px;
  min-height: 26px;
  width: 47px;
}

.study-diary-daily .weekly-1 {
  letter-spacing: 0;
  min-height: 21px;
  min-width: 35px;
  text-align: center;
}

.study-diary-daily .monthly {
  align-items: center;
  display: flex;
  flex-direction: column;
  margin-left: 79px;
  min-height: 26px;
  width: 47px;
}

.study-diary-daily .monthly-1 {
  letter-spacing: 0;
  min-height: 21px;
  min-width: 35px;
  text-align: center;
}

.study-diary-daily .today {
  align-items: center;
  display: flex;
  height: 38px;
  margin-left: 2.0px;
  margin-top: 36px;
  min-width: 159px;
}

.study-diary-daily .prev {
  height: 18px;
  width: 18px;
}

.study-diary-daily .todaydate {
  color: black;
  font-family: "NanumBarunGothic-Light";
  font-size: 20px;
  font-weight: 300;
  letter-spacing: 0;
  margin-left: 17px;
  min-height: 38px;
  min-width: 89px;
  text-align: center;
}

.study-diary-daily .next {
  height: 18px;
  margin-left: 15px;
  width: 18px;
}

.study-diary-daily .overlap-group1 {
  align-items: center;
  background-image: url(https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/study-percent@2x.svg);
  background-size: 100% 100%;
  display: flex;
  flex-direction: column;
  margin-top: 39px;
  min-height: 220px;
  padding: 55.7px 46px;
  width: 219px;
}

.study-diary-daily .percentage {
  letter-spacing: 0;
  margin-left: 9.0px;
  margin-top: 4px;
  min-height: 34px;
  width: 64px;
}

.study-diary-daily .overlap-group2 {
  align-self: flex-end;
  height: 27px;
  margin-top: 17px;
  position: relative;
  width: 112px;
}

.study-diary-daily .totalstudytime {
  left: 0;
  letter-spacing: 0;
  position: absolute;
  top: 0;
  width: 112px;
}

.study-diary-daily .line-4 {
  height: 2px;
  left: 4px;
  position: absolute;
  top: 25px;
  width: 90px;
}

.study-diary-daily .totalstudytime-1 {
  letter-spacing: 0;
  margin-left: 6.0px;
  margin-top: 7px;
  min-height: 20px;
  text-align: center;
  width: 103px;
}

.study-diary-daily .to-do {
  align-self: flex-start;
  height: 76px;
  margin-left: 36px;
  margin-top: 54px;
  position: relative;
  width: 206px;
  opacity: 0;
  transform: translate(0, 25px);
}

.study-diary-daily .to-do.animate-enter {
  animation: animate-enter-frames 0.40s ease-in-out 0.40s 1 normal forwards;
  opacity: 0;
  transform: translate(0, 25px);
}

@keyframes animate-enter-frames {
  from{opacity: 0;
    transform: translate(0, 25px);
  }
  to{opacity: 1;
    transform: translate(0,0);
  }
}

.study-diary-daily .to-do-list {
  color: black;
  font-family: "NanumBarunGothic-Light";
  font-size: 20px;
  left: 0;
  letter-spacing: 0;
  position: absolute;
  top: 0;
}

.study-diary-daily .check-icon {
  height: 17px;
  left: 149px;
  position: absolute;
  top: 3px;
  width: 22px;
}

.study-diary-daily .to-do-d {
  left: 11px;
  letter-spacing: 0;
  position: absolute;
  top: 55px;
}

.study-diary-daily .navibar {
  align-items: flex-start;
  display: flex;
  margin-left: 6.0px;
  margin-top: 240px;
  min-width: 381px;
}

.study-diary-daily .overlap-group {
  align-items: center;
  background-color: White;
  display: flex;
  height: 50px;
  justify-content: flex-end;
  min-width: 375px;
  padding: 0 25px;
}

.study-diary-daily .x3 {
  align-self: flex-start;
  height: 48px;
  object-fit: cover;
  width: 48px;
}

.study-diary-daily .x2 {
  letter-spacing: 0;
  margin-bottom: 0.98px;
  margin-left: 44px;
  min-height: 17px;
  min-width: 29px;
}

.study-diary-daily .x4 {
  letter-spacing: 0;
  margin-bottom: 0.98px;
  margin-left: 50px;
  min-height: 17px;
  min-width: 43px;
}

.study-diary-daily .x5 {
  letter-spacing: 0;
  margin-bottom: 0.98px;
  margin-left: 37px;
  min-height: 17px;
  min-width: 69px;
}

@import url("https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css");

@font-face {
  font-family: "NanumBarunGothic-Regular";
  font-style: normal;
  font-weight: 400;
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot');
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot?#iefix') format('embedded-opentype'), url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.woff') format('woff'), url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.ttf') format('truetype');
}

@font-face {
  font-family: "NanumBarunGothic-Bold";
  font-style: normal;
  font-weight: 700;
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.eot');
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.eot?#iefix') format('embedded-opentype'), url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.woff') format('woff'), url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.ttf') format('truetype')
}

@font-face {
  font-family: 'NanumBarunGothic-Light';
  font-style: normal;
  font-weight: 300;
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebLight.eot');
  src: url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebLight.eot?#iefix') format('embedded-opentype'), url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebLight.woff') format('woff'), url('//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebLight.ttf') format('truetype');
}
.screen a {
  display: contents;
  text-decoration: none;
}

.container-center-horizontal {
  display: flex;
  flex-direction: row;
  justify-content: center;
  pointer-events: none;
  width: 100%;
}

.container-center-horizontal > * {
  flex-shrink: 0;
  pointer-events: auto;
}

* {
  box-sizing: border-box;
}
</style>
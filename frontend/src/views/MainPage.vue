<template>
  <div>
    <BottomMenu />
    <div class="main" style="backgroud-color:#FF0000;">
      <!-- quote start -->
      <div class="quote cafeFont d-flex justify-content-centers">
        <h1>“</h1>
        <span
          v-if="
            this.$store.state.mainPage.mainData.quote != null &&
              quoteLen(this.$store.state.mainPage.mainData.quote) == 1
          "
          class="highlightQuote"
          style="font-size:6vw"
        >
          {{ this.$store.state.mainPage.mainData.quote }}
        </span>
        <span
          v-else-if="
            this.$store.state.mainPage.mainData.quote != null &&
              quoteLen(this.$store.state.mainPage.mainData.quote) == 2
          "
          class="highlightQuote"
          style="font-size:4.5vw"
        >
          {{ this.$store.state.mainPage.mainData.quote }}
        </span>
        <span v-else class="highlightQuote" style="font-size:4vw">
          {{ this.$store.state.mainPage.mainData.quote }}
        </span>
        <span>”</span>
      </div>
      <!-- quote end-->

      <div
        class="study-time pt-3"
        :style="{ 'min-height': 0.8 * this.$store.state.window_width + 'px' }"
      >
        <div class="time-area" style="margin:auto;">
          <div
            class="todaydate animate-enter1"
            :style="{
              'font-size': 0.06 * this.$store.state.window_width + 'px',
              'margin-top': 0.02 * this.$store.state.window_width + 'px',
            }"
          >
            {{ this.todayText }}
          </div>
          <h1
            class="focusstudytime fontHyemin animate-enter2"
            :style="{
              'font-size': 0.11 * this.$store.state.window_width + 'px',
              'margin-top': 0.03 * this.$store.state.window_width + 'px',
            }"
          >
            {{ this.$store.state.mainPage.mainData.focustime }}
          </h1>
          <img
            class="line-1 animate-enter4"
            src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/line-1@2x.svg"
          />
          <div
            class="totalstudytime fontHyemin animate-enter3"
            :style="{
              'font-size': 0.06 * this.$store.state.window_width + 'px',
              'margin-top': 0.05 * this.$store.state.window_width + 'px',
            }"
          >
            {{ this.$store.state.mainPage.mainData.alltime }}
          </div>
        </div>
      </div>
      <br />
      <div class="todolist">
        <MainPageToDo />
      </div>
    </div>
  </div>
  <BottomMenu />
</template>

<script>
import BottomMenu from "@/components/BottomMenu.vue";
import MainPageToDo from "@/components/MainPage/MainPageToDo.vue";

export default {
  name: "MainPage",
  data: function() {
    return {
      today: null,
      todayYear: null,
      todayMonth: null,
      todayDate: null,
      todayText: null,
    };
  },
  components: {
    BottomMenu,
    MainPageToDo,
  },
  created() {
    this.$store.dispatch("mainPage/getJWT", this.$store.state.login.userToken);
    this.$store.dispatch("mainPage/getMainData");
    this.today = new Date();
    this.todayYear = this.today.getFullYear();
    this.todayMonth = ("0" + (this.today.getMonth() + 1)).slice(-2);
    this.todayDate = ("0" + this.today.getDate()).slice(-2);
    this.todayText =
      this.todayYear + "." + this.todayMonth + "." + this.todayDate;
  },
  methods: {
    quoteLen() {
      console.log(
        Object.keys(this.$store.state.mainPage.mainData.quote).length
      );
      if (Object.keys(this.$store.state.mainPage.mainData.quote).length < 25) {
        return 1;
      } else if (
        Object.keys(this.$store.state.mainPage.mainData.quote).length < 36
      ) {
        return 2;
      } else {
        return 3;
      }
    },
  },
};
</script>

<style scoped>
.main {
  padding: 16px;
  margin-bottom: 30px;
  align-items: center;
}

/* blob */
.study-time {
  font-family: "IM_Hyemin-Regular";
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url(https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/blob1@2x.svg);
  background-size: contain;
  background-repeat: no-repeat;
  width: 100%;
  padding-top: 3vh;
}

.time-area {
  width: 100%;
  height: 100%;
  text-align: center;
  padding-bottom: 3vh;
}

.back-img {
  max-width: 80%;
}

.playbtn {
  margin: auto;
  opacity: 0;
  transform: translate(0, 25px);
}

.playbtn.animate-enter {
  animation: animate-enter-frames 0.4s ease-in-out 0s 1 normal forwards;
  display: block;
  opacity: 0;
  transform: translate(0, 25px);
}

@keyframes animate-enter-frames {
  from {
    opacity: 0;
    transform: translate(0, 25px);
  }
  to {
    opacity: 1;
    transform: translate(0, 0);
  }
}

.todaydate {
  color: var(--black);
  /* font-family: "nanumsquare"; */

  font-family: "IM_Hyemin-Regular";
  font-weight: 300;
  letter-spacing: 0;
  margin-right: 1.95px;
  min-height: 25px;
  min-width: 118px;
  opacity: 0;
  transform: translate(0, 25px);
}

.fontHyemin {
  font-family: "IM_Hyemin-Regular";
}

.todaydate.animate-enter1 {
  animation: animate-enter1-frames 0.4s ease-in-out 0s 1 normal forwards;
  opacity: 0;
  transform: translate(0, 25px);
}

@keyframes animate-enter1-frames {
  from {
    opacity: 0;
    transform: translate(0, 25px);
  }
  to {
    opacity: 1;
    transform: translate(0, 0);
  }
}

.focusstudytime {
  align-self: flex-end;
  color: var(--black);
  font-weight: 300;
  letter-spacing: 0;
  min-height: 46px;
  min-width: 202px;
  text-align: center;
  opacity: 0;
  transform: translate(0, 25px);
}

.focusstudytime.animate-enter2 {
  animation: animate-enter2-frames 0.4s ease-in-out 0.3s 1 normal forwards;
  opacity: 0;
  transform: translate(0, 25px);
}

@keyframes animate-enter2-frames {
  from {
    opacity: 0;
    transform: translate(0, 25px);
  }
  to {
    opacity: 1;
    transform: translate(0, 0);
  }
}

.line-1 {
  display: block;
  height: 1px;
  margin: auto;
  width: 180px;
  opacity: 0;
  transform: translate(0, 25px);
}

.line-1.animate-enter4 {
  animation: animate-enter4-frames 0.4s ease-in-out 0.3s 1 normal forwards;
  display: block;
  opacity: 0;
  transform: translate(0, 25px);
}

@keyframes animate-enter4-frames {
  from {
    opacity: 0;
    transform: translate(0, 25px);
  }
  to {
    opacity: 1;
    transform: translate(0, 0);
  }
}

.totalstudytime {
  color: var(--black);
  font-weight: 300;
  letter-spacing: 0;
  min-height: 29px;
  min-width: 105px;
  text-align: center;
  opacity: 0;
}

.totalstudytime.animate-enter3 {
  animation: animate-enter3-frames 0.4s ease-in-out 0.3s 1 normal forwards;
  opacity: 0;
}

@keyframes animate-enter3-frames {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

/* // */

.demo {
  font-family: sans-serif;
  border: 1px solid #eee;
  border-radius: 2px;
  padding: 20px 30px;
  margin-top: 1em;
  margin-bottom: 40px;
  user-select: none;
  overflow-x: auto;
}

.tab-button {
  padding: 6px 10px;
  border-top-left-radius: 3px;
  border-top-right-radius: 3px;
  border: 1px solid #ccc;
  cursor: pointer;
  background: #f0f0f0;
  margin-bottom: -1px;
  margin-right: -1px;
}
.tab-button:hover {
  background: #e0e0e0;
}
.tab-button.active {
  background: #e0e0e0;
}
.demo-tab {
  border: 1px solid #ccc;
  padding: 10px;
}

/* 세령 추가 */
.quote {
  font-size: 6vw;
  font-weight: 700;
  font-style: italic;
  justify-content: center;

  padding-top: 3vh;
  padding-bottom: 3vh;
}

.highlightQuote {
  background: linear-gradient(to top, #a4f9fc 40%, transparent 30%);
}
</style>

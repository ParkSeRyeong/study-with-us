<template>
  <div>
    <TitleBar v-if="loading || !aiPage">&nbsp;&nbsp;SELF STUDY</TitleBar>

    <div>
      <!-- notice page start -->
      <div class="wrap studyBackImage displayCenter">
        <div
          id="init"
          v-if="!loading && !aiPage"
          @click="clickStart()"
          class="pt-5"
        >
          <div class="">
            <img class="noticeIcon" src="@/assets/images/idea.png" />
            <p class="noticeMSG">공부 시작 전</p>
            <p class="noticeMSG">손과 얼굴이 나오도록</p>
            <p class="noticeMSG">각도를 조정해주세요!</p>
          </div>

          <div style="font-size:5vw; padding-top:15%">
            준비가 되었다면 아무곳이나 터치 해주세요! :D
          </div>
        </div>

        <div id="loading" v-else-if="loading" class="displayCenter">
          <div class="displayCenter">
            <p class="noticeMSG" style="padding-top:30%;">공부를 시작합니다!</p>
            <p
              class="noticeMSG"
              style="padding-bottom:30%;"
              v-show="delayLoadingEffect()"
            >
              카메라 준비중...
            </p>
            <div v-show="delayLoadingTime">
              <loader
                object="#D2F3F5"
                color1="#48D9DF"
                color2="#39B6BB"
                size="5"
                speed="2"
                bg="#343a40"
                objectbg="#adadad"
                opacity="0"
                name="circular"
              ></loader>
            </div>
            <div style="padding-bottom:61vh;"></div>
          </div>
        </div>
      </div>
      <!-- notice page end -->
      <div v-if="aiPage">
        <TitleBar>&nbsp;&nbsp;NOW STUDYING</TitleBar>

        <div v-if="webcamLoad">
          <p class="top highlighting">
            집중 시간 : {{ formattedFocusElapsedTime }}
          </p>
          <br />
          <p class="bottom">
            <img class="pencil2" src="@/assets/images/zzz.png" />&nbsp;{{
              formattedSleepElapsedTime
            }}&nbsp;<img
              class="pencil2"
              src="@/assets/images/zzz.png"
            />&nbsp;&nbsp;
            <img class="pencil2" src="@/assets/images/폰.png" />&nbsp;{{
              formattedPhoneElapsedTime
            }}&nbsp;<img class="pencil2" src="@/assets/images/폰.png" />
          </p>
        </div>

        <div id="webcam-container"></div>
        <br />
        <div v-if="webcamLoad">
          <!-- pause / stop button -->
          <div>
            <router-link @click="stop" to="/main"
              ><img class="iconBtn" src="@/assets/images/stop.png"
            /></router-link>
            <div v-if="toggle" @click="start">
              <img class="iconBtn" src="@/assets/images/play.png" />
            </div>
            <div v-else @click="pause">
              <img class="iconBtn" src="@/assets/images/pause.png" />
            </div>
          </div>
          <!-- pause / stop button end -->

          <div id="stopwatch">
            <div style="text-align:center; font-size: 13vw;">
              <h3 id="studying" style="color:green" class="study-msg">
                공부 중...
              </h3>
              <h3 id="phone" style="color:red" class="study-msg blink">
                &#128245; 휴대폰 그만! &#128245;
              </h3>
              <h3 id="snoozing" style="color:red" class="blink study-msg">
                일어납시다!
              </h3>
              <h3 id="pauseCm" class="blink study-msg">일시정지</h3>
            </div>
          </div>
        </div>
        <br /><br /><br /><br /><br /><br /><br /><br />
        <div id="label-container"></div>
      </div>
    </div>
  </div>
</template>

<script>
// the link to your model provided by Teachable Machine export panel
import * as tf from "@tensorflow/tfjs"; // eslint-disable-line no-unused-vars
import * as tmImage from "@teachablemachine/image";
import TitleBar from "@/components/TitleBar";
import axios from "axios";
import SERVER from "@/api/api";

let model, webcam, labelContainer, maxPredictions;

export default {
  el: "#ai",
  components: {
    TitleBar,
  },

  data() {
    return {
      delayLoadingTime: false,
      loading: false,
      aiPage: false,
      webcamLoad: false,
      behavior: ["집중", "핸드폰", "졸기"],
      allElapseTime: 0,
      focusElapsedTime: 0,
      sleepElapsedTime: 0,
      phoneElapsedTime: 0,
      focusTimer: undefined,
      sleepTimer: undefined,
      phoneTimer: undefined,
      focusCheck: false,
      sleepCheck: false,
      phoneCheck: false,
      toggle: false,
      pauseToggle: false,
      formattedAllElapsedTimeRgF: "",
      formattedFocusElapsedTimeRgF: "",
      formattedSleepElapsedTimeRgF: "",
      formattedPhoneElapsedTimeRgF: "",
    };
  },

  computed: {
    formattedFocusElapsedTime() {
      const date = new Date(null);
      date.setSeconds(this.focusElapsedTime / 1000);
      const utc = date.toUTCString();
      return utc.substr(utc.indexOf(":") - 2, 8);
    },
    formattedSleepElapsedTime() {
      const date = new Date(null);
      date.setSeconds(this.sleepElapsedTime / 1000);
      const utc = date.toUTCString();
      return utc.substr(utc.indexOf(":") - 2, 8);
    },
    formattedPhoneElapsedTime() {
      const date = new Date(null);
      date.setSeconds(this.phoneElapsedTime / 1000);
      const utc = date.toUTCString();
      return utc.substr(utc.indexOf(":") - 2, 8);
    },
  },

  methods: {
    clickStart() {
      this.init();
    },
    sleep(ms) {
      return new Promise((resolve) => {
        setTimeout(resolve, ms);
      });
    },
    async delayLoadingEffect() {
      await this.sleep(1000);
      this.delayLoadingTime = true;
      return true;
    },

    async init() {
      this.loading = true;
      await this.sleep(1000);
      const URL = "https://teachablemachine.withgoogle.com/models/uK53pgina/";
      const modelURL = URL + "model.json";
      const metadataURL = URL + "metadata.json";
      const flip = true; // whether to flip the webcam

      // load the model and metadata
      // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
      // or files from your local hard drive
      // Note: the pose library adds "tmImage" object to your window (window.tmImage)

      model = await tmImage.load(modelURL, metadataURL);
      maxPredictions = model.getTotalClasses();

      this.aiPage = true;
      // Convenience function to setup a webcam
      webcam = new tmImage.Webcam(window.outerWidth, 400, flip); // width, height, flip
      await webcam.setup(); // request access to the webcam
      await webcam.play();
      window.requestAnimationFrame(this.loop);

      // append elements to the DOM
      document.getElementById("webcam-container").appendChild(webcam.canvas);
      labelContainer = document.getElementById("label-container");
      for (let i = 0; i < maxPredictions; i++) {
        // and class labels
        labelContainer.appendChild(document.createElement("div"));
      }
      this.loading = false;
      this.webcamLoad = true;
    },

    async loop() {
      webcam.update(); // update the webcam frame
      await this.predict();
      window.requestAnimationFrame(this.loop);
    },

    // run the webcam image through the image model
    async predict() {
      // predict can take in an image, video or canvas html element
      const prediction = await model.predict(webcam.canvas);
      //console.log(prediction);
      for (let i = 0; i < maxPredictions; i++) {
        const classPrediction =
          prediction[i].className + ": " + prediction[i].probability.toFixed(2);
        labelContainer.childNodes[i].innerHTML = classPrediction;

        if (
          (prediction[i].probability.toFixed(2) >= 0.9 && !this.toggle) ||
          this.pauseToggle
        ) {
          if (prediction[i].probability.toFixed(2) >= 0.9) {
            this.pauseToggle = false;
          }
          if (prediction[i].className === this.behavior[0]) {
            document.getElementById("studying").style.display = "inline";
            document.getElementById("phone").style.display = "none";
            document.getElementById("snoozing").style.display = "none";
            document.getElementById("pauseCm").style.display = "none";
            document
              .getElementById("webcam-container")
              .classList.remove("blink");
            if (!this.focusCheck) {
              this.startFocus();
              this.pauseSleep();
              this.pausePhone();
              this.focusCheck = true;
              this.sleepCheck = false;
              this.phoneCheck = false;
            }
          } else if (prediction[i].className === this.behavior[1]) {
            document.getElementById("studying").style.display = "none";
            document.getElementById("phone").style.display = "inline";
            document.getElementById("snoozing").style.display = "none";
            document.getElementById("pauseCm").style.display = "none";
            document.getElementById("webcam-container").classList.add("blink");
            if (!this.phoneCheck) {
              this.startPhone();
              this.pauseFocus();
              this.pauseSleep();
              this.focusCheck = false;
              this.sleepCheck = false;
              this.phoneCheck = true;
            }
          } else {
            document.getElementById("studying").style.display = "none";
            document.getElementById("phone").style.display = "none";
            document.getElementById("snoozing").style.display = "inline";
            document.getElementById("pauseCm").style.display = "none";
            document.getElementById("webcam-container").classList.add("blink");
            if (!this.sleepCheck) {
              this.startSleep();
              this.pauseFocus();
              this.pausePhone();
              this.focusCheck = false;
              this.sleepCheck = true;
              this.phoneCheck = false;
            }
          }
        }
      }
    },
    formattedAllElapsedTimeRg() {
      const date = new Date(null);
      date.setSeconds(
        (this.focusElapsedTime +
          this.sleepElapsedTime +
          this.phoneElapsedTime) /
          1000
      );
      const utc = date.toUTCString();
      this.formattedAllElapsedTimeRgF = utc.substr(utc.indexOf(":") - 2, 8);
    },
    formattedFocusElapsedTimeRg() {
      const date = new Date(null);
      date.setSeconds(this.focusElapsedTime / 1000);
      const utc = date.toUTCString();
      this.formattedFocusElapsedTimeRgF = utc.substr(utc.indexOf(":") - 2, 8);
    },
    formattedSleepElapsedTimeRg() {
      const date = new Date(null);
      date.setSeconds(this.sleepElapsedTime / 1000);
      const utc = date.toUTCString();
      this.formattedSleepElapsedTimeRgF = utc.substr(utc.indexOf(":") - 2, 8);
    },
    formattedPhoneElapsedTimeRg() {
      const date = new Date(null);
      date.setSeconds(this.phoneElapsedTime / 1000);
      const utc = date.toUTCString();
      this.formattedPhoneElapsedTimeRgF = utc.substr(utc.indexOf(":") - 2, 8);
    },
    startFocus() {
      this.focusTimer = setInterval(() => {
        this.focusElapsedTime += 1000;
      }, 1000);
    },
    pauseFocus() {
      clearInterval(this.focusTimer);
    },
    startSleep() {
      this.sleepTimer = setInterval(() => {
        this.sleepElapsedTime += 1000;
      }, 1000);
    },
    pauseSleep() {
      clearInterval(this.sleepTimer);
    },
    startPhone() {
      this.phoneTimer = setInterval(() => {
        this.phoneElapsedTime += 1000;
      }, 1000);
    },
    pausePhone() {
      clearInterval(this.phoneTimer);
    },
    start() {
      this.toggle = false;
      this.pauseToggle = true;
    },
    pause() {
      this.toggle = true;
      clearInterval(this.focusTimer);
      clearInterval(this.sleepTimer);
      clearInterval(this.phoneTimer);
      document.getElementById("studying").style.display = "none";
      document.getElementById("phone").style.display = "none";
      document.getElementById("snoozing").style.display = "none";
      document.getElementById("pauseCm").style.display = "inline";
      document.getElementById("webcam-container").classList.remove("blink");
    },
    stop() {
      this.formattedAllElapsedTimeRg();
      this.formattedFocusElapsedTimeRg();
      this.formattedSleepElapsedTimeRg();
      this.formattedPhoneElapsedTimeRg();
      axios({
        method: "post",
        url: `${SERVER.URL}/study/stop`,
        headers: {
          "Access-Control-Allow-Origin": "*",
          Authorization: this.$store.state.login.userToken,
        },
        data: {
          alltime: this.formattedAllElapsedTimeRgF,
          focustime: this.formattedFocusElapsedTimeRgF,
          sleeptime: this.formattedSleepElapsedTimeRgF,
          phonetime: this.formattedPhoneElapsedTimeRgF,
          screen: 0,
          sound: 0,
          sentence: 1,
        },
      }).then((res) => {
        console.log(res.data.message);
      });
    },
  },
};

// More API functions here:
// https://github.com/googlecreativelab/teachablemachine-community/tree/master/libraries/image

// Load the image model and setup the webcam
</script>

<style scoped>
.noticeStudy {
  display: flex;
  justify-items: center;
  align-items: center;
}
.noticeIcon {
  padding-top: 30%;
  padding-bottom: 3vh;
  height: 25vh;
  width: auto;
}

.studyBackImage {
  background-image: url("../assets/images/dark_table.png");
  background-size: cover;
  background-repeat: no-repeat;
  height: 100%;
}
.displayCenter {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.noticeMSG {
  padding-bottom: 3vh;
}
.hyemin {
  font-family: "IM_Hyemin-Regular";
  font-weight: 600;
  font-size: 4vw;
}
/*------------------ */
.wrap * {
  display: flex;
  flex-direction: column;
  align-items: center;
  vertical-align: center;
  color: white;
  font-family: "IM_Hyemin-Regular";
  font-size: 7vw;
}

#init {
  height: 90vh;
}

.blink {
  animation: blink 1s linear infinite;
}
@keyframes blink {
  0% {
    opacity: 1;
  }
  50% {
    opacity: 0.5;
  }
  100% {
    opacity: 1;
  }
}

p {
  font-family: "NanumBarunGothic-Light";
}

.top {
  text-align: center;
  font-size: 9vw;
  font-weight: 700;
  font-family: "IM_Hyemin-Regular";
  margin-top: 4vh;
}
.highlighting {
  margin-left: 9vw;
  margin-right: 9vw;
  background: linear-gradient(to top, #b5f1f3 50%, transparent 30%);
}

.bottom {
  text-align: center;
  font-size: 15px;
  font-family: "IM_Hyemin-Regular";
  padding-bottom: 2vh;
  font-weight: 700;
}

.iconBtn {
  float: right;
  width: 30px;
  height: 30px;
}

#label-container {
  font-size: 0px;
}

.pencil1 {
  width: 50vw;
  vertical-align: middle;
}

.pencil2 {
  width: 15px;
  margin: 2px;
  vertical-align: middle;
}

.study-msg {
  font-family: "IM_Hyemin-Regular";
  font-size: 8vw;
  font-weight: 700;
  margin-left: 15%;
}
</style>

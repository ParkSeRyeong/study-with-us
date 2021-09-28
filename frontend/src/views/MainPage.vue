<template>
  <div>
    <NavBar/>
    <div class="main">
      <div class="wise-saying">
        <div class="text-2 nanumbarungothic-ultra-light-black-50px">“</div>
        <div class="text-1 nanumbarungothic-ultra-light-black-30px">꿈은 꾸는 자에게 온다</div>
        <div class="text-3 nanumbarungothic-ultra-light-black-50px">”</div>
      </div>
      <div class="study-time" :style="{ 'min-height': 0.8 * this.$store.state.window_width + 'px' }">
        <div class="time-area" style="margin:auto;">
          <img
            class="playbtn animate-enter"
            :style="{ 'width': 0.15 * this.$store.state.window_width + 'px' }"
            src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/play-btn@2x.svg"
          />
          <div class="todaydate animate-enter1" :style="{ 'font-size': 0.06 * this.$store.state.window_width + 'px', 'margin-top': 0.02 * this.$store.state.window_width + 'px' }">2021.09.10</div>
          <h1 class="focusstudytime animate-enter2" :style="{ 'font-size': 0.11 * this.$store.state.window_width + 'px', 'margin-top': 0.03 * this.$store.state.window_width + 'px' }">03 : 25 : 13</h1>
          <img
            class="line-1 animate-enter4"
            src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/line-1@2x.svg"
          />
          <div class="totalstudytime animate-enter3" :style="{ 'font-size': 0.06 * this.$store.state.window_width + 'px', 'margin-top': 0.05 * this.$store.state.window_width + 'px' }">05:07:25</div>
        </div>
      </div>
      <br>
      <div class="todolist">
        투두리스트
      </div>

      <div class="container-center-horizontal">
        <div class="main-page screen" data-id="59:2">
          <div class="quote" data-id="59:42">
            
          </div>
          <div class="overlap-group1" data-id="an|nAposqBK">
            
          </div>
          <div class="to-do" data-id="59:4">
            <div class="flex-row" data-id="an|yrmKE0Kz">
              <div class="to-do-list" data-id="59:24">TO-DO LIST</div>
              <img
                class="check-icon"
                data-id="59:25"
                src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/check-icon@2x.svg"
              />
              <img
                class="edit"
                data-id="59:5"
                src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/edit@2x.svg"
              />
            </div>
            <div class="to-do-list-item" data-id="59:13">
              <div class="flex-col" data-id="an|PNQU633z">
                <div class="todo1" data-id="59:23"></div>
                <div class="todo2" data-id="59:17">
                  <img
                    class="check"
                    data-id="59:19"
                    src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/check-2@2x.svg"
                  />
                </div>
                <div class="todo3" data-id="59:16"></div>
              </div>
              <div class="flex-col-1 nanumbarungothic-ultra-light-black-18px" data-id="an|d0D9wAoY">
                <div class="todo1-1" data-id="59:22">알고리즘 풀이</div>
                <div class="todo2-1" data-id="59:15">카카오 자소서 쓰기</div>
                <div class="todo3-1" data-id="59:14">Notion 정리</div>
                <div class="todo1-1" data-id="59:22">알고리즘 풀이</div>
                <div class="todo2-1" data-id="59:15">카카오 자소서 쓰기</div>
                <div class="todo3-1" data-id="59:14">Notion 정리</div>
                <div class="todo1-1" data-id="59:22">알고리즘 풀이</div>
                <div class="todo2-1" data-id="59:15">카카오 자소서 쓰기</div>
                <div class="todo3-1" data-id="59:14">Notion 정리</div>
                <div class="todo1-1" data-id="59:22">알고리즘 풀이</div>
                <div class="todo2-1" data-id="59:15">카카오 자소서 쓰기</div>
                <div class="todo3-1" data-id="59:14">Notion 정리</div>
                <div class="todo1-1" data-id="59:22">알고리즘 풀이</div>
                <div class="todo2-1" data-id="59:15">카카오 자소서 쓰기</div>
                <div class="todo3-1" data-id="59:14">Notion 정리</div>
                <div class="todo1-1" data-id="59:22">알고리즘 풀이</div>
                <div class="todo2-1" data-id="59:15">카카오 자소서 쓰기</div>
                <div class="todo3-1" data-id="59:14">Notion 정리</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/common/NavBar.vue'
export default {
  name: 'MainPage',
  components: {
    NavBar,
  },
}
</script>

<style>
.main {
  padding: 16px;
  margin-bottom: 30px;
  align-items: center;
}

.study-time {
  text-align: center;
  background-image: url(https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429a1f66b3b00ba3a869ec/img/blob1@2x.svg); 
  background-size: contain;
  background-repeat: no-repeat;
  width: 100%;
}

.time-area {
  width: 100%;
  height: 100%;
  padding-top: 10%;
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
  animation: animate-enter-frames 0.40s ease-in-out 0.00s 1 normal forwards;
  display: block;
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

.todaydate {
  color: var(--black);
  font-family: "NanumBarunGothic-Light";
  font-weight: 300;
  letter-spacing: 0;
  margin-right: 1.95px;
  min-height: 25px;
  min-width: 118px;
  opacity: 0;
transform: translate(0, 25px);
}

.todaydate.animate-enter1 {
  animation: animate-enter1-frames 0.40s ease-in-out 0.00s 1 normal forwards;
  opacity: 0;
transform: translate(0, 25px);
}

@keyframes animate-enter1-frames {
  from{opacity: 0;
transform: translate(0, 25px);
}
to{opacity: 1;
transform: translate(0,0);
}
}

.focusstudytime {
  align-self: flex-end;
  color: var(--black);
  font-family: "NanumBarunGothic-Light";
  font-weight: 300;
  letter-spacing: 0;
  min-height: 46px;
  min-width: 202px;
  text-align: center;
  opacity: 0;
transform: translate(0, 25px);
}

.focusstudytime.animate-enter2 {
  animation: animate-enter2-frames 0.40s ease-in-out 0.30s 1 normal forwards;
  opacity: 0;
transform: translate(0, 25px);
}

@keyframes animate-enter2-frames {
  from{opacity: 0;
transform: translate(0, 25px);
}
to{opacity: 1;
transform: translate(0,0);
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
  animation: animate-enter4-frames 0.40s ease-in-out 0.30s 1 normal forwards;
  display: block;
  opacity: 0;
transform: translate(0, 25px);
}

@keyframes animate-enter4-frames {
  from{opacity: 0;
transform: translate(0, 25px);
}
to{opacity: 1;
transform: translate(0,0);
}
}

.totalstudytime {
  color: var(--black);
  font-family: "NanumBarunGothic-Light";
  font-weight: 300;
  letter-spacing: 0;
  min-height: 29px;
  min-width: 105px;
  text-align: center;
  opacity: 0;
}

.totalstudytime.animate-enter3 {
  animation: animate-enter3-frames 0.40s ease-in-out 0.30s 1 normal forwards;
  opacity: 0;
}

@keyframes animate-enter3-frames {
  from{opacity: 0;
  }
  to{opacity: 1;
  }
}


</style>
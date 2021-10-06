<template>
  <div class="container-center-horizontal">
    <div
      class="entrance-page screen"
      :style="{ width: width + 'px', height: height + 'px' }"
    >
      <div class="overlap-group2">
        <img class="blob" src="@/assets/images/Blob.png" />
        <h1
          class="study-with-us animate-enter"
          :style="{ 'font-size': title_fontsize + 'px', top: title_top + 'px' }"
        >
          STUDY<br />WITH<br />US
        </h1>
      </div>
      <div class="login">
        <div v-if="!this.login_state">
          <input
            class="form-control login-form"
            type="text"
            placeholder="ID"
            v-model="credentials.userid"
          />
          <input
            class="form-control login-form"
            type="password"
            placeholder="PASSWORD"
            v-model="credentials.password"
            @keyup.enter="userLogin"
          />
          <button
            class="btn login-btn"
            type="button"
            style="width: 100%"
            @click="userLogin"
          >
            로그인
          </button>
        </div>
        <div v-else>
          <div class="welcome-ment">{{ this.credentials.userid }}님 어서오세요!</div>
          <button
            class="btn login-btn"
            type="button"
            style="width: 100%"
            @click="pushToMain"
          >
            메인페이지로
          </button>
        </div>

        <div class="container">
          <div class="row justify-content-between">
            <div @click="pushToSignup()" class="col below-btn-left">
              회원가입
            </div>
            <div class="col below-btn-right">아이디/비밀번호 찾기</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "EntrancePage",
  computed: {
    login_id: function() {
      return this.$store.state.login.userid;
    },
  },
  data: function() {
    return {
      width: 0,
      height: 0,
      title_fontsize: 0,
      title_right: 0,
      title_top: 0,
      login_top: 0,
      login_state: false,
      credentials: {
        userid: null,
        password: null,
      },
    };
  },
  created() {
    this.login_state = false;
    this.width = window.innerWidth;
    this.height = window.innerHeight;
    this.title_fontsize = window.innerHeight * 0.074;
    this.title_top = window.innerHeight * 0;
    this.login_top = window.innerHeight * 0.5;
    // console.log(this.$store.getters["login/decodedToken"]);
    if (this.$store.getters["login/decodedToken"]) {
      this.$router.push({ name: "MainPage" });
    }
  },
  methods: {
    pushToMain() {
      this.$router.push({ name: "MainPage" });
    },
    async userLogin() {
      await this.$store.dispatch("login/getJWT", this.credentials);
      this.$router.push({ name: "MainPage" });
    },
    pushToSignup() {
      this.$router.push({ name: "Signup" });
    },
  },
};
</script>

<style scoped>
.welcome-ment {
  margin-bottom: 20px;
  text-align: center;
  font-family: "NanumBarunGothic-Regular";
}

.login {
  position: fixed;
  bottom: 30px;
  padding: 15px;
  width: 100%;
}

.entrance-page {
  align-items: center;
  background-image: url(https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429912d2a048cf6bf13594/img/entrance-page@1x.png);
  background-size: cover;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  padding: 99px 0 0 0;
}

.entrance-page .overlap-group2 {
  align-self: flex-end;
  height: 220px;
  margin-right: -100px;
  position: relative;
  width: 316px;
}

.entrance-page .blob {
  height: 209px;
  left: 104px;
  position: absolute;
  top: 0;
  width: 212px;
}

.entrance-page .study-with-us {
  color: #218d93;
  font-family: "Cafe24Shiningstar";
  font-weight: 400;
  letter-spacing: 0;
  position: absolute;
  text-align: right;
  text-shadow: 4px 4px 7px #00000040;
  opacity: 0;
  right: 120px;
}

.entrance-page .study-with-us.animate-enter {
  animation: animate-enter-frames 0.6s ease-in-out 0.5s 1 normal forwards;
  opacity: 0;
}

@keyframes animate-enter-frames {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.entrance-page .login-form {
  font-family: "NanumBarunGothic-Light";
  width: 100%;
  margin-bottom: 10px;
}

.entrance-page .login .login-btn {
  background-color: #21b2ba;
  font-family: "NanumBarunGothic-Bold";
  color: white;
  margin-bottom: 10px;
  width: 100%;
}

.entrance-page .login .below-btn-left {
  font-family: "NanumBarunGothic-Regular";
  padding: 0;
  font-size: 13px;
}

.entrance-page .login .below-btn-right {
  font-family: "NanumBarunGothic-Regular";
  font-size: 13px;
  padding: 0;
  text-align: right;
}

.entrance-page .flex-row-1 {
  align-items: flex-start;
  display: flex;
  margin-top: 17px;
  min-width: 125px;
}

.entrance-page .googlelogo {
  height: 57px;
  object-fit: contain;
  width: 57px;
  padding: 0;
}

.entrance-page .kakaologo {
  height: 57px;
  margin-left: 11px;
  object-fit: contain;
  width: 57px;
  padding: 0;
}

@import url("https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css");

@font-face {
  font-family: "NanumBarunGothic-Regular";
  font-style: normal;
  font-weight: 400;
  src: url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot");
  src: url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.eot?#iefix")
      format("embedded-opentype"),
    url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.woff")
      format("woff"),
    url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWeb.ttf")
      format("truetype");
}

@font-face {
  font-family: "NanumBarunGothic-Bold";
  font-style: normal;
  font-weight: 700;
  src: url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.eot");
  src: url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.eot?#iefix")
      format("embedded-opentype"),
    url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.woff")
      format("woff"),
    url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebBold.ttf")
      format("truetype");
}

@font-face {
  font-family: "NanumBarunGothic-Light";
  font-style: normal;
  font-weight: 300;
  src: url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebLight.eot");
  src: url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebLight.eot?#iefix")
      format("embedded-opentype"),
    url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebLight.woff")
      format("woff"),
    url("//cdn.jsdelivr.net/font-nanumlight/1.0/NanumBarunGothicWebLight.ttf")
      format("truetype");
}

.nanumbarungothic * {
  font-family: "NanumBarunGothic", sans-serif;
}

@font-face {
  font-family: "BM DoHyeon-Regular";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

@font-face {
  font-family: "Cafe24Shiningstar";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.1/Cafe24Shiningstar.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
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

.valign-text-middle {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

* {
  box-sizing: border-box;
}
</style>

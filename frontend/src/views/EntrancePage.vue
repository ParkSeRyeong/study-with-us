<template>
  <div class="container-center-horizontal">
    <div class="entrance-page screen" :style="{ width: width + 'px', height: height + 'px' }">
      <div class="overlap-group2">
        <img
          class="blob"
          src="@/assets/images/Blob.png"
        />
        <h1 class="study-with-us animate-enter" :style="{ 'font-size': title_fontsize + 'px', top: title_top + 'px' }">STUDY<br />WITH<br />US</h1>
      </div>
      <div class="login" :style="{ top: login_top }">
        <div class="overlap-group1">
          <input class="form-control" type="text" placeholder="ID">
        </div>
        <div class="overlap-group3">
          <input class="password nanumbarungothic-ultra-light-boulder-18px" type="password" placeholder="Password">
        </div>
        <div class="loginbtn">
          <div class="overlap-group">
            <div class="loginbtntext valign-text-middle">로그인</div>
          </div>
        </div>
        <div class="flex-row nanumbarungothic-regular-normal-black-13px">
          <div class="signupbtntext">회원가입</div>
          <div class="finduserbtntext">아이디/비밀번호 찾기</div>
        </div>
        <div class="flex-row-1">
          <img
            class="googlelogo"
            src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429912d2a048cf6bf13594/img/google-logo@2x.png"
          />
          <img
            class="kakaologo"
            src="https://anima-uploads.s3.amazonaws.com/projects/614138d997e275bf9f1a3a68/releases/61429912d2a048cf6bf13594/img/kakao-logo@2x.png"
          />
        </div>

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EntrancePage',
  data: function () {
    return {
      width: 0,
      height: 0,
      title_fontsize: 0,
      title_right: 0,
      title_top: 0,
      login_top: 0,
    }
  },
  created() {
    this.width = window.innerWidth
    this.height = window.innerHeight
    this.title_fontsize = window.innerHeight * 0.074
    this.title_top = window.innerHeight * 0
    this.login_top = window.innerHeight * 0.5
  }
}
</script>

<style>
.login {
  position: absolute;
  bottom: auto;
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
  color: #218D93;
  font-family: 'Cafe24Shiningstar';
  font-weight: 400;
  letter-spacing: 0;
  position: absolute;
  text-align: right;
  text-shadow: 4px 4px 7px #00000040;
  opacity: 0;
  right: 120px;
}

.entrance-page .study-with-us.animate-enter {
  animation: animate-enter-frames 0.60s ease-in-out 0.50s 1 normal forwards;
  opacity: 0;
}

@keyframes animate-enter-frames {
  from{opacity: 0;
}
to{opacity: 1;
}
}

.entrance-page .overlap-group1 {
  align-items: flex-end;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0px 4px 4px #69bcc1;
  display: flex;
  height: 41px;
  margin-right: 1.0px;
  margin-top: 101px;
  min-width: 266px;
  padding: 8px 12px;
}

.entrance-page .overlap-group3 {
  align-items: flex-end;
  background-color: var(--white);
  border-radius: 10px;
  box-shadow: 0px 4px 4px #69bcc1;
  display: flex;
  height: 41px;
  margin-right: 1.0px;
  margin-top: 19px;
  min-width: 266px;
  padding: 7px 12px;
}

.entrance-page .password {
  letter-spacing: 0;
  min-height: 21px;
  min-width: 81px;
}

.entrance-page .loginbtn {
  align-items: flex-start;
  display: flex;
  margin-left: 1.0px;
  margin-top: 19px;
  min-width: 268px;
}

.entrance-page .overlap-group {
  align-items: flex-end;
  background-color: var(--teal-blue);
  border-radius: 10px;
  box-shadow: 0px 4px 4px #69bcc1;
  display: flex;
  height: 45px;
  justify-content: flex-end;
  min-width: 266px;
  padding: 9.8px 104px;
}

.entrance-page .loginbtntext {
  color: var(--white);
  font-family: "NanumBarunGothic-Bold";
  font-size: var(--font-size-s2);
  font-weight: 700;
  height: 25px;
  letter-spacing: 0;
  text-align: right;
  width: 56px;
}

.entrance-page .flex-row {
  align-items: flex-start;
  display: flex;
  height: 15px;
  margin-left: 1.0px;
  margin-top: 13px;
  min-width: 268px;
}

.entrance-page .signupbtntext {
  letter-spacing: 0;
  min-height: 15px;
  min-width: 49px;
}

.entrance-page .finduserbtntext {
  letter-spacing: 0;
  margin-left: 103px;
  min-height: 15px;
  min-width: 116px;
  text-align: right;
}

.entrance-page .flex-row-1 {
  align-items: flex-start;
  display: flex;
  margin-left: 4.0px;
  margin-top: 17px;
  min-width: 125px;
}

.entrance-page .googlelogo {
  height: 57px;
  object-fit: cover;
  width: 57px;
}

.entrance-page .kakaologo {
  height: 57px;
  margin-left: 11px;
  object-fit: cover;
  width: 57px;
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

.nanumbarungothic * {
 font-family: 'NanumBarunGothic', sans-serif;
}


@font-face {
    font-family: "BM DoHyeon-Regular";
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_one@1.0/BMDOHYEON.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

@font-face {
    font-family: 'Cafe24Shiningstar';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.1/Cafe24Shiningstar.woff') format('woff');
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
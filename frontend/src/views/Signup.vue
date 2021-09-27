<template>
  <div>
    <div class="page-background container" :style="{ width: this.$store.state.window_width + 'px', height: this.$store.state.window_height + 'px' }">
      <p class="signup-title">회원 가입</p>
      <br>
      <p class="signup-eletitle">아이디</p>
      <input type="text" id="userid" class="form-control" placeholder="아이디 입력" v-model="credentials.userid">
      <button
        v-if="idDup === null || idDup === '401' || idDup === '100'"
        class="btn dup-btn"
        @click="idDupCheck"
      >
        중복검사
      </button>
      <p
        v-if="idDup === '401'"
        class="warning-text"  
      >
        "이미 존재하는 아이디입니다."
      </p>
      <p
        v-else-if="idDup === '200'"
        class="warning-text"
      >
        "해당 아이디를 사용할 수 있습니다."
      </p>
      <p
        v-else-if="idDup === '100'"
        class="warning-text"
      >
        "아이디 입력창이 비어있습니다. 아이디를 입력해주세요."
      </p>
      <p class="signup-eletitle">비밀번호</p>
      <input type="password" id="password" class="form-control" placeholder="비밀번호 입력" v-model="credentials.password">
      <p class="signup-eletitle">비밀번호 확인</p>
      <input type="password" id="passwordConfirmation" class="form-control" placeholder="비밀번호 확인" v-model="passwordConfirmation">
      <p
        v-if="
          credentials.password === passwordConfirmation &&
          credentials.password != null
        "
        class="warning-text"
      >
        "비밀번호가 일치합니다."
      </p>
      <p
        v-else-if="credentials.password != null"
        class="warning-text"
      >
        "비밀번호가 일치하지 않습니다."
      </p>
      <p class="signup-eletitle">닉네임</p>
      <input type="text" id="nickname" class="form-control" placeholder="닉네임 입력" v-model="credentials.nickname">
      <p class="signup-eletitle">이름</p>
      <input type="text" id="name" class="form-control" placeholder="이름 입력" v-model="credentials.name">
      <p class="signup-eletitle">전화번호</p>
      <input type="tel" id="phone" class="form-control" placeholder="전화번호 입력" v-model="credentials.phone">
    </div>  
    <button
      type="button"
      class="signup-btn"
      @click="signup(credentials)"
    >
      회원가입
    </button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Signup',
  data: function () {
    return {
      credentials: {
        userid: null,
        password: null,
        nickname: null,
        name: null,
        phone: null,
      },
      idDup: null,
      idDupid: null,
      emailSend: null,
      passwordConfirmation: null,
      signupstate: null,
      warningtext: null,
    }
  },
  methods: {
    idDupCheck: function () {
      axios({
        method: 'get',
        url: `http://localhost:8080/user/idDuplciateCheck/${this.credentials.userid}`
      })
        .then((res)=> {
          console.log(res)
          if (this.credentials.userid == null) {
            this.idDup = '100'
          } else {
            if (res.data.statusCode == 200) {
              this.idDup = '200'
              this.idDupid = this.credentials.userid
            } else if (res.data.statusCode == 401) {
              this.idDup = '401'
            }
          }
        })
        .catch((err) => {
          console.log(err)
          this.emailDup = '401'
        })
    },

    signup: function () {
      if (
        this.idDupid === this.credentials.userid &&
        this.credentials.password === this.passwordConfirmation
      ) {
        this.signupstate = 'success'
        axios({
          method: 'post',
          url: 'http://localhost:8080/user/signup',
          data: this.credentials,
        })
          .then((res) => {
            console.log(res)
          })
          .catch((err) => {
            console.log(err)
          })
      } else {
        this.signupstate = 'fail'
        if (!(this.idDupid === this.credentials.userid)) {
          this.warningtext = 
            '중복체크한 아이디와 입력된 아이디가 다릅니다. 다시 확인해주세요.'
        } else if (!(this.credentials.password === this.passwordConfirmation)) {
          this.warningtext =
            '비밀번호가 다릅니다.'
        }
        console.log(this.warningtext)
      }
    },
    gotoLogin: function () {
      this.$router.push({ name: 'entrancePage'})
    },
  },
  created() {
    if (this.$store.getters['login/decodedToken']) {
      this.$router.push({ name: 'MainPage' })
    }
  },
  mounted() {
    window.scrollTo(0, 0)
  }
}
</script>

<style>
.page-background {
  background-color: #ffffff;
  background-size: cover;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  padding: 20px;
  font-family: "NanumBarunGothic-Light";
}

.signup-title {
  font-size: 20px;
}

.signup-eletitle {
  text-align: left;
  padding-top: 10px;
  padding-bottom: 5px;
  
}

.warning-text {
  text-align: left;
  font-size: 12px;
  margin-top: 10px;
}

.dup-btn {
  background-color: #218D93;
  color: white;
  margin-top: 10px;
}

.signup-btn {
  align-items: center;
  background-color: #218D93;
  color: white;
  position: absolute;
  bottom: 0px;
  display: inline-block;
  font-weight: 400;
  line-height: 2;
  text-align: center;
  text-decoration: none;
  vertical-align: middle;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  user-select: none;
  border: 1px solid transparent;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  width: 100%;
  transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out, border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}
</style>
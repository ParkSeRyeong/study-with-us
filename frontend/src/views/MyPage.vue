<template>
    <div>
        <TitleBar>MY PAGE</TitleBar>
        
        <form class="row">
            
            <div class="form-group">
                <label class="label">아이디</label>
                <input class="form-control" type="text" id="account-fn" :value="userId" disabled="">
            </div>
        
        
            <div class="form-group">
                <label class="label">닉네임</label>
                <input class="form-control" type="text" id="account-ln" :value="nickName">
            </div>
        
            <div class="form-group">
                <label class="label">이름</label>
                <input class="form-control" type="text" id="account-email" :value="name" disabled="">
            </div>
        
            <div class="form-group">
                <label class="label">핸드폰</label>
                <input class="form-control" type="text" id="account-email" :value="phone" disabled="">
            </div>
        
            <div class="form-group">
                <label class="label">새 비밀번호</label>
                <input class="form-control" type="password" id="account-pass">
            </div>
            
            <div class="button-group d-flex flex-wrap justify-content-between align-items-center">
                <button id="logoutBtn" class="btn" @click="logout" data-toast="" data-toast-position="topRight" data-toast-type="success" data-toast-icon="fe-icon-check-circle" data-toast-title="Success!" data-toast-message="Your profile updated successfuly."><span>로그아웃</span></button>
                <button id="saveBtn" class="btn" @click="save" data-toast="" data-toast-position="topRight" data-toast-type="success" data-toast-icon="fe-icon-check-circle" data-toast-title="Success!" data-toast-message="Your profile updated successfuly."><span>저장하기</span></button>
            </div>
            
        </form>
    </div>
</template>

<script>
import TitleBar from '@/components/TitleBar';
import axios from 'axios';
import SERVER from "@/api/api";

export default {
    components: {
        TitleBar
    },
    data() {
        return {
          userId:'',
          nickName:'',
          name:'',
          phone:''
        }
    },
    mounted() {
       axios({
            method: 'get',
            url: `${SERVER.URL}/user/userinfo`,
            headers: {
                'Access-Control-Allow-Origin': "*",
                Authorization: this.$store.state.login.userToken
            }
          }).then(res => {
              this.userId = res.data.userid;
              this.nickName = res.data.nickname;
              this.name = res.data.name;
              this.phone = res.data.phone;
          })
    },
    methods: {
      logout () {
        this.$store.dispatch('login/deleteJWT')
        this.$router.push({ name: 'EntrancePage' })
      },

      save() {
        axios({
          method: 'put',
          url: `${SERVER.URL}/user/update`,
          headers: {
              'Access-Control-Allow-Origin': "*",
              Authorization: this.$store.state.login.userToken
          },
          data: {
            user:
            {
            userid : "abcd",
            password : "1234",
            nickname : "ssafy",
            name : "김싸피",
            phone : "010-9999-9999"
            }
          }
          }).then(res => {
                console.log(res)
                //this.$router.push('/user/challenge');
          }).catch(err => {
                console.log(err);
                })
      }
    }
}
</script>

<style>
  .row {
    width: 95vw;
    height: 80vh;
    margin-top: 2vh;
  }

  .form-control {
    margin: 1vh;
    margin-left: 2vh;
  }

  .label {
    margin-left: 2vh;
    font-family: 'NanumBarunGothic-Bold';
  }

  .button-group {
    margin-left: 2vh;
  }

  #logoutBtn {
    background-color: #f5ff6971;
    color: #525F7F;
    border-color: #D4DFE6;
  }

  #saveBtn {
    background-color: #f5ff6971;
    color: #44471571;
    border-color: #D4DFE6;
  }

  span {
    color: black;
    font-family: 'NanumBarunGothic-light';
  }


   
</style>
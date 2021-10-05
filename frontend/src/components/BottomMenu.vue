<template>
    <div>
        <router-view></router-view>
        <div class="bottom_menu">
            <div>
                <router-link to="/selfstudy"><img class='icon' src="@/assets/images/1.png"></router-link>
            </div>
            <div>
                <router-link to="/diary"><img class='icon' src="@/assets/images/2.png"></router-link>
            </div>
            <div>
                <router-link to="/main"><img class='icon' src="@/assets/images/3.png"></router-link>
            </div>
            <div>
                <router-link to="/studygroup"><img class='icon' src="@/assets/images/4.png"></router-link>
            </div>
            <div>
                <button @click="logout()">logout</button>

                <!-- <router-link to="/mypage"><img class='icon' src="@/assets/images/5.png"></router-link> -->
            </div>
        </div>
    </div>
</template>

<script>
export default {
  methods: {
    logout () {
      this.$store.dispatch('login/deleteJWT')
      this.$router.push({ name: 'EntrancePage' })
    } 
  }
}
</script>

<style>
    .bottom_menu { 
        position: fixed; 
        bottom: 0px; 
        left: 0px; 
        width: 100%; 
        height: 50px; 
        z-index:100; 
        border-top: 1px solid black; 
        background-color: white;
        }

	.bottom_menu > div { 
        float: left; 
        width: 20%; 
        height: 100%; 
        text-align: center; 
        padding-top: 10px; 
        }
    
    .icon {
        width: 30px;
        height: 30px;
    }
</style>
<template>
    <div>
        <TitleBar v-if='loading || !aiPage'>SELF STUDY</TitleBar>
        
        <div id='container'>
            <div id= 'wrap'>
                <div id="init" v-if="!loading && !aiPage" @click="clickStart()">
                    <br><br><br><br><br><br><br>
                    <p style="font-size:20px">손과 얼굴이 나오도록 각도를 조정하고</p>
                    <br><br><br>
                    <img id="pencil1" src='@/assets/images/pencil.png'>
                    <br><br><br><br>
                    <p>아무곳이나 터치 해주세요!</p>
                </div>    

                <div id="loading m-5" v-if="loading" class="y-border">
                    <br><br><br><br><br><br><br>
                    <p style="font-size:20px">SELF STUDY를 시작합니다</p>
                    <br><br><br>
                    <img id="pencil1" src='@/assets/images/pencil.png'>
                    <br><br><br><br>
                    <p>카메라 준비중...</p>
                </div>
            </div>

            <div v-if="aiPage">

                <div v-if="webcamLoad" id='time'>
                    <p class="top">집중 시간 : {{formattedFocusElapsedTime}}</p><br>
                    <p class="bottom"><img class="pencil2" src="@/assets/images/zzz.png"/>{{formattedSleepElapsedTime}}<img class="pencil2" src="@/assets/images/zzz.png"/>  <img class="pencil2" src="@/assets/images/폰.png"/>{{formattedPhoneElapsedTime}}<img class="pencil2" src="@/assets/images/폰.png"/></p>
                    <div id='btn'>
                        <div @click="stop"><img class='icon' src='@/assets/images/stop.png'></div>
                        <div v-if="toggle" @click="start"><img class='icon' src='@/assets/images/play.png'></div>
                        <div v-else @click="pause"><img class='icon' src='@/assets/images/pause.png'></div>
                    </div> 

                </div> 
                
                <div id='webcam-container'></div>
                <br>
                <div v-if="webcamLoad" id='stopwatch'>
                    <div  style="text-align:center; font-size: 13vw" >
                        <h2 id="studying" style="color:green">공부 중...</h2>
                        <h2 id='phone' style="color:red" class="blink">휴대폰 그만!</h2>
                        <h2 id="snoozing" style="color:red" class="blink">일어납시다!</h2>
                    </div>
                </div>
                <br><br><br><br><br><br><br><br>
                <div id="label-container"></div>
            </div>
        </div>
    </div>
</template>


<script>
// the link to your model provided by Teachable Machine export panel
import * as tf from'@tensorflow/tfjs';  // eslint-disable-line no-unused-vars
import * as tmImage from '@teachablemachine/image';
import TitleBar from '@/components/TitleBar';
import axios from "axios";
import SERVER from "@/api/api";

let model, webcam, labelContainer, maxPredictions;

export default {
    el:'#ai',
    components: {
    TitleBar
    },

    data() {
        return{
            loading : false,
            aiPage : false,
            webcamLoad : false,
            behavior : ['집중', '핸드폰', '졸기'],
            allElapseTime: 0,
            focusElapsedTime: 0,
            sleepElapsedTime: 0,
            phoneElapsedTime: 0,
            focusTimer: undefined,
            sleepTimer: undefined,
            phoneTimer: undefined,
            focusCheck : false,
            sleepCheck : false,
            phoneCheck : false,
            toggle : false,
            pauseToggle : false,
            formattedAllElapsedTimeRgF: '',
            formattedFocusElapsedTimeRgF: '',
            formattedSleepElapsedTimeRgF: '',
            formattedPhoneElapsedTimeRgF: '',
        }
    },

    mounted() {
        axios({
                method: 'get',
                url: `${SERVER.URL}/study/stop`,
                headers: {
                    'Access-Control-Allow-Origin': "*",
                    Authorization: this.$store.state.login.userToken
                },
            })
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

        async init() {
            this.loading = true;

            const URL = 'https://teachablemachine.withgoogle.com/models/uK53pgina/'
            const modelURL = URL + 'model.json';
            const metadataURL = URL + 'metadata.json';
            const flip = true; // whether to flip the webcam

            
            // load the model and metadata
            // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
            // or files from your local hard drive
            // Note: the pose library adds "tmImage" object to your window (window.tmImage)
        
            model = await tmImage.load(modelURL,metadataURL);
            maxPredictions = model.getTotalClasses();
            
            this.aiPage = true;
            // Convenience function to setup a webcam
            webcam = new tmImage.Webcam(360, 360, flip); // width, height, flip
            await webcam.setup(); // request access to the webcam
            await webcam.play();
            window.requestAnimationFrame(this.loop);

            
            // append elements to the DOM
            document.getElementById('webcam-container').appendChild(webcam.canvas);
            labelContainer = document.getElementById('label-container');
            for (let i = 0; i < maxPredictions; i++) {
                // and class labels
                labelContainer.appendChild(document.createElement('div'));
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
                const classPrediction = prediction[i].className + ': ' + prediction[i].probability.toFixed(2);
                labelContainer.childNodes[i].innerHTML = classPrediction;
                
                    if ((prediction[i].probability.toFixed(2) >= 0.9 && !this.toggle) || this.pauseToggle){
                        if(prediction[i].probability.toFixed(2) >= 0.9){
                            this.pauseToggle = false;
                        }
                        if(prediction[i].className === this.behavior[0]){
                            document.getElementById('studying').style.display = 'inline'
                            document.getElementById('phone').style.display = 'none'
                            document.getElementById('snoozing').style.display = 'none'
                            document.getElementById('webcam-container').classList.remove('blink')
                            if(!this.focusCheck){
                                this.startFocus();
                                this.pauseSleep();
                                this.pausePhone();
                                this.focusCheck = true;
                                this.sleepCheck = false;
                                this.phoneCheck = false;
                            }
                        }
                        
                        else if(prediction[i].className === this.behavior[1]){
                            document.getElementById('studying').style.display = 'none'
                            document.getElementById('phone').style.display = 'inline'
                            document.getElementById('snoozing').style.display = 'none'
                            document.getElementById('webcam-container').classList.add('blink')
                            if(!this.phoneCheck){
                                this.startPhone();
                                this.pauseFocus();
                                this.pauseSleep();
                                this.focusCheck = false;
                                this.sleepCheck = false;
                                this.phoneCheck = true;
                            }
                        }
                        else{
                            document.getElementById('studying').style.display = 'none'
                            document.getElementById('phone').style.display = 'none'
                            document.getElementById('snoozing').style.display = 'inline'
                            document.getElementById('webcam-container').classList.add('blink')
                            if(!this.sleepCheck){
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
        date.setSeconds((this.focusElapsedTime + this.sleepElapsedTime + this.phoneElapsedTime) / 1000);
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
        start(){
            this.toggle = false;
            this.pauseToggle = true;
        },
        pause(){
            this.toggle = true;
            clearInterval(this.focusTimer);
            clearInterval(this.sleepTimer);
            clearInterval(this.phoneTimer);
            document.getElementById('studying').style.display = 'none'
            document.getElementById('phone').style.display = 'none'
            document.getElementById('snoozing').style.display = 'none'
            document.getElementById('webcam-container').classList.remove('blink')
        },
        stop() {
            this.formattedAllElapsedTimeRg();
            this.formattedFocusElapsedTimeRg();
            this.formattedSleepElapsedTimeRg();
            this.formattedPhoneElapsedTimeRg();
            console.log(this.formattedAllElapsedTimeRgF);
            axios({
                method: 'post',
                url: `${SERVER.URL}/study/stop`,
                headers: {
                    'Access-Control-Allow-Origin': "*",
                    Authorization: this.$store.state.login.userToken
                },
                data: {
                    alltime : this.formattedAllElapsedTimeRgF,
                    focustime : this.formattedFocusElapsedTimeRgF,
                    sleeptime : this.formattedSleepElapsedTimeRgF,
                    phonetime : this.formattedPhoneElapsedTimeRgF,
                    todo : [],
                    screen : 0,
                    sound : 0,
                    sentence : 1
                }
            }).then(res => {
                console.log(res)
            })
        },
        alltime() {
            
        }
        

    }
}

// More API functions here:
// https://github.com/googlecreativelab/teachablemachine-community/tree/master/libraries/image



// Load the image model and setup the webcam

</script>

<style>
    
    .pencil1{
            width:50vw;
            vertical-align: middle;
    }

    #wrap *{
        text-align: center;
    }

    #init {
        height: 100vh;
    }

    img {
        width:50vw;
        vertical-align: middle;
    }
    
    .blink{
        animation: blink 1s linear infinite;
    }
    @keyframes blink{
        0%{opacity: 1;}
        50%{opacity: 0.5;}
        100%{opacity: 1;}
    }
    #stopwatch * {
        font-family: 'NanumBarunGothic-Bold';
    }

    p{
        font-family: 'NanumBarunGothic-Light';
    }

    #stopwatch {
        position: relative;
    }

    .top {
        font-size: 30px;
        font-family: 'NanumBarunGothic-Bold';
        margin-top: 5vh;
    }

    .bottom {
        font-size: 15px;
        font-family: 'NanumBarunGothic-Light';
    }

    .icon{
        float: right;
    }

    #touch {
        color: #cdd835;
        margin-bottom: 10px;
    }

    #label-container{
        font-size: 5px;
    }
    .pencil2{
        width:15px;
        margin: 2px;
    }
    
    #time{
        text-align: center;
    }
</style>

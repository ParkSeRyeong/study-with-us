<template>
    <div>
        <div  id="init" v-if="!loading && !aiPage">
            <h1 class="m-5" style="font-size : 50px">Self Study</h1>
            <h5>손과 얼굴이 나오도록 각도를 조정해주세요</h5>
            <div class="m-5">
                <a @click="clickStart()" class="btn2 m-3">START</a>
            </div>
        </div>    

        <div id="loading m-5" v-if="loading" class="y-border">
            <h1 class="m-5">SELF STUDY를 시작합니다</h1>
            <h5>카메라 준비중</h5>
    
            <i class="fa fa-spinner fa-pulse fa-5x fa-fw m-5" ></i>
            <h6>Loading...</h6>
        </div>

        <div v-if="aiPage">
            <p align='middle' id='webcam-container'></p>
            <div v-if="webcamLoad">
                <h2 id="studying" style="color:green">공부중...</h2>
                <h2 id='phone' style="color:red" class="blink">휴대폰 그만...!</h2>
                <h2 id="snoozing" style="color:red" class="blink">일어나서 공부 합시다!</h2>
            </div>


            <div id="label-container"></div>
        </div>
    </div>
</template>


<script>
// the link to your model provided by Teachable Machine export panel
import * as tf from'@tensorflow/tfjs';  // eslint-disable-line no-unused-vars
import * as tmImage from '@teachablemachine/image';

let model, webcam, labelContainer, maxPredictions;

export default {
    data() {
        return{
            loading : false,
            aiPage : false,
            webcamLoad : false,
            behavior : ['공부', '핸드폰', '엎드려 자기']

        }
    },

    mounted() {

        
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
            webcam = new tmImage.Webcam(350, 350, flip); // width, height, flip
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
                
                if (prediction[i].probability.toFixed(2) >= 0.9){
                    if(prediction[i].className === this.behavior[0]){
                        document.getElementById('studying').style.display = 'inline'
                        document.getElementById('phone').style.display = 'none'
                        document.getElementById('snoozing').style.display = 'none'
                        document.getElementById('webcam-container').classList.remove('blink')
                    }
                    else if(prediction[i].className === this.behavior[1]){
                        document.getElementById('studying').style.display = 'none'
                        document.getElementById('phone').style.display = 'inline'
                        document.getElementById('snoozing').style.display = 'none'
                        document.getElementById('webcam-container').classList.add('blink')
                    }
                    else{
                        document.getElementById('studying').style.display = 'none'
                        document.getElementById('phone').style.display = 'none'
                        document.getElementById('snoozing').style.display = 'inline'
                        document.getElementById('webcam-container').classList.add('blink')
                    }
                }
            }
        }
    }
}

// More API functions here:
// https://github.com/googlecreativelab/teachablemachine-community/tree/master/libraries/image



// Load the image model and setup the webcam

</script>

<style>
    * {
        text-align: center; 
    }
    .blink{
        animation: blink 1s linear infinite;
    }

    @keyframes blink{
        0%{opacity: 1;}
        50%{opacity: 0.5;}
        100%{opacity: 1;}
    }


    #label-container{
        position: absolute;
        bottom: 5%;
        left: 50%;
        transform: translate(-50%, 0);
    }
</style>
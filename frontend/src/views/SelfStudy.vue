<template>
    <div>
        <!-- <input type="file" id='upload-model'>
        <input type="file" id='upload-weights'>
        <input type="file" id='upload-metadata'> -->
        <Blink>can you see me?</Blink>
        <div id='webcam-container' style="height: auto; width: 100%; border:5px solid gold;"></div>
        <div id="label-container"></div>
        
    </div>
</template>

<script>
// the link to your model provided by Teachable Machine export panel
import * as tf from'@tensorflow/tfjs';  // eslint-disable-line no-unused-vars
import * as tmImage from '@teachablemachine/image';
//import 'https://cdn.jsdelivr.net/npm/@tensorflow/tfjs@1.3.1/dist/tf.min.js'
//import * as tmImage from 'https://cdn.jsdelivr.net/npm/@teachablemachine/image@0.8.3/dist/teachablemachine-image.min.js';

let model, webcam, labelContainer, maxPredictions;

export default {
    data() {
        return{
        }
    },

    async mounted() {

        //let externalScript = document.createElement('script')
        //externalScript.setAttribute('src', 'https://cdn.jsdelivr.net/npm/@tensorflow/tfjs@1.3.1/dist/tf.min.js')
        
        //document.body.appendChild(externalScript)

        //let externalScript2 = document.createElement('script')
        //externalScript2.setAttribute('src', 'https://cdn.jsdelivr.net/npm/@teachablemachine/image@0.8/dist/teachablemachine-image.min.js')
        //document.body.appendChild(externalScript2)

        const URL = 'https://teachablemachine.withgoogle.com/models/uK53pgina/'
        //const URL = '../assets/my_model/';
        const modelURL = URL + 'model.json';
        const metadataURL = URL + 'metadata.json';
        //const weightsURL = URL + 'weights.bin';
        const flip = true; // whether to flip the webcam

        // load the model and metadata
        // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
        // or files from your local hard drive
        // Note: the pose library adds "tmImage" object to your window (window.tmImage)

        //const uploadModel = document.getElementById('upload-model');
        //const uploadWeights = document.getElementById('upload-weights');
        //const uploadMetadata = document.getElementById('upload-metadata');
       
        model = await tmImage.load(modelURL,metadataURL);
        maxPredictions = model.getTotalClasses();
        

        // Convenience function to setup a webcam
        webcam = new tmImage.Webcam(500, 500, flip); // width, height, flip
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
    },

    methods: {
        async loop() {
            webcam.update(); // update the webcam frame
            await this.predict();
            window.requestAnimationFrame(this.loop);
        },

        // run the webcam image through the image model
        async predict() {
            // predict can take in an image, video or canvas html element
            const prediction = await model.predict(webcam.canvas);
            for (let i = 0; i < maxPredictions; i++) {
                const classPrediction =
                    prediction[i].className + ': ' + prediction[i].probability.toFixed(2);
                labelContainer.childNodes[i].innerHTML = classPrediction;
            }
        }
    }
}

// More API functions here:
// https://github.com/googlecreativelab/teachablemachine-community/tree/master/libraries/image



// Load the image model and setup the webcam

</script>

<style>
    blink{
        animation: blink 0.5s linear infinite;
    }

    @keyframes blink{
        0%{opacity: 1;}
        50%{opacity: 0;}
        100%{opacity: 1;}
    }

</style>


        
        
 
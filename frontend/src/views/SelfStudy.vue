<template>
    <div>
        <button type="button" @click="init()">Start</button>
        <div id='webcam-container'></div>
        <div id="label-container"></div>
        
    </div>
</template>

<script>
// the link to your model provided by Teachable Machine export panel
import '@tensorflow/tfjs';
import * as tmImage from '@teachablemachine/image';
let model, webcam, labelContainer, maxPredictions;

export default {

    name : 'AI',
    data() {
        return{}
    },
    method: {
        async init() {
            
            const URL = '../assets/my_model/';
            const modelURL = URL + 'model.json';
            const metadataURL = URL + 'metadata.json';

            // load the model and metadata
            // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
            // or files from your local hard drive
            // Note: the pose library adds "tmImage" object to your window (window.tmImage)
            model = await tmImage.load(modelURL, metadataURL);
            maxPredictions = model.getTotalClasses();
            

            // Convenience function to setup a webcam
            const flip = true; // whether to flip the webcam
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

</style>


        
        
 
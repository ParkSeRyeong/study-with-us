pipeline {
    agent none

    stages {
        stage('example build') {
            steps {
                echo 'jenkins sample build ! ${AUTHOR}'
            }
        }
    }
    post {
        always {
            echo 'post process !'
        }
    }
}
pipeline {
    agent any
    stages {
        stage('start') {
            steps {
                slackSend (
                    channel: '#gascharge',
                    color: '#FFFF00',
                    message: "Started: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]"
                )
            }
        }
        stage('Checkout') {
            steps {
                echo 'test1'
                git branch: 'main',
                    credentialsId: 'gascharge-app-reservation-access-token',
                    url: 'https://github.com/illinia/gascharge-app-reservation.git'
            }
        }
    }
    post {
        success {
            slackSend (
                channel: '#gascharge',
                color: '#00FF00',
                message: """
                    success
                """
            )
        }
        failure {
            slackSend (
                channel: '#gascharge',
                color: '#FF0000',
                message: "Fail"
            )
        }
    }
}
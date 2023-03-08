pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: 'gascharge-app-reservation-access-token',
                    url: 'https://github.com/illinia/gascharge-app-reservation.git'
            }
        }
    }
}
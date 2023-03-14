pipeline {
    agent any
    tools {
        gradle 'gradle'
    }
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
        stage('Gradle project build') {
            steps {
                sh '''
                    echo 'Gradle 프로젝트 빌드 시작'
                    ./gradlew clean bootJar -Pdev
                    cp /var/jenkins_home/application-oauth.yml /var/jenkins_home/workspace/gascharge-app-reservation/application-oauth.yml
                '''
            }
        }
        stage('ssh publisher') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'gascharge-app-ssh-server',
                            transfers: [
                                sshTransfer(
                                    remoteDirectory: 'k8s/gascharge-app-reservation',
                                    removePrefix: 'build/libs',
                                    sourceFiles: 'build/libs/gascharge-app-reservation-0.0.1-SNAPSHOT.jar'
                                ),
                                sshTransfer(
                                    remoteDirectory: 'k8s/gascharge-app-reservation',
                                    sourceFiles: 'application-oauth.yml'
                                ),
                                sshTransfer(
                                    remoteDirectory: 'k8s/gascharge-app-reservation',
                                    sourceFiles: 'Dockerfile'
                                ),
                                sshTransfer(
                                    execCommand: '/usr/local/bin/docker stop gascharge-app-reservation-container'
                                ),
                                sshTransfer(
                                    execCommand: '/usr/local/bin/docker rm gascharge-app-reservation-container'
                                ),
                                sshTransfer(
                                    execCommand: '/usr/local/bin/docker rmi gascharge-app-reservation'
                                ),
                                sshTransfer(
                                    execCommand: 'nohup /usr/local/bin/docker build -t gascharge-app-reservation k8s/gascharge-app-reservation/ > nohup-app-reservation-build.out 2>&1 &'
                                ),
                                sshTransfer(
                                    execCommand: 'nohup /usr/local/bin/docker run --name gascharge-app-reservation-container -it -d -p 8400:8400 --privileged --cgroupns=host -v /sys/fs/cgroup:/sys/fs/cgroup:rw gascharge-app-reservation /usr/sbin/init > nohup-app-reservation-run.out 2>&1 &'
                                )
                            ]
                        )
                    ]
                )
            }
        }
    }
    post {
        success {
            slackSend (
                channel: '#gascharge',
                color: '#00FF00',
                message: """
                    Success ${env.JOB_NAME} [${env.BUILD_NUMBER}
                """
            )
        }
        failure {
            slackSend (
                channel: '#gascharge',
                color: '#FF0000',
                message: """
                    Failed ${env.JOB_NAME} [${env.BUILD_NUMBER}
                """
            )
        }
        unstable {
            slackSend (
                channel: '#gascharge',
                color: '#FF0000',
                message: """
                    Unstable ${env.JOB_NAME} [${env.BUILD_NUMBER}
                """
            )
        }
    }
}
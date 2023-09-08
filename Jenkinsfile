pipeline {
    agent any

    stages {
        stage("checkout") {
            steps {
                bat "dir"
                git branch:'main', url: 'https://github.com/DembinskiD/ufcapi'
                bat "dir"
            }
        }
        stage("build") {
            steps {
                bat '.\\mvnw package'
            }
        }
        stage("post-build actions") {
            steps {
                archiveArtifacts '**/target/*.jar'
                junit '**/target/surefire-reports/TEST*.xml'
                jacoco()
            }
        }
    }

    post {
        always{
            emailext body: "${env.BUILD_URL}\n${currentBuild.absoluteUrl}",
            to: 'dembik@gmail.com',
            recipientProviders: [previous()],
            subject: "${currentBuild.currentResult}: Job ${env.JOB_NAME} [${env.BUILD_NUMBER}]"
        }
    }
}

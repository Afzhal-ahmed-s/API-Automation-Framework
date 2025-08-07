pipeline {
  agent any

  environment {
    IMAGE_NAME = 'afzhalahmeds/api-tests'
    TAG = 'latest'
  }

  stages {
    stage('Checkout') {
      steps {
        git url: 'https://github.com/Afzhal-ahmed-s/API-Automation-Framework'
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t $IMAGE_NAME:$TAG .'
      }
    }

    stage('Push to DockerHub') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          sh '''
            echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
            docker push $IMAGE_NAME:$TAG
          '''
        }
      }
    }

    stage('Run Tests in Container') {
      steps {
        sh '''
          docker run --rm \
            -v $(pwd)/logs:/app/logs \
            -v $(pwd)/reports:/app/reports \
            $IMAGE_NAME:$TAG
        '''
      }
    }

    stage('Publish Extent Report') {
      steps {
        script {
          def reportFile = sh(script: "ls reports/Test_Report_*.html | tail -n 1", returnStdout: true).trim()
          def reportDir = "reports"
          def reportName = 'Extent Report'

          publishHTML(target: [
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: reportDir,
            reportFiles: reportFile.replace("${reportDir}/", ""),
            reportName: reportName
          ])
        }
      }
    }

    stage('Archive Logs') {
      steps {
        archiveArtifacts artifacts: 'logs/**/*.log', allowEmptyArchive: true
      }
    }
  }
}

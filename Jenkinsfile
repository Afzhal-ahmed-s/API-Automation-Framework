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
            docker push afzhalahmeds/api-tests:latest
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

    stage('Detect Latest Report') {
      steps {
        script {
          def reportFile = sh(script: "ls -t reports/Test_Report_*.html | head -n 1", returnStdout: true).trim()
          env.LATEST_REPORT = reportFile
        }
      }
    }

    stage('Publish HTML Report') {
      steps {
        publishHTML([
          allowMissing: false,
          alwaysLinkToLastBuild: true,
          keepAll: true,
          reportDir: 'reports',
          reportFiles: "${env.LATEST_REPORT}",
          reportName: 'Extent Report'
        ])
      }
    }

    stage('Archive Logs') {
      steps {
        archiveArtifacts artifacts: 'logs/*.log', allowEmptyArchive: true
      }
    }
  }
}

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
          mkdir -p logs reports
          docker run --rm \
            -v "$(pwd)/logs:/app/logs" \
            -v "$(pwd)/reports:/app/reports" \
            $IMAGE_NAME:$TAG
        '''
      }
    }

    stage('Save Jenkins Console Log') {
      steps {
        script {
          def timestamp = new Date().format("yyyy-MM-dd_HH-mm-ss")
          def logFileName = "logs/jenkins-console-${timestamp}.log"
          writeFile file: logFileName, text: currentBuild.rawBuild.getLog().join('\n')
        }
      }
    }

    stage('Archive Reports and Logs') {
      steps {
        archiveArtifacts artifacts: 'logs/**', allowEmptyArchive: false
        archiveArtifacts artifacts: 'reports/**', allowEmptyArchive: true
      }
    }
  }
}

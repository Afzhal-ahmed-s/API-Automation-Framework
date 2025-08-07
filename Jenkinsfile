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

    stage('Archive Reports and Logs') {
      steps {
        archiveArtifacts artifacts: 'reports/**', allowEmptyArchive: true
        archiveArtifacts artifacts: 'logs/**', allowEmptyArchive: true
      }
    }
  }
}

pipeline {
  agent any

  environment {
    IMAGE_NAME = 'afzhalahmeds/api-tests'
    TAG = 'latest'
    TIMESTAMP = "${new Date().format('yyyy-MM-dd_HH-mm-ss')}"
    BUILD_LOG_DIR = "logs/build-${BUILD_NUMBER}"
    BUILD_REPORT_DIR = "reports/build-${BUILD_NUMBER}"
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

    stage('Organize Logs and Reports') {
      steps {
        sh '''
          mkdir -p logs/build-${BUILD_NUMBER} reports/build-${BUILD_NUMBER}
          mv reports/Test_Report_*.html reports/build-${BUILD_NUMBER}/ || echo "No report found"
          mv logs/automation-*.log logs/build-${BUILD_NUMBER}/ 2>/dev/null || true
          cp reports/build-${BUILD_NUMBER}/Test_Report_*.html reports/latest.html || echo "Latest report copy failed"
        '''
      }
    }

    stage('Publish Report') {
      steps {
        publishHTML([
          allowMissing: false,
          alwaysLinkToLastBuild: true,
          keepAll: true,
          reportDir: 'reports',
          reportFiles: 'latest.html',
          reportName: 'Extent Report'
        ])
      }
    }
  }
}

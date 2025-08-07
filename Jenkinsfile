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
          sh """
            echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
            docker push $IMAGE_NAME:$TAG
          """
        }
      }
    }

    stage('Run Tests in Container') {
      steps {
        sh """
          docker run --rm \
            -v $(pwd)/logs:/app/logs \
            -v $(pwd)/reports:/app/reports \
            $IMAGE_NAME:$TAG
        """
      }
    }

    stage('Organize Logs and Reports') {
      steps {
        script {
          sh """
            mkdir -p ${BUILD_REPORT_DIR} ${BUILD_LOG_DIR}

            # Move report file(s)
            mv reports/Test_Report_*.html ${BUILD_REPORT_DIR}/ || echo "No report found"

            # Move log file(s) if any
            mv logs/automation-*.log ${BUILD_LOG_DIR}/ 2>/dev/null || true

            # Copy latest for Jenkins HTML Publisher
            cp ${BUILD_REPORT_DIR}/Test_Report_*.html reports/latest.html || echo "Latest report copy failed"
          """
        }
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

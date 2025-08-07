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

          for file in logs/build-${BUILD_NUMBER}/automation-*.log; do
            name=$(basename "$file" .log)
            echo "<html><body><pre>$(cat \"$file\")</pre></body></html>" > logs/build-${BUILD_NUMBER}/${name}.html
          done

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
          reportDir: "reports/build-${BUILD_NUMBER}",
          reportFiles: 'Test_Report_*.html',
          reportName: "Extent Report"
        ])
      }
    }

    stage('Publish Logs') {
      steps {
        publishHTML([
          allowMissing: true,
          alwaysLinkToLastBuild: true,
          keepAll: true,
          reportDir: "logs/build-${BUILD_NUMBER}",
          reportFiles: '*.html',
          reportName: "Execution Logs"
        ])
      }
    }

    stage('Archive Logs') {
      steps {
        archiveArtifacts artifacts: "logs/build-${BUILD_NUMBER}/automation-*.log", allowEmptyArchive: true
      }
    }
  }
}

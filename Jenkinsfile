pipeline {
  agent any

  environment {
    IMAGE_NAME = 'afzhalahmeds/api-tests'
    TAG = 'latest'
    TIME = "${new Date().format('yyyy-MM-dd_HH-mm-ss')}"
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
          rm -rf logs reports
          mkdir -p logs reports

          docker run --rm \
            -v "$PWD/logs:/app/logs" \
            -v "$PWD/reports:/app/reports" \
            $IMAGE_NAME:$TAG
        '''
      }
    }

    stage('Archive Artifacts') {
      steps {
        archiveArtifacts artifacts: 'logs/**', allowEmptyArchive: false
        archiveArtifacts artifacts: 'reports/**', allowEmptyArchive: false
      }
    }

    stage('Publish Extent Report') {
      steps {
        publishHTML([
          allowMissing: false,
          alwaysLinkToLastBuild: true,
          keepAll: true,
          reportDir: 'reports',
          reportFiles: 'Test_Report_*.html',
          reportName: 'Extent Report'
        ])
      }
    }

    stage('Publish Logs Panel') {
      steps {
        script {
          // Dynamically find the latest log file
          def logFile = sh(script: "ls -1 logs/automation_*.log | tail -1", returnStdout: true).trim()

          sh """
            mkdir -p logs_html
            cp ${logFile} logs_html/automation.html
          """

          publishHTML([
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: 'logs_html',
            reportFiles: 'automation.html',
            reportName: 'Logs'
          ])
        }
      }
    }
  }
}

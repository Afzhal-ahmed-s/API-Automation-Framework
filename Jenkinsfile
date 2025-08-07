pipeline {
  agent any

  triggers {
    // Runs once every day at 2 AM IST (which is 20:30 UTC the previous day)
    cron('H 20 * * *')
  }

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

  post {
    success {
      emailext (
        to: 'sdet.mark1@gmail.com',
        subject: "✅ Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
        body: """Hi Team,

The scheduled Jenkins build completed successfully.

📝 *Job*: ${env.JOB_NAME}
🔢 *Build*: #${env.BUILD_NUMBER}
📊 *Report*: ${env.BUILD_URL}report/Extent_20Report/

Regards,
Jenkins CI
"""
      )
    }

    failure {
      emailext (
        to: 'your_email@example.com',
        subject: "❌ Build FAILED: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
        body: """Hi Team,

The scheduled Jenkins build failed.

📝 *Job*: ${env.JOB_NAME}
🔢 *Build*: #${env.BUILD_NUMBER}
📎 *Logs/Report*: ${env.BUILD_URL}

Please investigate.

Regards,
Jenkins CI
"""
      )
    }
  }
}

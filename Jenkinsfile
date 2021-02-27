pipeline {
    agent {
        label 'master'
    }

    environment {
        cred_id = '64129bbc-8486-40e0-8e13-3bdc2172052a'
    }

    parameters {
        string(name: 'branch', defaultValue: 'master', description: 'Git branch')
    }

    stages {
        stage('检出代码') {
            steps {
                git credentialsId: cred_id, url: 'https://github.com/jinglv/api-object-auto.git', branch: "$params.branch"
            }
        }

        stage('Maven执行测试') {
            steps {
                sh '''
                    . ~/.bash_profile

                    cd ${WORKSPACE}
                    mvn clean install
                '''
            }
        }
    }

    post {
        always {
            script {
                println "Do some actions when always need."
            }
        }
        failure {
            script {
                println "Do some actions when build failed."
            }
        }
        success {
            script {
                println "Do some actions when build success."
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
            }
        }
    }
}
pipeline {
    agent {
        label 'master'
    }

    environment {
        cred_id = 'd7f10e03-1795-4e5f-805a-65c5487f4dfc'
    }

    parameters {
        string(name: 'branch', defaultValue: 'master', description: 'Git branch')
    }

    stages {
        stage('检出代码') {
            steps {
                git credentialsId: cred_id, url: 'https://gitee.com/jeanlv/api-object-auto.git', branch: "$params.branch"
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
                allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
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
            }
        }
    }
}
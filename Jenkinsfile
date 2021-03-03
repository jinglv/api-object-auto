pipeline {
    agent {
        label 'master'
    }

    environment {
        cred_id = 'b4d58207-0fa3-43f5-bf1d-c635025a7684'
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

        stage('JaCOCO Dump数据') {
            steps {
                sh '''
                    java -jar /usr/local/jacoco/lib/jacococli.jar dump --address 127.0.0.1 --port 6301 --destfile /root/jacoco-project/jacoco_docker.exec
                '''
            }
        }

        stage('JaCOCO测试报告生成') {
            steps {
                sh '''
                    cd ..
                    cd SpringBoot-Restful-Api-Docker-Pipeline
                    java -jar /usr/local/jacoco/lib/jacococli.jar report /root/jacoco-project/jacoco_docker.exec --classfiles=./target/classes --sourcefiles=./src/main/java --html /root/jacoco-project/report/ --xml /root/jacoco-project/report/jacoco.xml
                '''
            }
        }

        stage('SonarQube分析') {
            steps {
                sh '''
                    sonar-scanner -Dsonar.coverage.jacoco.xmlReportPaths=/root/jacoco-project/report/jacoco.xml -Dsonar.projectKey=spring-boot-restful-api-test -Dsonar.projectName=spring-boot-restful-api-test -Dsonar.language=java -Dsonar.sourceEncoding=UTF-8 -Dsonar.core.codeCoveragePlugin=jacoco -Dsonar.sources=./src/main/java -Dsonar.java.binaries=./target
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
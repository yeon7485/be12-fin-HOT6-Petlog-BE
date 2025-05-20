pipeline {
    agent any

    environment {
        IMAGE_NAME = 'petlog/frontend'
        IMAGE_TAG = "${BUILD_NUMBER}"
        TAG = "0.1.${BUILD_NUMBER}"
        K8S_MASTER = "test@192.0.60.11"
    }

    stages {
        stage('Git Clone') {
            steps {
                echo "Clone Repository"
                git branch: 'main', url: 'https://github.com/beyond-sw-camp/be12-fin-HOT6-Petlog-FE'
            }
        }

        stage('Build Frontend') {
            steps {
                echo "Install dependencies and build"
                sh '''
                    rm -rf node_modules package-lock.json dist
                    npm install
                    npm install @rollup/rollup-linux-x64-gnu --save-optional
                    npx vite build
                '''
            }
        }

        stage('Debug Build Output') {
            steps {
                echo "Checking dist directory..."
                sh 'ls -al dist'
            }
        }

        stage('Docker Build & Push') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId: 'DOCKER_USER', usernameVariable: 'DOCKER_ID', passwordVariable: 'DOCKER_PW')
                ]) {
                    echo "Docker Build & Push"
                    sh """
                        docker build -t ${IMAGE_NAME}:${TAG} .
                        docker login -u $DOCKER_ID -p $DOCKER_PW
                        docker push ${IMAGE_NAME}:${TAG}
                    """
                }
            }
        }

        stage('Generate Canary YAMLs') {
            steps {
                script {
                    def buildId = "${BUILD_NUMBER}"
                    def imageTag = "0.1.${buildId}"
                    def resourceBase = "frontend-${buildId}"
                    sh """
                        echo "🧼 Cleaning old YAMLs"
                        rm -rf k8s/generated/*
                        echo \"Generating YAMLs for ${resourceBase}\"
                        mkdir -p k8s/generated
                        cp k8s/template/*.yml k8s/generated/

                        for file in k8s/generated/*.yml; do
                            sed -i "s|__NAME__|${resourceBase}|g" \$file
                            sed -i "s|__IMAGE_TAG__|${imageTag}|g" \$file
                        done
                    """
                }
            }
        }

        stage('Deploy Canary Resources') {
            steps {
                script {
                    def remote_dir = "/home/test/k8s/frontend"
                    sh """
                        echo \"Sending resources to ${K8S_MASTER}\"
                        ssh -o StrictHostKeyChecking=no ${K8S_MASTER} "mkdir -p ${remote_dir}"
                        ssh -o StrictHostKeyChecking=no ${K8S_MASTER} "rm -rf ${remote_dir}/*"
                        scp -o StrictHostKeyChecking=no k8s/generated/*.yml ${K8S_MASTER}:${remote_dir}/

                        echo \"Applying deployment and service\"
                        ssh -o StrictHostKeyChecking=no ${K8S_MASTER} "kubectl apply -f ${remote_dir}/"
                    """
                }
            }
        }

        stage('Deploy Canary Ingress (10%)') {
            steps {
                script {
                    def resourceBase = "frontend-${BUILD_NUMBER}"
                    def ingressTemplate = "k8s/template/frontend-ingress.yml"
                    def generatedIngress = "k8s/generated/${resourceBase}-ingress.yml"

                    sh """
                        cp ${ingressTemplate} ${generatedIngress}
                        sed -i "s|__NAME__|${resourceBase}|g" ${generatedIngress}

                        echo \"Sending ingress file to ${K8S_MASTER}\"
                        scp -o StrictHostKeyChecking=no ${generatedIngress} ${K8S_MASTER}:/home/test/k8s/frontend/

                        echo \"Applying canary ingress\"
                        ssh -o StrictHostKeyChecking=no ${K8S_MASTER} "kubectl apply -f /home/test/k8s/frontend/${resourceBase}-ingress.yml"
                    """
                }
            }
        }
    }
}

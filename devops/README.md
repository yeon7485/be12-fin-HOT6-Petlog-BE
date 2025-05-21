<br>
<img src="https://capsule-render.vercel.app/api?type=venom&height=250&color=bb8378&fontColor=FFFFFF&text=🐾펫로그&fontSize=70&fontAlignY=30&animation=fadeIn&rotate=0&desc=반려동물%20일정%20관리%20·%20커뮤니티%20플랫폼&descSize=25&reversal=false" style="width: 120%;">

<br>

## 🕵️ 팀원 소개

> **[한화시스템 BEYOND SW캠프 12기] Final Project**  
> Team Hot6🔥

<div align="center">

|          <img src="../images/냐옹이.jpg" width="100" />          |        <img src="../images/로사.jpg" width="100" />         |       <img src="../images/로이.jpg" width="100" />        |      <img src="../images/마자용.jpg" width="100" />       |
|:-------------------------------------------------------------:|:---------------------------------------------------------:|:-------------------------------------------------------:|:------------------------------------------------------:|
| 🐳 **박동휘**<br/>[@parkdonghwi-git](https://github.com/donghwi) | 🐢 **유승호**<br/>[@seungho99](https://github.com/seungho99) | 🧶 **박세연**<br/>[@yeon7485](https://github.com/yeon7485) | ⚽ **안규호**<br/>[@Ahngyuho](https://github.com/Ahngyuho) |

</div>

<br>

### 목차

- [🐶 기술 스택](#-기술-스택)
- [🐱 펫로그 데모 사이트 링크](#-펫로그-데모-사이트-바로가기)
- [🐹 펫로그 서비스 소개](#-펫로그-서비스-소개)
- [🐰 시스템 아키텍처](#-시스템-아키텍처)
- [🐦 데브옵스 프로젝트 목표](#-데브옵스-프로젝트-목표)
- [🐟 주요 기능 시연](#-주요-기능-시연)
- [🚀 핵심 로직 상세 설명](#-핵심-로직-상세-설명)
- [🦎 프로젝트 폴더 바로가기](#-프로젝트-폴더-바로가기)

<br>
<br>

## 🐶 기술 스택

### 🎨 Frontend

![Vue.js](https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vue.js&logoColor=4FC08D) <!-- Vue.js -->
![Pinia](https://img.shields.io/badge/Pinia-ffe564?style=for-the-badge&logo=pinia&logoColor=black) <!-- 상태관리 라이브러리 -->
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white)
![Nginx](https://img.shields.io/badge/Nginx-009639?style=for-the-badge&logo=nginx&logoColor=white) <!-- Nginx -->

### 🧠 Backend

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) <!-- 스프링 부트 -->
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) <!-- 인증/인가 -->
![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white) <!-- 클라우드 마이크로서비스 -->

### 🗄 DB

![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white)

### ⚙️ CI/CD

![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white) <!-- 컨테이너화 -->
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white) <!-- 오케스트레이션 -->
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=jenkins&logoColor=white) <!-- CI/CD -->

### 💻 Etc

![Kafka](https://img.shields.io/badge/kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white) <!-- 형상 관리 -->
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white) <!-- GitHub -->
![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)
![Discord](https://img.shields.io/badge/discord-326CE5?style=for-the-badge&logo=discord&logoColor=white)

<br><br>

## 🐱 펫로그 데모 사이트 바로가기

### [펫로그 사이트](https://www.petlog.kro.kr)</a>

<br>

## 🐹 펫로그 서비스 소개
<div align="center">
<img src="../images/logo_white.png" width="50%"/>
</div>

#### 프로젝트 배경
기존에는 프론트엔드와 백엔드 모두 로컬 또는 CI에서 빌드한 결과물을 수동으로 서버에 업로드하고, SSH로 접속하여 직접 실행하거나 재시작하는 방식으로 배포하였다.  
이 과정은 반복적이고 비효율적이며, 작업자에 따라 방식이 달라지거나 실수로 인한 오류가 발생하는 문제가 있었다. 또한, 문제 발생 시 빠르게 롤백하기 어려운 구조였다.  
<br>
이러한 문제를 해결하기 위해 Jenkins 기반의 CI/CD 파이프라인을 도입하였다.  
프론트엔드와 백엔드는 각각 별도의 Jenkinsfile로 구성하여 독립적으로 관리하였으며, 코드 변경 시 Jenkins가 자동으로 Docker 이미지를 빌드하고 Kubernetes 클러스터에 배포하도록 전체 과정을 자동화하였다. 관련된 Jenkinsfile과 Kubernetes yml 파일은 프로젝트 내에 포함되어 있다.



#### 적용 효과

- 반복적인 수작업을 제거하여 배포 시간이 단축되고 안정성이 향상되었다.
- Docker와 Kubernetes를 기반으로 환경 간 일관성을 확보하였다.
- Jenkins를 통해 빌드부터 배포까지 자동화된 파이프라인을 구성하였다.
- 배포 설정을 코드로 관리함으로써 버전 관리 및 유지보수가 용이해졌다.

<br>

---
## 🐰 시스템 아키텍처
<img src="../backend/images/시스템아키텍처.png" width="100%"/>

<br>

---
## 🐦 데브옵스 프로젝트 목표
- **`CI/CD 자동화 구축`**<br>
  - Jenkins를 활용하여 프론트엔드와 백엔드에 대해 각각 독립적인 파이프라인 구성
  - 코드 변경 시 Docker 이미지 자동 빌드 및 Kubernetes 클러스터에 자동 배포

- **`무중단 배포 전략 적용`**<br>
  - 프론트엔드 : 카나리 배포 방식 적용
    사용자 반응에 따라 점진적으로 새 버전 트래픽 확장
  - 백엔드 : 블루-그린 배포 방식 적용
    기존 버전과 새 버전을 병렬로 운영하며, 무중단 전환 가능

- **`서비스 안정성 확보`**<br>
  - 배포 중에도 서비스 중단 없이 운영 가능
  - 배포 실패 시 빠른 롤백 가능

---
## 🐟 주요 기능 시연
<details>
<summary>무중단 배포</summary>

![백엔드](/devops/gif/백엔드%20배포.gif)<br>
**🔧 백엔드 무중단 배포**

> 다운 타임이 없는 무중단 배포 방식 중 Blue/Green 배포 방식을 적용하였다.  블루그린 배포 방식은 지속적 배포 방식 중 하나로 신 버전을 배포가 완료되면 구 버전을 바라보던 서비스가 신 버전으로 일제히 전환하도록 하는 방식이다.  
> 신속한 업데이트와 동시에 서버 안정성이 좋기 때문에 이 방식을 선택했다.

<br>

![프론트](/devops/gif/프론트%20엔드%20배포.gif)<br>
**🎨 프론트엔드 무중단 배포**

> 프론트엔드는 사용자 반응을 확인하며 점진적으로 배포하기 위해 카나리 배포 방식을 적용하였다.  
> 초기에는 신버전을 전체 트래픽의 10%에만 적용하여 안정성을 검증한 뒤, 문제가 없을 경우 점차 확장하는 전략을 사용하였다.

</details>

<details>
<summary>CI/CD 파이프라인</summary>

프론트엔드/백엔드 GitHub 저장소의 main 브랜치에 코드가 푸시되면, Webhook 설정을 통해 Jenkins로 이벤트가 전달된다.  
Jenkins는 이를 트리거로 감지하여 지정된 프론트/백파이프라인을 자동으로 실행한다.

</details>


---
## 🚀 핵심 로직 상세 설명
### [📃 프로젝트 Wiki](https://github.com/beyond-sw-camp/be12-fin-HOT6-Petlog-BE/wiki) <br>


---
## 📂 프로젝트 폴더 바로가기
### [📃 frontend](https://github.com/beyond-sw-camp/be12-fin-HOT6-Petlog-FE) <br>
### [📃 Backend](https://github.com/beyond-sw-camp/be12-fin-HOT6-Petlog-BE)
<br>

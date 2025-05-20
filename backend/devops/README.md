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
- 반려동물 케어의 모든 것, 하나의 플랫폼으로 통합된 Petlog 서비스 <br>

반려동물을 돌보는 일은 단순한 애정 표현을 넘어 체계적인 관리와 지속적인 관심이 필요한 일이다.<br>
그러나 현재 제공되는 서비스는 콘텐츠 소비나 단편적인 일정 기록에 그치는 경우가 많고, 이러한 기능들이 하나로 통합된 플랫폼은 매우 드문 실정이다.<br>
우리는 반려동물의 건강과 일상을 기록하고 관리할 수 있는 기능과 더불어, 반려인 간의 소통과 정보 공유를 가능하게 하는 커뮤니티 기능까지 아우르는 Petlog 서비스를 구축하였다.
<br>

- `통합 반려동물 관리` : 단순한 스케줄러가 아닌, 반려동물의 병원 방문, 예방접종, 산책, 미용 등 다양한 일정을 통합적으로 등록 및 조회하며 건강 상태를 지속적으로 관리할 수 있도록 지원한다.<br>
- `일상 기록 및 건강 모니터링` : 하루 단위로 사진, 이상 행동, 식사량 등 다양한 정보를 기록하고, 누적된 데이터를 기반으로 반려동물의 건강 변화를 추적한다.<br>
- `위치 기반 탐색 기능` : 현재 위치를 중심으로 가까운 동물병원, 미용실 등 주요 반려동물 관련 시설을 손쉽게 탐색할 수 있어 유사시 빠르게 대처 가능하다.<br>
- `스마트 알림 시스템` : 등록된 일정을 실시간으로 알림 받아 누락 없이 일정을 챙길 수 있으며, 반복 일정도 편리하게 설정 가능하다.<br>
- `그룹 채팅 및 커뮤니티 게시판` : 반려인들끼리 그룹 채팅을 통해 소통하고, 일정이나 반려동물 정보를 카드 형태로 공유할 수 있으며, 정보 공유/자유/Q&A 게시판을 통해 궁금증을 해소하고 다양한 팁을 나눌 수 있다.<br>
- `AI 기반 Q&A 지원` : Q&A 게시판에서는 질문에 대해 사용자와 **AI**가 함께 답변을 제공함으로써 빠르고 정확한 정보 획득이 가능하다.<br>



#### 세부 기능

- **`📇 반려동물 카드`**<br>
  반려동물 정보를 카드 형식으로 등록 및 관리
- **`📆 일정 관리`**<br>
  단순한 스케줄러가 아닌, 반려동물의 병원 방문, 예방접종, 산책, 미용 등 다양한 일정을 통합적으로 등록 및 조회하며 반려동물 지속적으로 관리할 수 있도록 지원
- **`🧾 일일 기록`**<br>
  반려동물의 건강 상태, 이상 현상, 오늘의 사진 등 일일 단위로 기록 및 관리
- **`📍 위치 기반 탐색`**<br>
  현재 위치를 기준으로 주변 동물병원, 펫샵 등의 반려동물 시설 탐색
- **`🔔 일정 알림`**<br>
  스케줄 알림을 실시간 알림으로 받아볼 수 있는 기능
- **`💬 그룹 채팅 및 이벤트 채팅방`**<br>
  사용자들이 실시간으로 소통하고, 채팅방에서 일정 또는 반려동물 카드도 함께 공유, 제한된 시간과 인원 안에서만 참여할 수 있는 이벤트 전용 채팅방 제공
- **`🪧 커뮤니티 게시판 및 Q&A`**<br>
  반려인들이 자유롭게 소통하고 정보를 나눌 수 있는 자유게시판, 정보게시판, Q&A 게시판을 제공 + Q&A 게시판에서는 사용자와 AI가 함께 질문에 답변하며, 궁금증을 신속하고 정확하게 해결

<br>

---
## 🐰 시스템 아키텍처
<img src="../images/시스템아키텍처.png" width="100%"/>

<br>

---
## 🐦 데브옵스 프로젝트 목표
- `CI/CD 파이프라인 구축` : 코드 변경 → 자동 빌드 → 테스트 → 배포까지 자동화드 변경 → 자동 빌드 → 테스트 → 배포까지 자동화
- `모니터링 및 로깅` : 애플리케이션 상태 모니터링 (Prometheus, Grafana 등)
- `컨테이너화 및 오케스트레이션` : Docker, Kubernetes 등을 통한 배포 및 확장성 확보
- `인프라 관리` : 앤서블, LB, 오토스케일 등
- `보안 및 백업 관리` : 서버 보안, SSL 인증서 관리, 정기 백업 자동화

---
## 🐟 주요 기능 시연
<details>
<summary>무중단 배포</summary>

![백엔드](../gif/백엔드%20배포.gif)<br>
**🔧 백엔드 무중단 배포**



> 다운 타임이 없는 무중단 배포 방식 중 Blue/Green 배포 방식을 적용하였다.  블루그린 배포 방식은 지속적 배포 방식 중 하나로 신 버전을 배포가 완료되면 구 버전을 바라보던 서비스가 신 버전으로 일제히 전환하도록 하는 방식이다.  
> 신속한 업데이트와 동시에 서버 안정성이 좋기 때문에 이 방식을 선택했다.
</details>


<details>
<summary>CI/CD 파이프라인</summary>

![프론트](../gif/프론트%20엔드%20배포.gif)<br>
**🎨 프론트엔드 무중단 배포**
1. github에 be-dev 최신 버전 프로젝트를 머지
2. github는 젠킨스에게 webhook을 통해서 젠킨스에게 이벤트 전달
3. 젠킨스는 파이프라인에 저장된 절차 실행
   a. 젠킨스는 github에서 최신 코드를 clone한다.
   b. backend 혹은 common(repository 관련 모듈) 모듈의 변화를 인식
   c. 단위 테스트 실행 후 전체 테스트 성공 시
   d. 클론된 코드를 기반으로 빌드 작업 수행
   e. 빌드를 통해 도커 이미지 생성 및 도커 허브에 push
4. 쿠버네티스 마스터에 SSH 접속 후 쉘스크립트 실행
   현재 실행 중인 디플로이먼트가 blue라면 green으로 디플로이먼트 생성
   a. 젠킨스에서 도커 허브에 푸쉬한 이미지로 컨테이너 실행
   rollout명령어를 활용하여 생성한 디플로이먼트내의 프로그램이 정상 작동인지 확인
   정상 작동중이라면 서비스를 신버전 디플로이먼트의 파드들과 연결하도록 수정
   구버전 디플로이먼트 삭제
5. webhook을 통해 Discode에게 파이프라인 결과 전달
   a. 젠킨스에 설치한 Discode 플러그인을 통해 파이프라인 제목, 결과, 실행 시간이 담긴 Post를 Discode에 보냄
   b. Discode봇이 데이터를 받아 지정한 Discode 서버에 실행 결과를 전송
</details>

<details>
<summary>모니터링 시스템</summary>

![image](https://private-user-images.githubusercontent.com/64758888/350669513-a197a74e-f6e1-4018-9b9c-9f2764d8208e.gif?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3NDcxMDE2NTEsIm5iZiI6MTc0NzEwMTM1MSwicGF0aCI6Ii82NDc1ODg4OC8zNTA2Njk1MTMtYTE5N2E3NGUtZjZlMS00MDE4LTliOWMtOWYyNzY0ZDgyMDhlLmdpZj9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNTA1MTMlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwNTEzVDAxNTU1MVomWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTY0MWYzNWMzZDYxZTliZjNhY2NlODFjYjU2Mzg0OTA5NDkwYWRiOTZiMmM0NDY5NjU2YjVhYTA2ZTExY2ZkOTAmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0In0.m6F8hhEXCVS_oFAYpS-vsc8SWeR0Pp7rHx0ICsRNt7M)
> 이메일, 비밀번호, 닉네임, 프로필 이미지(필수 X)로 회원 가입을 한다.  
> 이메일, 닉네임은 **중복이 불가능**하다.
</details>

---
## 🚀 핵심 로직 상세 설명
### [📃 프로젝트 Wiki](https://github.com/beyond-sw-camp/be12-fin-HOT6-Petlog-BE/wiki) <br>


---
## 📂 프로젝트 폴더 바로가기
### [📃 frontend](https://github.com/beyond-sw-camp/be12-fin-HOT6-Petlog-FE) <br>
### [📃 Backend](https://github.com/beyond-sw-camp/be12-fin-HOT6-Petlog-BE)
<br>

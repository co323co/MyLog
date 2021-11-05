MyLog : 마이로그
---

# 💡 현재 배포 중인 블로그입니다.

[MyLog](http://mylog.cf)

위 주소로 접속 가능합니다.

서버 부담으로 사용자가 많을 시 서버에 장애가 올 수 있습니다.



---
# 목차

- [개발 도구](#개발-도구)   
- [기능 설명](#기능-설명)
- [프로젝트 명세](#프로젝트-명세)
  - [배포 방법](#배포-방법)
  - [산출물](#산출물) 
    - [ERD](#DB-ERD) 
    - [동작 화면](#동작-화면) 
    - [API 명세서](#API-명세서) 

---

## ⚒ 개발 도구

- **Front-end**
	- Vue.js
	-  Vuetify
- **Back-end**
	- Spring Boot
	- JPA + Quserydsl
	- Spring Rest Docs
	- MySql
- **배포**
	- Docker-Compose
	-  AWS EC2
	-   AWS RDS
---

## 🖥 기능 설명

- **홈 (메인 페이지)**
- **메뉴**
  - 상단 메뉴탭을 통해 조회중인 시리즈를 변경할 수 있습니다.

- **게시글 관리**
    1. 게시글 작성
    2. 게시글 수정 및 이동
    3. 게시글 삭제
    4. 게시글 검색 및 정렬 
    5. 게시글 목록 일괄 삭제
- **댓글 관리**
    1. 댓글 작성
    2. 댓글 수정
    3. 댓글 삭제
       1. 댓글 작성시 password를 입력하며, password가 일치해야만 수정할 수 있습니다.
       2. 댓글 작성시 작성자명을 입력하지 않으면 익명으로 입력됩니다.

**관리자 페이지**

- 시리즈 관리
  - 시리즈는 카테고리와 유사한 개념입니다. 시리즈가 없는 미분류 게시물도 존재할 수 있습니다.
    1. 시리즈 추가
    2. 시리즈 수정
    3. 시리즈 삭제

---

## 🔧 프로젝트 명세

### ️ 배포 방법

### 환경

- 사용 버전	
  - Docker version 20.10.10
  - docker-compose version 1.27.2
  - JDK 11

### 추가 설정 및 파일

1. 실행 쉘 스크립트 (Linux 기반)	
   1. ```git clone https://github.com/co323co/MyLog.git```
   
   2. ```vim run.sh``` 후 아래 내용을 작성합니다. (clone 후 디렉토이를 옮기지 말고 바로 작성해주세요)
      
        ```sh
        cd MyLog
        git pull origin master
        cd backend
        sudo ./gradlew build
        cd ..
        docker-compose down --rmi all
        docker-compose up -d
   
2. application.yml, appication_test.yml

   1. /MyLog/backend/src/main/resources 으로 이동합니다.
   2. 아래와 같은 내용으로 resources 안에 application.yml와 application_test.yml를 생성합니다.

        ```yaml
         spring:
           datasource:
             hikari:
               driver-class-name: com.mysql.cj.jdbc.Driver
               jdbc-url: {{ your database url }}
               username: {{ your database username }}
               password: {{ your database password }}
        
           jpa:
             properties:
               hibernate:
                 show_sql: true
                 format_sql: true
                 use_sql_comments: true
                 hbm2ddl.auto: update
        
         server:
           servlet:
             context-path: "/api"
           port : 8081
        ```
   
3. 배포

   1. run.sh가 있는 경로로 이동합니다.
   2. ```./run.sh```

---

## 🎞 산출물

### DB ERD

   ![ERD](https://user-images.githubusercontent.com/56910798/140428422-55179e9b-b298-4431-96fc-05992654339f.png)

### 동작 화면
#### 홈
![메인](https://user-images.githubusercontent.com/56910798/140431638-b369cdd2-66f8-44b7-9482-dc97533a3d17.png)
#### 좌측 서랍
![좌측 서랍](https://user-images.githubusercontent.com/56910798/140431641-07a46a77-6a4a-446e-bd68-58f17716a174.png)
#### 게시판, 시리즈(상단 메뉴탭)
![게시판(시리즈)](https://user-images.githubusercontent.com/56910798/140431644-969ca9b0-d7ba-46a3-afc5-6f10deb1f44c.png)
#### 글 수정
![글 수정](https://user-images.githubusercontent.com/56910798/140431623-e633c4c6-218b-46fb-8219-9cf1abf1d0db.png)
#### 글 수정 -> 취소
![글 수정-취소](https://user-images.githubusercontent.com/56910798/140431629-dc749dd1-87f8-49dc-a43e-a99092a9acf5.png)
#### 글 작성
![글 작성](https://user-images.githubusercontent.com/56910798/140431630-468f029a-d219-4213-a9ef-72d609dc08e3.png)
#### 글 조회 -> 삭제
![글 조회-삭제](https://user-images.githubusercontent.com/56910798/140431632-37274774-39aa-4976-b7a5-c0d0ec2a2291.png)
#### 댓글 조회, 작성
![댓글](https://user-images.githubusercontent.com/56910798/140431635-6e8741cb-64ee-4fc7-acf7-989b27a50528.png)
#### 댓글 수정
![댓글-수정](https://user-images.githubusercontent.com/56910798/140431636-fbb32039-f5e0-42ee-aff4-bae4b4b25060.png)
#### 관리자 페이지 (시리즈 관리)
![관리자 페이지](https://user-images.githubusercontent.com/56910798/140431619-6a560867-2ee0-4126-8a1e-d7ed26a7e742.png)

---

### API 명세서

- REST API 규칙을 지키도록 노력했습니다.
- Spring Rest Docs를 통해 테스트를 거쳐 만들었습니다.
- /MyLog/docs 경로에 API 명세서 pdf 및 html이 들어있습니다.

#### 명세서 바로 보기

- [api_document.pdf](https://github.com/co323co/MyLog/files/7479524/api_document.pdf) 

---


![logo.png](logo.png)

## 🎈 프로젝트 이름 : 스터디 위드 미

## 👨‍👩‍👦‍👦  팀원소개

|NAME|ROLE|EMAIL|
|------|---|---|
|👑김호영|Distribution & Front-End|junghoon401@naver.com|
|🧒박세령|Backend & Front-End|sr5871@gmail.com|
|🧑안상훈|AI & Front-End|ahnsang9@gmail.com|
|🧑김상재|Frontend Tech Leader|sangjea5@naver.com|
|🧔이현송|AI & Front-End|lhs7615@naver.com|



## 📖 프로젝트 소개

스터디 위드 미는 순 공부 시간 측정 및 분석 프로그램입니다. 공부를 할때 인공지능으로 순 공부시간과 다른 행동을 한 시간을 사람들에게 보여줌으로써 공부를 할때 도움을 줄 수 있도록 하였습니다. 이외에도 회원들은 자신의 일별, 주별, 월별 공부 시간 분석도 받고 to-do list 도 관리 할 수 있습니다. 이러한 공부 관리 프로그램으로 사용자들이 좀 더 자기만의 공부 흐름과 리듬을 찾을 수 있게 도와 줍니다. 바쁜일상에서 효율적으로 공부하고 싶은 분들 저희 스터디 위드 미와 함께하세요!


## 🚀 배포 플로우

![logo.png](logo.png)



## 💻 기술 스택

### Frontend

- Vue.js  2.6.11
  - 네이티브 앱과 같이 뛰어난 사용성을 제공하는 SPA를 구현하고자 Vue.js를 채택하였습니다.
- Sass (Scss) 4.11.1
  - 스타일링 측면에서의 개발 생산성을 높이기 위해 Sass를 채택하였습니다.

### Backend

- Spring-Boot 2.3.9
- mysql 8.0.22



### AI

##### Teachablemachine with google

1. CNN
Fully Connected Layer1 만으로 구성된 인공 신경망의 입력 데이터는 1차원(배열) 형태로 한정됩니다. 한 장의 컬러 사진은 3차원 데이터입니다. 배치 모드에 사용되는 여러장의 사진은 4차원 데이터입니다. 사진 데이터로 전연결(FC, Fully Connected) 신경망을 학습시켜야 할 경우에, 3차원 사진 데이터를 1차원으로 평면화시켜야 합니다. 사진 데이터를 평면화 시키는 과정에서 공간 정보가 손실될 수밖에 없습니다. 결과적으로 이미지 공간 정보 유실로 인한 정보 부족으로 인공 신경망이 특징을 추출 및 학습이 비효율적이고 정확도를 높이는데 한계가 있습니다. 이미지의 공간 정보를 유지한 상태로 학습이 가능한 모델이 바로 CNN(Convolutional Neural Network)입니다.


### Dev-Ops

- AWS EC2 (Ubuntu 18.0.4)
- Jenkins 2.249.2
  - CI/CD 자동화를 통해 개발 생산성을 높이기 위하여 Jenkins를 도입하였습니다.
- Docker 19.03.13
  - 배포에서의 용이성을 위하여 Docker를 도입하였습니다.
  

## 📜 기획

### [WireFrame](https://www.figma.com/file/2MsgYMOiJ8pfkabvcmoWnX/Study-With-Us-%ED%99%94%EB%A9%B4%EC%84%A4%EA%B3%84?node-id=0%3A1)

### [ERD](https://user-images.githubusercontent.com/18321002/98944352-3dc85b80-2534-11eb-975c-a57c9555baf3.png)




## 📱 페이지 기능 소개





## 🔌 Contributing

### [API 명세서](https://docs.google.com/spreadsheets/d/1ie8E6G0lYDcWueaEh44aiWQ_lkphggbpnxSamp6uFsA/edit#gid=0)






## ⭐  Develop Rules
### branch
```
master -> develop -> FEdevelop -> feat/기능이름
master -> develop -> BEdevelop -> feat/기능이름
```

### branch name
```
ex)

BE_register

FE_login
```

### commit 메시지

```
feat : 새로운 기능에 대한 커밋

fix : 버그 수정에 대한 커밋

build : 빌드 관련 파일 수정에 대한 커밋

chore : 그 외 자잘한 수정에 대한 커밋

ci : CI관련 설정 수정에 대한 커밋

docs : 문서 수정에 대한 커밋

style : 코드 스타일 혹은 포맷 등에 관한 커밋

refactor :  코드 리팩토링에 대한 커밋

test : 테스트 코드 수정에 대한 커밋
```

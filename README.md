![logo.png](logo.png)

## 🎈 프로젝트 이름 : 스터디 위드 미

## 👨‍👩‍👦‍👦  팀원소개

|NAME|ROLE|EMAIL|
|------|---|---|
|👑김호영|Distribution & Front-End||
|🧒박세령|Backend & Front-End||
|🧑안상훈|AI & Front-End||
|🧑김상재|Frontend Tech Leader||
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

##### Face Recognition & Face Classification(얼굴 인식 및 분류)

1. **vgg_face**와 **Keras** 이용한 pre_trained 된 모델을 이용하여 closed set classification로 구성 되어있음
   - 학습된 데이터에 대해서만 분류가 되어 학습시키지 않은 얼굴 모델의 경우 unknown으로 분류가 되지 않음
     - closed set이 아닌 open set classification 방식으로 학습 시키지 않는 모델 식별 필요하여 `파기`
2. **ArcFace**를 이용한 모델 학습으로 학습 되지 않은 부분까지 인지할 수 있음
   - 학습 시켜야 할 모델의 크기가 너무 크고(100GB) 새로운 얼굴이 등록될 때마다 학습이 이루어 져야 하며, 이용자 별로 모델을 따로 가지고 있어야 하여 `파기`
3. **OpenCV Face Recognition**에 있는 얼굴 유사도 측정
   - 이미 잘 알려져있는 방법으로 가볍고 빠르다고 판단하여 `선택`



##### Face Landmark (얼굴 특성 좌표점)

1. **OpenCV**를 이용하여 사용할 수 있는 모델인 mmod_human_face_detector.dat와 shape_predictor_68_face_landmarks.dat 를 이용하여 얼굴 인식 및 Landmark생성
   - 옆모습 및 각도에 따라 인식률이 현저하게 낮음
   - 다양한 각도에 대해서도 인식을 하고 Landmark를 만들어 줄 수 있는 모델의 필요성이 있어 `파기`
2. **3DDFA**라는 Open Source로 기존 학습된 모델을 이용하여 얼굴 인식 및 얼굴 윤곽선의 예측 좌표를 표시
   - Face Swap을 하는데에 있어 보이지 않는 부분의 윤곽선도 나타내어 추가적인 알고리즘을 대입하여 윤곽선을 거르는 작업이 필요하여 `파기`
3. **MTCNN**및 **PFLD**를 이용한 얼굴 인식 및 얼굴 Landmark 검출
   - 2번의 3DDFA와 달리 예측 좌표가 아닌 보이는 Landmark만 표시하여 경계를 구분할 수 있어 `선택`



##### Face Detection (얼굴 감지)

1. **Wider_Face**를 이용하여 얼굴을 감지
   - Pre_Trained된 모델을 이용하여 많거나 작은 얼굴까지 모두 사각형 형태로 감지할 수 있음
   - Face Landmark의 **3DDFA**와 합쳐서 사용할 수 있었지만  **MTCNN**과 **PFLD**를 이용하기로 하여 `파기`



### Dev-Ops

- AWS EC2 (Ubuntu 18.0.4)
- Jenkins 2.249.2
  - CI/CD 자동화를 통해 개발 생산성을 높이기 위하여 Jenkins를 도입하였습니다.
- Docker 19.03.13
  - 배포에서의 용이성을 위하여 Docker를 도입하였습니다.

# 의진이의 미세먼지 측정 어플
![Untitled](https://user-images.githubusercontent.com/93872496/222878428-0f8bfc31-972a-49eb-b280-1b791d0f416d.jpeg)


## Google Play Link
해당 링크에서 앱을 다운받으실 수 있습니다.
[Android Google Play Link](https://play.google.com/store/apps/details?id=com.org.kej.finedust)



## 사용자
- 평소에 우리 동네 미세먼지 수치를 알고 싶은 사람들
- 호흡기 질환이 있어 미세먼지 수치에 예민한 사람



## 기능
### 미세먼지 측정기능
- 현재 위치를 측정하여 가장 가까운 대기 오염 관측소 정보를 받아옵니다.
- 가장 가까운 대기 오염 관측소의 미세먼지 정보를 보여줍니다.



### 위젯 기능
- 미세먼지 수치를 앱을 진입하지 않고도 볼 수 있도록 하는 기능입니다.



## 프로젝트 구조 (Clean Architecture)
<img width="1479" alt="image" src="https://user-images.githubusercontent.com/93872496/222882074-82fa9955-46ce-4c33-90c8-aa953cffdba2.png">



## 📚사용 기술 및 라이브러리

- Clean Architecture, Hilt, LiveData
- Kakao API TM좌표 변환계, Open API Air Korea
- Retrofit, enum class, Coroutine, OkHttp3, SwipeRefreshLayout, State Pattern

## 📱 담당한 기능 (Android)

- 경-위도 형식으로 받아온 위치 정보를 Kakao API를 통해 TM좌표로 전환하는 기능 구현
- 변환한 TM좌표 기준으로 가장 가까운 관측소 정보를 받아오고 해당 관측소의 실시간 대기 오염 정보를 업데이트하는 코드 개발
- enum class를 생성하여 4단계로 내려오는 대기 오염 평균 등급을 상태, 이모티콘, 배경색으로 등급화 하여 타입 변환
- SwipeRefreshLayout을 활용하여 응답 시간 지연, 에러 상황에 측정 서비스 동기화 기능 구현

## ✒ 배운 점

- 클린아키텍쳐 기반의 앱 구조를 설계하고 직접 개발하는 경험이 되었다.
- 첫 앱 출시작으로 앱의 출시 뿐 아니라 지속적인 업데이트가 중요하다는 것을 깨달았다.
- 서버로부터 받아온 JSON 데이터를 처리하기 위해서는 Model의 효율적인 설계가 필요함을 깨달았다.
    - Kotlin Data Class File from Json 이라는 플러그인을 사용하면 Json 응답을 기입만 하더라도 간편하게 Model을 설계할 수 있다.
- MainScope를 통해 간단하게 Coroutine Scope(코루틴이 실행되는 범위)를 사용할 수 있다는 점을 학습 
→ Supervisor job job +  Dispatchers.Main 으로 이루어져있는 구조
- Main 쓰레드는 UI 작업을 위해, IO 쓰레드는 네트워크, 디스크 I/O 실행에 사용에 최적화 되어있다는 점을 학습(Dispatcher는 코루틴을 어떤 쓰레드에서 동작하는지에 대해서 명시)
- Retrofit 사용시 Main, IO 쓰레드가 자동으로 Switching된다는 점을 학습


## 🔔 부족한 점

- 공공데이터 포털에서 제공하는 Air Korea의 API 통신 서버가 자주 불안정하여 ****ERR_CONNECTION_TIMED_OUT**** 에러가 자주 발생한다.
- 직접 구현한 서버가 아닌 Open API를 이용했기 때문에 예외처리만 구현

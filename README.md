# android-ai-sample - version 1.0

**네이버클라우드플랫폼 AI 상품**을 더 쉽게 사용하기 위한, 안드로이드 버전 Sample 소스와 
안드로이드 모바일에서 실행할 수 있는 apk 파일을 제공 합니다.


## API 데모 및 소스

* Clova Speech Synthesis(CSS) : 텍스트를 음성으로 읽어주는 음성 합성 API
* Clova Speech Recognition(CSR) : 사람의 목소리를 텍스트로 바꿔주는 음성 인식 API
* Papago NMT : 인공 신경망기반 기계 번역 API
* Chatbot : 자연어 처리를 위한 Chatbot Custom API

## 관련 API 설명서 및 참조서

* Clova Speech Synthesis(CSS) : [CSS 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/speech/synthesis.html), [API 참조서](https://apidocs.ncloud.com/ko/ai-naver/clova_speech_synthesis/tts/)
* Clova Speech Recognition(CSR) : [Android/iOS SDK 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/speech/recognition-sdk.html), [CSR 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/speech/recognition-api.html), [API 참조서](https://apidocs.ncloud.com/ko/ai-naver/clova_speech_recognition/stt/)
* Papago NMT : [Papago NMT 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/translation/nmt.html), [API 참조서](https://apidocs.ncloud.com/ko/ai-naver/papago_nmt/translation/)
* Chatbot : [Custom API 설명서](http://docs.ncloud.com/ko/chatbot/chatbot-3-7.html), [Custom API 호출 URL 설명서](http://docs.ncloud.com/ko/chatbot/chatbot-2-5.html)

## 시작전 준비사항

* **네이버클라우드플랫폼 계정** : [네이버클라우드플랫폼](https://www.ncloud.com/) 계정 보유 및 회원가입 필요
* **AI·NAVER API Application 등록** ([Application 등록 설명서](https://console.ncloud.com/mc/solution/naverService/application))
  - Application 등록시 Service 선택 : Clova Speech Recognition(CSR), Clova Speech Synthesis(CSS), Papago NMT
  - Web 서비스 URL : http://localhost
  - Android 앱 패키지 이름 : com.example.user.ncpaidemo
  - 데모앱에서 등록가능한 인증키는 하나이므로, 하나의 Application에 CSR, CSS, Papago NMT를 등록해야 함
  <img width="808" alt="application_reg" src="https://user-images.githubusercontent.com/41188783/50975348-af967f00-1530-11e9-8fa8-d1f965e5cf34.png">

* **Chatbot 대화시나리오 구현** ([Chatbot 퀵스타트 설명서](http://docs.ncloud.com/ko/chatbot/chatbot-1-1.html))
  - 대화 시나리오 생성 및 학습 : 도메인 생성 > 대화 시나리오 생성 > 대화모델 빌드 > 테스트
  - 채널 연동 : 도메인 > 빌더 실행 > 챗봇 설정 > 메신저 연동 > Custom 설정([Custom 설정 & API Gateway 설정 설명서](http://docs.ncloud.com/ko/chatbot/chatbot-2-5.html))
  - API Gateway 설정 : API Gateway > My Product 
  - Chatbot 대화시나리오가 없을 경우, 데모앱의 음성 챗봇 기능은 사용하지 못함 (CSS,CSR,Papago NMT 데모용일 경우 설정하지 않아도 됨)

## 데모 앱 설치
* apk 다운로드 폴더 : [android-ai-sample/app/release/](https://github.com/NaverCloudPlatform/android-ai-sample/tree/master/app/release) 
* 안드로이드 지원 버전 : 안드로이드 4.4 킷캣 이상
* 앱 권한 : 앱 설치 후 설정 > 애플리케이션 > 권한 > 마이크, 저장공간 권한 부여(안드로이드 버전마다 다를 수 있음)
<img width="459" alt="default" src="https://user-images.githubusercontent.com/41188783/50977328-65fc6300-1535-11e9-84ef-c1dfe34f3dd3.png">



## 데모

* 인증 설정


## 소스 예제
  - 






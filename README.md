# android-ai-sample - Version 1.0

**네이버클라우드플랫폼 AI 상품**을 더 쉽게 사용하기 위한, 안드로이드 버전 Sample 소스와 
안드로이드 모바일에서 실행할 수 있는 apk 파일을 제공 합니다.


## 제공하는 AI API 데모 및 소스

* Clova Speech Synthesis(CSS) : 텍스트를 음성으로 읽어주는 음성 합성 API
* Clova Speech Recognition(CSR) : 사람의 목소리를 텍스트로 바꿔주는 음성 인식 API
* Papago NMT : 인공 신경망기반 기계 번역 API
* Chatbot : 자연어 처리를 위한 Chatbot Custom API

## API 설명서 및 참조서

* Clova Speech Synthesis(CSS) : [CSS 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/speech/synthesis.html), [API 참조서](https://apidocs.ncloud.com/ko/ai-naver/clova_speech_synthesis/tts/)
* Clova Speech Recognition(CSR) : [Android/iOS SDK 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/speech/recognition-sdk.html), [CSR 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/speech/recognition-api.html), [API 참조서](https://apidocs.ncloud.com/ko/ai-naver/clova_speech_recognition/stt/)
* Papago NMT : [Papago NMT 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/translation/nmt.html), [API 참조서](https://apidocs.ncloud.com/ko/ai-naver/papago_nmt/translation/)
* Chatbot : [Custom API 설명서](http://docs.ncloud.com/ko/chatbot/chatbot-3-7.html), [Custom API 호출 URL 설명서](http://docs.ncloud.com/ko/chatbot/chatbot-2-5.html)

## 시작전 준비사항

* **네이버클라우드플랫폼 계정** : [네이버클라우드플랫폼](https://www.ncloud.com/) 계정 보유 및 회원가입 필요
* **AI·NAVER API Application 등록** : [Application](https://console.ncloud.com/mc/solution/naverService/application) 등록
  1. Application 등록시 Service 선택
     -Clova Speech Recognition(CSR), Clova Speech Synthesis(CSS), Papago NMT
       *데모앱에서 등록가능한 인증키는 하나이므로, 하나의 Application에 위 상품을 전부 등록해야 함.
  2. 서비스 환경 등록
     -Web 서비스 URL : http://localhost
     -Android 앱 패키지 이름 : com.example.user.ncpaidemo
* **Chatbot 구축 **
  1. 대화 시나리오 생성 및 학습
    -도메인 생성 > 대화 시나리오 > 
    [Chatbot 퀵스타트 가이드](http://docs.ncloud.com/ko/chatbot/chatbot-1-1.html)를 




## 데모 실행






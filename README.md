# android-ai-sample - 1.0

**네이버클라우드플랫폼 AI 상품**을 쉽게 사용하기 위한, 안드로이드 버전 Sample 소스와 
안드로이드 모바일에서 실행할 수 있는 apk 파일을 제공합니다.
여러 AI API 사용 방법을 알 수 있고, 데모를 실행할 수 있습니다.

## 사용한 AI API

* Clova Speech Synthesis(CSS) : 텍스트를 음성으로 읽어주는 음성 합성 API
* Clova Premium Voice(CPV) : 사람에 가까운 자연스럽고 깨끗한 합성음을 제공하는 음성 합성 API
* Clova Speech Recognition(CSR) : 사람의 목소리를 텍스트로 바꿔주는 음성 인식 API
* Papago NMT : 인공 신경망기반 기계 번역 API
* Chatbot : 자연어 처리를 위한 Chatbot Custom API
* OCR : 이미지의 글자를 디지털 데이터로 추출하는 API(General)

## 관련 API 설명서 및 참조서

* Clova Speech Synthesis(CSS) : [CSS 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/speech/synthesis.html), [API 참조서](https://apidocs.ncloud.com/ko/ai-naver/clova_speech_synthesis/tts/)
* Clova Premium Voice(CPV) : [CSS 설명서](https://docs.ncloud.com/ko/naveropenapi_v3/speech/clova_premium_voice.html), [API 참조서](https://apidocs.ncloud.com/ko/ai-naver/clova_premium_voice/)
* Clova Speech Recognition(CSR) : [Android/iOS SDK 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/speech/recognition-sdk.html), [CSR 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/speech/recognition-api.html), [API 참조서](https://apidocs.ncloud.com/ko/ai-naver/clova_speech_recognition/stt/)
* Papago NMT : [Papago NMT 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/translation/nmt.html), [API 참조서](https://apidocs.ncloud.com/ko/ai-naver/papago_nmt/translation/)
* Chatbot : [Custom API 설명서](http://docs.ncloud.com/ko/chatbot/chatbot-3-7.html), [Custom API 호출 URL 설명서](http://docs.ncloud.com/ko/chatbot/chatbot-2-5.html)
* OCR : [OCR API 호출 가이드](https://docs.ncloud.com/ko/ocr/ocr-1-4.html), [OCR API 호출 설명서](https://apidocs.ncloud.com/ko/ai-application-service/ocr/ocr/)

## 시작전 준비사항

* **네이버클라우드플랫폼 계정** : [네이버클라우드플랫폼](https://www.ncloud.com/) 계정 보유 및 회원가입 필요
* **AI·NAVER API Application 등록** ([Application 등록 설명서](http://docs.ncloud.com/ko/naveropenapi_v3/application.html))
  - Application 등록시 Service 선택 : Clova Speech Recognition(CSR), Clova Speech Synthesis(CSS), Papago NMT
  - 데모앱에서 등록가능한 인증키는 하나이므로, 하나의 Application에 CSR, CSS, Papago NMT를 등록
  - Web 서비스 URL : http://localhost
  - Android 앱 패키지 이름 : com.example.user.ncpaidemo
  
  <img width="808" alt="application_reg" src="https://user-images.githubusercontent.com/41188783/50975348-af967f00-1530-11e9-8fa8-d1f965e5cf34.png">
  
  <img width="808" alt="application_reg" src="https://user-images.githubusercontent.com/41188783/51020496-10ba6300-15c2-11e9-9393-a3e08f5cea4e.png">
  
* **Chatbot 대화시나리오 구현** ([Chatbot 퀵스타트 설명서](http://docs.ncloud.com/ko/chatbot/chatbot-1-1.html))
  - 대화 시나리오 생성 및 학습 : 도메인 생성 > 대화 시나리오 생성 > 대화모델 빌드 > 테스트
  - 채널 연동 : 도메인 > 빌더 실행 > 챗봇 설정 > 메신저 연동 > Custom 설정([Custom 설정 & API Gateway 설정 설명서](http://docs.ncloud.com/ko/chatbot/chatbot-2-5.html))
  - API Gateway 설정 : API Gateway > My Product 
  - Chatbot 대화시나리오가 없을 경우, 데모앱의 음성 챗봇 기능은 사용하지 못함 (CSS,CSR,Papago NMT 데모용일 경우 설정하지 않아도 됨)
* **OCR 설정** 
  - OCR API는 Template OCR과 General OCR로 나누어 집니다. Template OCR은 정해진 규격 및 영역에서 Template 생성후 변환을 하는 API 이고,
    General OCR 은 전송한 이미지의 텍스트를 전부 추출하는 API 입니다. 
  - 본 샘플 소스에서는 General OCR API 호출하는 방식을 작성 했습니다.

## 데모 앱 설치
* apk 다운로드 폴더 : [android-ai-sample/app/release/](https://github.com/NaverCloudPlatform/android-ai-sample/tree/master/app/release)로 이동후 최신 버전의 apk 파일 다운로드 (NCP-AI-Demo-1.x-yyyymmdd-release.apk)
* 안드로이드 지원 버전 : 안드로이드 4.4 킷캣 이상
* 미디어 볼륨이 켜져 있어야 정상적으로 음성 출력
* 앱 권한 : 앱 설치 후 설정 > 애플리케이션 > 권한 > ***마이크, 저장공간*** 권한 부여(안드로이드 버전마다 다를 수 있음)
<p align="center"><img width="300" alt="default" src="https://user-images.githubusercontent.com/41188783/50977328-65fc6300-1535-11e9-84ef-c1dfe34f3dd3.png"></p>


## 데모

### 인증 설정
* 메뉴 : 우측 상단의 메뉴 선택 후 인증 설정 메뉴 선택

<p align="center"><img width="300" alt="default" src="https://user-images.githubusercontent.com/41188783/51009028-b5707c80-1591-11e9-9aad-556dd29ba4e4.png"></p>

* Application Key 설정
  - 네이버클라우드플랫폼 AI NAVER API > Application에 등록된 Application의 Client ID, Client Secret 복사 후 설정

<p align="center"><img width="600" alt="default" src="https://user-images.githubusercontent.com/41188783/51010046-e05ccf80-1595-11e9-8b16-f7cb39992376.png"></p>

 
* Chatbot 접속 정보 설정
  - Secret Key : 네이버클라우드플랫폼 Chatbot > 도메인 > 빌드관리 > Custom의 Secret Key 복사 후 설정
  - API Gateway URL : 네이버클라우드플랫폼 API Gateway > My Products > APIs > Stages의 Invoke URL 복사 후 설정
 
<p align="center"><img alt="default" src="https://user-images.githubusercontent.com/41188783/51010826-531b7a00-1599-11e9-8ae8-e2e461358764.png"></p>
     
<p align="center"><img width="600" alt="default" src="https://user-images.githubusercontent.com/41188783/51010825-531b7a00-1599-11e9-98ad-48bf7eff68f2.png"></p>

* OCR 접속 정보 설정
  - Secret Key : 네이버클라우드플랫폼 OCR > 도메인 목록 > General TEXT OCR > Secret Key
  - API Gateway URL : 네이버클라우드플랫폼 OCR > 도메인 목록 > General TEXT OCR > APIGW Invoke URL
  
<p align="center"><img alt="default" alt="default" src="https://user-images.githubusercontent.com/41188783/76212576-21a94780-624c-11ea-8f42-710fee53654e.png"></p>

  

### Clova Speech Synthesis(CSS)

* 출력된 음성 언어를 선택 한 후, 텍스트 입력
* 음성 듣기 버튼을 클릭 후 음성으로 출력되는지 확인

<p align="center"><img width="300" alt="default" src="https://user-images.githubusercontent.com/41188783/51013533-5406d880-15a6-11e9-9a5d-bf986642ec6f.png"></p>

### Clova Premium Voice(CPV)
* 출력된 음성 언어를 선택 한 후, 텍스트 입력(현재 nara만 지원)
* 음성 듣기 버튼을 클릭 후 음성으로 출력되는지 확인

<p align="center"><img width="300" alt="default" src="https://user-images.githubusercontent.com/41188783/76212436-ca0adc00-624b-11ea-9dfc-72f39d24b147.png"></p>


### Clova Speech Recognition(CSR)

* 말하기 버튼을 클릭하고 음성으로 입력
* 음성이 텍스트로 출력되는지 확인

<p align="center"><img width="300" alt="default" src="https://user-images.githubusercontent.com/41188783/51013409-94b22200-15a5-11e9-81e4-a17a5fd7839a.png"></p>

### Papago NMT

* 번역 대상 언어를 선택하고, 텍스트 입력
* 번역하기 버튼 클릭 후 번역 결과가 텍스트로 출력되는지 확인

<p align="center"><img width="300" alt="default" src="https://user-images.githubusercontent.com/41188783/51013596-c1b30480-15a6-11e9-9c07-ee345a5b6817.png"></p>


### 음성번역 (CSR + Papago NMT + CSS)

* 번역 대상 언어를 선택하고 말하기 버튼을 클릭 후 음성으로 입력
* 번역된 결과가 음성 및 텍스트로 출력되는지 확인

<p align="center"><img width="300" alt="default" src="https://user-images.githubusercontent.com/41188783/51011382-ae4e6c00-159b-11e9-840f-e83af71d290e.png"></p>


### 음성챗봇 (CSR + Chatbot + CSS)

* 말하기버튼을 클릭하고 Chatbot 대화시나리오에 설정한 질문을 음성으로 입력
* Chatbot 대화시나리오를 통해 학습시킨 답변이 음성 및 텍스트로 출력되는지 확인

<p align="center"><img width="300" alt="default" src="https://user-images.githubusercontent.com/41188783/51011163-c5d92500-159a-11e9-9830-db201b76c8f6.png"></p>

### 이미지 인식후 텍스트 변환(OCR)
* ObjectStorage에 이미지 업로드 후 공개로 설정
* ncp.ai.demo.process.OcrProc.java 소스의 objectStorageURL을 ObjectStorage에 업로드한 URL로 변경 합니다.
* 변환하기 버튼을 클릭 합니다.

<p align="center"><img width="300" alt="default" src="https://user-images.githubusercontent.com/41188783/76211727-397fcc00-624a-11ea-887d-8b69452c24a7.png"></p>

## 주요 소스 구조

    .
    ├── ...
    ├── app                    
    │   ├── release                                          [apk 파일]            
    │   ├── src              
    │       ├── ...          
    │       ├── main/java/com/example/user/ncpaidemo         [View]
    │           ├── AuthActivity.java                        # 인증정보 설정 Activity
    │           ├── BaseActivity.java                        # 공통 메뉴 Activity
    │           ├── CpvActivity.java                         # Clova Premium Voice 데모 Activity
    │           ├── CsrActivity.java                         # Clova Speech Recognition 데모 Activity
    │           ├── CssActivity.java                         # Clova Speech Synthesis 데모 Activity
    │           ├── MainActivity.java                        # 홈 Activity
    │           ├── NmtActivity.java                         # Papago NMT 데모 Activity
    │           ├── NmtActivity2.java                        # 음성번역 데모 Activity (CSS, Papago NMT, CSR)
    │           ├── OCRActivity.java                         # 이미지 텍스트 변환 Activity (OCR)
    │           ├── VoiceChatbotActivity.java                # 음성챗봇 데모 Activity (CSS, Chatbot, CSR)
    │       ├── main/java/com/ncp/ai/demo/process            [REST API, SDK 호출 모듈]
    │           ├── ChatbotProc.java                         # Chatbot Custom API 사용
    │           ├── CsrProc.java                             # CSR Android SDK 사용
    │           ├── CssProc.java                             # CSS REST API 사용
    │           ├── NmtProc.java                             # Papago NMT API 사용
    │   └── ...                 
    └── ...

## API 사용 간단 예제

### Clova Speech Synthesis HTTP Request 예제 ([source](./app/src/main/java/com/ncp/ai/demo/process/CssProc.java))

```java
  String text = URLEncoder.encode(msg, "UTF-8");
  String apiURL = "https://naveropenapi.apigw.ntruss.com/voice/v1/tts";
  URL url = new URL(apiURL);
  HttpURLConnection con = (HttpURLConnection)url.openConnection();
  con.setRequestMethod("POST");
  con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
  con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
  // post request
  String postParams = "speaker="+speaker+"&speed=0&text="+text;
  System.out.println(postParams);
  con.setDoOutput(true);
  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
  wr.writeBytes(postParams);
  wr.flush();
  wr.close();

```

### Chatbot Custom API HTTP Request 예제 ([source](./app/src/main/java/com/ncp/ai/demo/process/ChatbotProc.java))

```java
  URL url = new URL(apiURL);

  String message = getReqMessage(voiceMessage);
  System.out.println("##" + message);

  String encodeBase64String = makeSignature(message, secretKey);

  HttpURLConnection con = (HttpURLConnection)url.openConnection();
  con.setRequestMethod("POST");
  con.setRequestProperty("Content-Type", "application/json;UTF-8");
  con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);

  // post request
  con.setDoOutput(true);
  DataOutputStream wr = new DataOutputStream(con.getOutputStream());
  wr.write(message.getBytes("UTF-8"));
  wr.flush();
  wr.close();
```

### Chatbot Custom API Signature 생성 예제 ([source](./app/src/main/java/com/ncp/ai/demo/process/ChatbotProc.java))
```java
  public static String makeSignature(String message, String secretKey) {

        String encodeBase64String = "";

        try {
            byte[] secrete_key_bytes = secretKey.getBytes("UTF-8");

            SecretKeySpec signingKey = new SecretKeySpec(secrete_key_bytes, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            encodeBase64String = Base64.encodeToString(rawHmac, Base64.NO_WRAP);

            return encodeBase64String;

        } catch (Exception e){
            System.out.println(e);
        }

        return encodeBase64String;

    }

```

### Clova Speech Recognition Android SDK 사용 예제 ([source](./app/src/main/java/com/example/user/ncpaidemo/CsrActivity.java))

```java
    private CsrProc naverRecognizer;
    private TextView txtResult;
    private Button btnStart;
    private String mResult;
    private AudioWriterPCM writer;
    // Handle speech recognition Messages.
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady: // 음성인식 준비 가능
                txtResult.setText("Connected");
                writer = new AudioWriterPCM(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("Test");
                break;
            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;
            case R.id.partialResult:
                mResult = (String) (msg.obj);
                txtResult.setText(mResult);
                break;
            case R.id.finalResult: // 최종 인식 결과
                SpeechRecognitionResult speechRecognitionResult = (SpeechRecognitionResult) msg.obj;
                List<String> results = speechRecognitionResult.getResults();
                StringBuilder strBuf = new StringBuilder();
                for(String result : results) {
                    strBuf.append(result);
                    //strBuf.append("\n");
                    break;
                }
                mResult = strBuf.toString();
                txtResult.setText(mResult);
                break;

```






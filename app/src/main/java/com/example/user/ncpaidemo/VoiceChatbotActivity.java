package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naver.speech.clientapi.SpeechRecognitionResult;
import com.ncp.ai.demo.process.ChatbotProc;
import com.ncp.ai.demo.process.CsrProc;
import com.ncp.ai.demo.process.CssProc;
import com.ncp.ai.utils.AudioWriterPCM;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

public class VoiceChatbotActivity extends BaseActivity {

    private static final String TAG = VoiceChatbotActivity.class.getSimpleName();
    private RecognitionHandler handler;
    private CsrProc naverRecognizer;
    private TextView txtResult;
    private Button btnStart;
    private String mResult;
    private AudioWriterPCM writer;
    private String clientId;
    private String clientSecret;
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
                mResult += mResult;
                txtResult.setText(mResult);
                break;
            case R.id.finalResult: // 최종 인식 결과
                SpeechRecognitionResult speechRecognitionResult1 = (SpeechRecognitionResult) msg.obj;
                List<String> results1 = speechRecognitionResult1.getResults();
                StringBuilder strBuf1 = new StringBuilder();
                for(String result : results1) {
                    strBuf1.append(result);
                    //strBuf.append("\n");
                    break;
                }
                mResult = strBuf1.toString();
                txtResult.setText(mResult);

                requestChatbot();

                break;
            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }
                mResult = "Error code : " + msg.obj.toString();
                txtResult.setText(mResult);
                btnStart.setText(R.string.str_start);
                btnStart.setEnabled(true);
                break;
            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }
                btnStart.setText(R.string.str_start);
                btnStart.setEnabled(true);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_chatbot);
        SharedPreferences sharedPref = getSharedPreferences("PREF", Context.MODE_PRIVATE);

        clientId = sharedPref.getString("application_client_id", "");
        clientSecret = sharedPref.getString("application_client_secret", "");

        txtResult = (TextView) findViewById(R.id.textViewVoiceChatbotResult);
        btnStart = (Button) findViewById(R.id.btn_voice_chatbot1);
        handler = new RecognitionHandler(this);
        //naverRecognizer = new CsrProc(this, handler, clientId);
        naverRecognizer = CsrProc.getCsrProc(this, clientId);
        naverRecognizer.setHandler(handler);
        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!naverRecognizer.getSpeechRecognizer().isRunning()) {

                    mResult = "";
                    txtResult.setText("Connecting...");
                    btnStart.setText(R.string.str_stop);
                    naverRecognizer.recognize();
                } else {
                    Log.d(TAG, "stop and wait Final Result");
                    btnStart.setEnabled(false);
                    naverRecognizer.getSpeechRecognizer().stop();
                }
            }
        });

        Button voiceChatbotReplay;

        voiceChatbotReplay = (Button) findViewById(R.id.btn_voice_chatbot_replay);
        voiceChatbotReplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView txtResult = (TextView) findViewById(R.id.text_voice_chatbot_replay);
                CSSExecute(txtResult.getText().toString());

            }
        });
    }

    private void requestChatbot() {

        SharedPreferences sharedPref = getSharedPreferences("PREF", Context.MODE_PRIVATE);

        String chatbotApiGwUrl = sharedPref.getString("chatbot_api_gw_url", "");
        String chatbotSecretKey = sharedPref.getString("chatbot_secret_key", "");

        TextView csrSourceText = (TextView)findViewById(R.id.textViewVoiceChatbotResult);
        String text = csrSourceText.getText().toString();

        VoiceChatbotActivity.VoiceChatbotTask task = new VoiceChatbotActivity.VoiceChatbotTask();
        task.execute(text, chatbotApiGwUrl, chatbotSecretKey);
    }

    @Override
    protected void onStart() {
        super.onStart(); // 음성인식 서버 초기화는 여기서
        naverRecognizer.getSpeechRecognizer().initialize();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mResult = "";
        txtResult.setText("");
        btnStart.setText(R.string.str_start);
        btnStart.setEnabled(true);
    }
//    @Override
//    protected void onStop() {
//        System.out.println("voice chatbot 종료!!!");
//        super.onStop(); // 음성인식 서버 종료
//        naverRecognizer.getSpeechRecognizer().release();
//    }
    // Declare handler for handling SpeechRecognizer thread's Messages.
    static class RecognitionHandler extends Handler {
        private final WeakReference<VoiceChatbotActivity> mActivity;
        RecognitionHandler(VoiceChatbotActivity activity) {
            mActivity = new WeakReference<VoiceChatbotActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            VoiceChatbotActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }

    public class VoiceChatbotTask extends AsyncTask<String, String, String> {

        @Override
        public String doInBackground(String... strings) {

            return ChatbotProc.main(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(String result) {

            ReturnThreadResult(result);
        }
    }

    public String ReturnThreadResult(String result) {

        //{"version":"v2","userId":"U47b00b58c90f8e47428af8b7bddc1231heo2","sessionId":"617666","timestamp":1546593912020,
        // "bubbles":[{"type":"template","data":{"cover":{"type":"text","data":{"description":"안녕하세요. 저는 클라우드 어드바이저 Elias라고 해요."}},"contentTable":[[{"rowSpan":1,"colSpan":1,"data":{"type":"button","title":"상품별 특징과 사용법을 알려드려요","data":{"type":"basic","action":{"type":"link","data":{"url":"https://www.ncloud.com/product"}}}}}],[{"rowSpan":1,"colSpan":1,"data":{"type":"button","title":"자주 하는 질문들도 공부했어요","data":{"type":"basic","action":{"type":"link","data":{"url":"https://www.ncloud.com/product"}}}}}]]}}],"event":"send"}

        //{"version":"v2","userId":"U47b00b58c90f8e47428af8b7bddc1231heo2","sessionId":"641799","timestamp":1546777198124,
        // "bubbles":[{"type":"text","data":{"description":"질문에 답할 수 없지롱"}}],"event":"send"}
        String chatbotMessage =  "";
        String rlt = result;
        try{
            JSONObject jsonObject = new JSONObject(rlt);
            JSONArray bubbles = jsonObject.getJSONArray("bubbles");

            for (int i =0; i < bubbles.length(); i++){

                JSONObject bubble = bubbles.getJSONObject(i);

                String chatType = bubble.getString("type");

                if (chatType.equals("text")){

                    chatbotMessage = bubble.getJSONObject("data").getString("description");

                }else if (chatType.equals("template")) {

                    chatbotMessage = bubble.getJSONObject("data").getJSONObject("cover").getJSONObject("data").getString("description");

                }else {
                    chatbotMessage = "";
                }

                TextView txtResult = (TextView) findViewById(R.id.text_voice_chatbot_replay);
                txtResult.setText(chatbotMessage);

                break;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        CSSExecute(chatbotMessage);

        return chatbotMessage;
    }

    private void CSSExecute(String message) {

        VoiceChatbotActivity.NaverTTSTask tts = new VoiceChatbotActivity.NaverTTSTask();
        tts.execute(message, "mijin", clientId, clientSecret);
    }

    public class NaverTTSTask extends AsyncTask<String, String, String> {

        @Override
        public String doInBackground(String... strings) {
            System.out.println(strings[1]);
            CssProc.main(strings[0], strings[1], strings[2], strings[3]);
            return null;
        }
    }
}






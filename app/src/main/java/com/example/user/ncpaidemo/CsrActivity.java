package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.ncp.ai.demo.process.CsrProc;
import com.ncp.ai.demo.process.CssProc;
import com.ncp.ai.utils.AudioWriterPCM;
import com.naver.speech.clientapi.SpeechRecognitionResult;

import java.lang.ref.WeakReference;
import java.util.List;

public class CsrActivity extends BaseActivity {

    private static final String TAG = CsrActivity.class.getSimpleName();
    private RecognitionHandler handler;
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
        setContentView(R.layout.activity_csr);

        SharedPreferences sharedPref = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        String clientId = sharedPref.getString("application_client_id", "");

        txtResult = (TextView) findViewById(R.id.textViewCsrResult);
        btnStart = (Button) findViewById(R.id.btn_start);
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
    }

    @Override
    protected void onStart() {
        System.out.println("시작!!!");
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
//        System.out.println("CSR 종료!!!");
//        super.onStop(); // 음성인식 서버 종료
//        naverRecognizer.getSpeechRecognizer().release();
//    }
    // Declare handler for handling SpeechRecognizer thread's Messages.
    static class RecognitionHandler extends Handler {
        private final WeakReference<CsrActivity> mActivity;
        RecognitionHandler(CsrActivity activity) {
            mActivity = new WeakReference<CsrActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            CsrActivity activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }
}
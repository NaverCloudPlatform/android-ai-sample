package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.naver.speech.clientapi.SpeechRecognitionResult;
import com.ncp.ai.demo.process.CsrProc;
import com.ncp.ai.demo.process.CssProc;
import com.ncp.ai.demo.process.NmtProc;
import com.ncp.ai.utils.AudioWriterPCM;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.List;

public class NmtActivity2 extends BaseActivity {

    private static final String TAG = NmtActivity2.class.getSimpleName();
    private NmtActivity2.RecognitionHandler handler;
    private CsrProc naverRecognizer;
    private TextView txtCsrResult;
    private Button btnCsrStart;

    private String mCsrResult;
    private AudioWriterPCM writer;
    private String clientId;
    private String clientSecret;
    private String nmtClientId;
    private String nmtClientSecret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmt_2);

        SharedPreferences sharedPref = getSharedPreferences("PREF", Context.MODE_PRIVATE);

        clientId = sharedPref.getString("application_client_id", "");
        clientSecret = sharedPref.getString("application_client_secret", "");

        txtCsrResult = (TextView) findViewById(R.id.text_csr_result);
        btnCsrStart = (Button) findViewById(R.id.btn_csr_start);
        handler = new NmtActivity2.RecognitionHandler(this);
        //naverRecognizer = new CsrProc(this, handler, clientId);
        naverRecognizer = CsrProc.getCsrProc(this, clientId);
        naverRecognizer.setHandler(handler);
        btnCsrStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!naverRecognizer.getSpeechRecognizer().isRunning()) {
                    mCsrResult = "";
                    txtCsrResult.setText("Connecting...");
                    btnCsrStart.setText(R.string.str_stop);
                    naverRecognizer.recognize();
                } else {
                    Log.d(TAG, "stop and wait Final Result");
                    btnCsrStart.setEnabled(false);
                    naverRecognizer.getSpeechRecognizer().stop();
                }
            }
        });


        /************ Papago NMT **************/
        nmtClientId = sharedPref.getString("application_client_id", "");
        nmtClientSecret = sharedPref.getString("application_client_secret", "");

        Button csrTranslateBtn;

        csrTranslateBtn = (Button) findViewById(R.id.btn_nmt_start);
        csrTranslateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView csrSourceText = (TextView)findViewById(R.id.text_csr_result);
                String text = csrSourceText.getText().toString();

                Spinner csrSourceSpinner = (Spinner)findViewById(R.id.nmt_lang_source_spinner);
                String selSourceItem = csrSourceSpinner.getSelectedItem().toString();

                Spinner csrTragetSpinner = (Spinner)findViewById(R.id.nmt_lang_target_spinner);
                String selTargetItem = csrTragetSpinner.getSelectedItem().toString();

                NmtActivity2.PapagoNmtTask nmtTask = new NmtActivity2.PapagoNmtTask();
                nmtTask.execute(text, convertLangItem(selSourceItem), convertLangItem(selTargetItem),
                        nmtClientId, nmtClientSecret);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart(); // 음성인식 서버 초기화는 여기서
        naverRecognizer.getSpeechRecognizer().initialize();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mCsrResult = "";
        txtCsrResult.setText("");
        btnCsrStart.setText(R.string.str_start);
        btnCsrStart.setEnabled(true);
    }
//    @Override
//    protected void onStop() {
//        super.onStop(); // 음성인식 서버 종료
//        System.out.println("## On Stop");
//        naverRecognizer.getSpeechRecognizer().release();
//    }
    // Declare handler for handling SpeechRecognizer thread's Messages.
    static class RecognitionHandler extends Handler {
        private final WeakReference<NmtActivity2> mActivity;
        RecognitionHandler(NmtActivity2 activity) {
            mActivity = new WeakReference<NmtActivity2>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            NmtActivity2 activity = mActivity.get();
            if (activity != null) {
                activity.handleMessage(msg);
            }
        }
    }


    // Handle speech recognition Messages.- CSR
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case R.id.clientReady: // 음성인식 준비 가능
                txtCsrResult.setText("Connected");
                writer = new AudioWriterPCM(Environment.getExternalStorageDirectory().getAbsolutePath() + "/NaverSpeechTest");
                writer.open("Test");
                break;
            case R.id.audioRecording:
                writer.write((short[]) msg.obj);
                break;
            case R.id.partialResult:
                mCsrResult = (String) (msg.obj);
                txtCsrResult.setText(mCsrResult);
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
                mCsrResult = strBuf.toString();
                txtCsrResult.setText(mCsrResult);

                translateNmt();
                break;
            case R.id.recognitionError:
                if (writer != null) {
                    writer.close();
                }
                mCsrResult = "Error code : " + msg.obj.toString();
                txtCsrResult.setText(mCsrResult);
                btnCsrStart.setText(R.string.str_start);
                btnCsrStart.setEnabled(true);
                break;
            case R.id.clientInactive:
                if (writer != null) {
                    writer.close();
                }
                btnCsrStart.setText(R.string.str_start);
                btnCsrStart.setEnabled(true);
                break;
        }
    }


    // Papago NMT

    private  void translateNmt() {
        TextView csrSourceText = (TextView)findViewById(R.id.text_csr_result);
        String text = csrSourceText.getText().toString();

        Spinner csrSourceSpinner = (Spinner)findViewById(R.id.nmt_lang_source_spinner);
        String selSourceItem = csrSourceSpinner.getSelectedItem().toString();

        Spinner csrTragetSpinner = (Spinner)findViewById(R.id.nmt_lang_target_spinner);
        String selTargetItem = csrTragetSpinner.getSelectedItem().toString();

        NmtActivity2.PapagoNmtTask nmtTask = new NmtActivity2.PapagoNmtTask();
        nmtTask.execute(text, convertLangItem(selSourceItem), convertLangItem(selTargetItem),
                nmtClientId, nmtClientSecret);
    }

    public void ReturnThreadResult(String result) {

        //{"message":{"@type":"response","@service":"naverservice.nmt.proxy","@version":"1.0.0","result":{"srcLangType":"ko","tarLangType":"en","translatedText":"Hello."}}}
        String rlt = result;
        try {

            JSONObject jsonObject = new JSONObject(rlt);
            String text = jsonObject.getString("message");

            jsonObject = new JSONObject(text);
            jsonObject = new JSONObject(jsonObject.getString("result"));
            text = jsonObject.getString("translatedText");

            //System.out.println(text);
            TextView txtResult = (TextView) findViewById(R.id.text_nmt_target);
            txtResult.setText(text);

            CSSExecute();

        } catch (Exception e){

        }
    }

    public class PapagoNmtTask extends AsyncTask<String, String, String> {

        @Override
        public String doInBackground(String... strings) {

            return NmtProc.main(strings[0], strings[1], strings[2], strings[3], strings[4]);
        }

        @Override
        protected void onPostExecute(String result) {

            ReturnThreadResult(result);
        }
    }

    private String convertLangItem(String strItem){

        if(strItem.equals("한국어"))
            return "ko";
        else if(strItem.equals("중국어(간체)"))
            return "zh-CN";
        else if(strItem.equals("중국어(번체)"))
            return "zh-TW";
        else if(strItem.equals("영어"))
            return "en";
        else if(strItem.equals("일본어"))
            return "ja";
        else if(strItem.equals("스페인어"))
            return "es";
        else if(strItem.equals("프랑스어"))
            return "fr";
        else if(strItem.equals("베트남어"))
            return "vi";
        else if(strItem.equals("태국어"))
            return "th";
        else if(strItem.equals("인도네시아어"))
            return "id";
        else
            return "";
    }


    /********* CSS ******************/

    private  void CSSExecute(){

        TextView cssInputText = (TextView)findViewById(R.id.text_nmt_target);;
        String strText = cssInputText.getText().toString();

        Spinner spinner = (Spinner)findViewById(R.id.nmt_lang_target_spinner);
        String selItem = spinner.getSelectedItem().toString();

        String[] splits = selItem.split("\\(");

        String speaker = splits[0];


        if (speaker.equals("한국어")){
            speaker = "matt";
        } else if (speaker.startsWith("일본어")){
            speaker = "shinji";
        } else if (speaker.startsWith("중국어")){
            speaker = "meimei";
        } else if (speaker.startsWith("스페인어")){
            speaker = "jose";
        }else {
            speaker = "matt"; //영어
        }

        NmtActivity2.NaverTTSTask tts = new NmtActivity2.NaverTTSTask();
        tts.execute(strText, speaker, clientId, clientSecret);
    }

    public class NaverTTSTask extends AsyncTask<String, String, String> {

        @Override
        public String doInBackground(String... strings) {

            CssProc.main(strings[0], strings[1], strings[2], strings[3]);
            return null;
        }
    }

}

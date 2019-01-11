package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ncp.ai.demo.process.CssProc;

public class CssActivity extends BaseActivity {

    Button cssBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_css);

        SharedPreferences sharedPref = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        final String clientId = sharedPref.getString("application_client_id", "");
        final String clientSecret = sharedPref.getString("application_client_secret", "");

        cssBtn = (Button) findViewById(R.id.btn_css);
        cssBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText cssInputText = (EditText) findViewById(R.id.text_input_css);
                String strText = cssInputText.getText().toString();

                Spinner spinner = (Spinner)findViewById(R.id.spinner_css_lang);
                String selItem = spinner.getSelectedItem().toString();

                String[] splits = selItem.split("\\(");

                String speaker = splits[0];

                if (speaker.isEmpty() || speaker.equals("")){
                    speaker = "mijin";
                }

                CssActivity.NaverTTSTask tts = new CssActivity.NaverTTSTask();
                tts.execute(strText, speaker, clientId, clientSecret);
            }
        });

        Spinner s = (Spinner)findViewById(R.id.spinner_css_lang);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Spinner s = (Spinner)findViewById(R.id.spinner_css_lang);
                String lang = s.getSelectedItem().toString();
                String text = "";
                if (lang.contains("한국어")) {
                    text = "네이버 클라우드 플랫폼에서는 Clova, papago 등 네이버의 다양한 인공지능 서비스를 API 형태로 제공합니다.";
                }else if (lang.contains("영어")) {
                    text = "NAVER CLOUD PLATFORM provides various AI services in API formats, such as Clova and Papago.";
                }else if (lang.contains("일본어")) {
                    text = "NAVER CLOUD PLATFORMは、ClovaやPapagoなどのAPIフォーマットでさまざまなAIサービスを提供します.";

                }else if (lang.contains("중국어")) {
                    text = "NAVER CLOUD PLATFORM以API格式提供各种AI服务，例如Clova和Papago.";

                }else if (lang.contains("스페인어")) {
                    text = "NAVER CLOUD PLATFORM proporciona varios servicios de AI en formatos API, como Clova y Papago.";

                }else {
                    text = "";
                }

                TextView textViewVersionInfo = (TextView) findViewById(R.id.text_input_css);
                textViewVersionInfo.setText(text);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    public class NaverTTSTask extends AsyncTask<String, String, String> {

        @Override
        public String doInBackground(String... strings) {

            CssProc.main(strings[0], strings[1], strings[2], strings[3]);
            return null;
        }
    }
}

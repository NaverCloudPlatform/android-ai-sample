package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ncp.ai.demo.process.ClovaCSS;

public class CssActivity extends BaseActivity {

    Button cssBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_css);

        SharedPreferences sharedPref = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        final String clientId = sharedPref.getString("client_id", "");
        final String clientSecret = sharedPref.getString("client_secret", "");

        cssBtn = (Button) findViewById(R.id.btn_css);
        cssBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText cssInputText = (EditText) findViewById(R.id.text_input_css) ;
                String strText = cssInputText.getText().toString() ;

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
    }

    public class NaverTTSTask extends AsyncTask<String, String, String> {

        @Override
        public String doInBackground(String... strings) {

            ClovaCSS.main(strings[0], strings[1], strings[2], strings[3]);
            return null;
        }
    }
}

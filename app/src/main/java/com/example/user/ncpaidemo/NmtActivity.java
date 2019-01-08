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

import com.ncp.ai.demo.process.NmtProc;

import org.json.JSONObject;

public class NmtActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmt);

        SharedPreferences sharedPref = getSharedPreferences("PREF", Context.MODE_PRIVATE);

        final String nmtClientId = sharedPref.getString("application_client_id", "");
        final String nmtClientSecret = sharedPref.getString("application_client_secret", "");

        Button csrTranslateBtn;

        csrTranslateBtn = (Button) findViewById(R.id.btn_csr_translate);
        csrTranslateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText csrSourceText = (EditText)findViewById(R.id.text_input_csr_target);
                String text = csrSourceText.getText().toString();

                Spinner csrSourceSpinner = (Spinner)findViewById(R.id.csr_lang_source_spinner);
                String selSourceItem = csrSourceSpinner.getSelectedItem().toString();

                Spinner csrTragetSpinner = (Spinner)findViewById(R.id.csr_lang_target_spinner);
                String selTargetItem = csrTragetSpinner.getSelectedItem().toString();

                NmtActivity.PapagoNmtTask nmtTask = new NmtActivity.PapagoNmtTask();
                nmtTask.execute(text, convertLangItem(selSourceItem), convertLangItem(selTargetItem),
                        nmtClientId, nmtClientSecret);
            }
        });

        Spinner s = (Spinner)findViewById(R.id.csr_lang_source_spinner);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                System.out.println("## position : " + position + parent.getItemAtPosition(position));

                String[] items;

                if(parent.getItemAtPosition(position).equals("영어")) {

                    items = new String[]{"한국어", "중국어(간체)", "중국어(번체)", "프랑스어"};
                }else if (parent.getItemAtPosition(position).equals("한국어")) {
                    items = new String[]{"영어", "중국어(간체)", "중국어(번체)", "프랑스어", "스페인어", "베트남어", "태국어"};
                }else {
                    items = new String[]{"한국어"};
                }

                Spinner spinner = (Spinner) findViewById(R.id.csr_lang_target_spinner);

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(NmtActivity.this, android.R.layout.simple_spinner_item, items);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner.setAdapter(spinnerArrayAdapter);

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
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
            TextView txtResult = (TextView) findViewById(R.id.textView_nmt_result);
            txtResult.setText(text);

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

        // 일본어 빠졌다잉~
    }
}

package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AuthActivity extends BaseActivity {

    Button cssBtn;
    Button chatbotBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // 인증 정보 load
        SharedPreferences sharedPref1 = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        String applicationClientId = sharedPref1.getString("application_client_id", "");
        String applicationClientSecret = sharedPref1.getString("application_client_secret", "");

        String chatbotApiGwUrl = sharedPref1.getString("chatbot_api_gw_url", "");
        String chatbotSecretKey = sharedPref1.getString("chatbot_secret_key", "");

        EditText editTextApplicationClientId = (EditText) findViewById(R.id.text_input_application_client_secret2);
        editTextApplicationClientId.setText(applicationClientId);

        EditText editTextApplicationClientSecret = (EditText) findViewById(R.id.text_input_application_client_secret);
        editTextApplicationClientSecret.setText(applicationClientSecret);

        EditText editTextChatbotApiGwUrl = (EditText) findViewById(R.id.text_input_chatbot_api_gw_url);
        editTextChatbotApiGwUrl.setText(chatbotApiGwUrl);

        EditText editTextChatbotSecretKey = (EditText) findViewById(R.id.text_input_chatbot_secret_key);
        editTextChatbotSecretKey.setText(chatbotSecretKey);



        cssBtn = (Button) findViewById(R.id.btn_auth);
        cssBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 인증 정보 Save
                EditText editTextClientId = (EditText) findViewById(R.id.text_input_application_client_secret2);
                String clientIdInputText = editTextClientId.getText().toString();

                EditText editTextClientSecret = (EditText) findViewById(R.id.text_input_application_client_secret);
                String clientSecretInputText = editTextClientSecret.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("PREF", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("application_client_id", clientIdInputText);
                editor.putString("application_client_secret", clientSecretInputText);
                editor.commit();

            }
        });


        chatbotBtn = (Button) findViewById(R.id.btn_chatbot);
        chatbotBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    // 인증 정보 Save
                    EditText editTextApiUrl = (EditText) findViewById(R.id.text_input_chatbot_api_gw_url);
                    String clientApiUrl = editTextApiUrl.getText().toString();

                    EditText editTextSecretKey = (EditText) findViewById(R.id.text_input_chatbot_secret_key);
                    String clientSecretKey = editTextSecretKey.getText().toString();

                    SharedPreferences sharedPref = getSharedPreferences("PREF", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("chatbot_api_gw_url", clientApiUrl);
                    editor.putString("chatbot_secret_key", clientSecretKey);
                    editor.commit();

                }catch (Exception e){

                }


            }
        });
    }
}

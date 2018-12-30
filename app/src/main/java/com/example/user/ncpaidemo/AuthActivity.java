package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AuthActivity extends BaseActivity {

    Button cssBtn;
    Button nmtBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // 인증 정보 load
        SharedPreferences sharedPref1 = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        String clovaClientId = sharedPref1.getString("clova_client_id", "");
        String clovaClientSecret = sharedPref1.getString("clova_client_secret", "");

        String nmtClientId = sharedPref1.getString("nmt_client_id", "");
        String nmtClientSecret = sharedPref1.getString("nmt_client_secret", "");

        EditText editTextClovaClientId = (EditText) findViewById(R.id.text_input_clova_client_id);
        editTextClovaClientId.setText(clovaClientId);

        EditText editTextClovaClientSecret = (EditText) findViewById(R.id.text_input_clova_client_secret);
        editTextClovaClientSecret.setText(clovaClientSecret);

        EditText editTextPapagoClientId = (EditText) findViewById(R.id.text_input_nmt_client_id);
        editTextPapagoClientId.setText(nmtClientId);

        EditText editTextPapagoClientSecret = (EditText) findViewById(R.id.text_input_nmt_client_secret);
        editTextPapagoClientSecret.setText(nmtClientSecret);

        cssBtn = (Button) findViewById(R.id.btn_auth);
        cssBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 인증 정보 Save
                EditText editTextClientId = (EditText) findViewById(R.id.text_input_clova_client_id);
                String clientIdInputText = editTextClientId.getText().toString();

                EditText editTextClientSecret = (EditText) findViewById(R.id.text_input_clova_client_secret);
                String clientSecretInputText = editTextClientSecret.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("PREF", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("clova_client_id", clientIdInputText);
                editor.putString("clova_client_secret", clientSecretInputText);
                editor.commit();
            }
        });


        nmtBtn = (Button) findViewById(R.id.btn_nmt_auth);
        nmtBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 인증 정보 Save
                EditText editTextClientId = (EditText) findViewById(R.id.text_input_nmt_client_id);
                String clientIdInputText = editTextClientId.getText().toString();

                EditText editTextClientSecret = (EditText) findViewById(R.id.text_input_nmt_client_secret);
                String clientSecretInputText = editTextClientSecret.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("PREF", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("nmt_client_id", clientIdInputText);
                editor.putString("nmt_client_secret", clientSecretInputText);
                editor.commit();
            }
        });
    }
}

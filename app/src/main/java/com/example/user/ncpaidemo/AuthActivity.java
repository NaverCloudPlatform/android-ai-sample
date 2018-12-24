package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AuthActivity extends BaseActivity {

    Button cssBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // 인증 정보 load
        SharedPreferences sharedPref1 = getSharedPreferences("PREF", Context.MODE_PRIVATE);
        String client_id = sharedPref1.getString("client_id", "");
        String client_secret = sharedPref1.getString("client_secret", "");

        EditText editTextClientId = (EditText) findViewById(R.id.text_input_client_id);
        editTextClientId.setText(client_id);

        EditText editTextClientSecret = (EditText) findViewById(R.id.text_input_client_secret);
        editTextClientSecret.setText(client_secret);

        cssBtn = (Button) findViewById(R.id.btn_auth);
        cssBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 인증 정보 Save
                EditText editTextClientId = (EditText) findViewById(R.id.text_input_client_id);
                String clientIdInputText = editTextClientId.getText().toString();

                EditText editTextClientSecret = (EditText) findViewById(R.id.text_input_client_secret);
                String clientSecretInputText = editTextClientSecret.getText().toString();

                SharedPreferences sharedPref = getSharedPreferences("PREF", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("client_id", clientIdInputText);
                editor.putString("client_secret", clientSecretInputText);
                editor.commit();
            }
        });
    }
}

package com.example.user.ncpaidemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.menu_auth:

                intent = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(intent);

                return true;
            case R.id.menu_css:

                intent = new Intent(getApplicationContext(), CssActivity.class);
                startActivity(intent);

                return true;
            case R.id.menu_csr:

                intent = new Intent(getApplicationContext(), CsrActivity.class);
                startActivity(intent);

                return true;
            case R.id.menu_voice_chatbot:

                intent = new Intent(getApplicationContext(), VoiceChatbotActivity.class);
                startActivity(intent);

                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }
}

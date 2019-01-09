package com.example.user.ncpaidemo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView1 = (TextView) findViewById(R.id.textView_exp) ;
        textView1.setText("[인증 정보 설정]\n\n" +
                "1. 우측 상단 인증설정 메뉴 선택\n" +
                "2. Application Key 설정\n" +
                "   - Client ID, Client Secret\n" +
                "    :AI NAVER API > Application 인증정보 설정\n" +
                "     (CSR, CSS, Papago NMT 통합)\n" +
                "3. Chatbot 접속 정보 설정\n" +
                "   - API Gateway URL\n" +
                "    :Chatbot API Gateway Invoke URL 등록\n" +
                "   - Secret Key\n" +
                "    :Chatbot Custom Secret Key 등록\n" +
                " " +
                "");
    }
}
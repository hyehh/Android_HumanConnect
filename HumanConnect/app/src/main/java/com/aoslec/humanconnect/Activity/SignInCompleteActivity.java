package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aoslec.humanconnect.R;

public class SignInCompleteActivity extends AppCompatActivity {

    String name = null;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_complete);

        textView = findViewById(R.id.sign_in_complete_tv);
        button = findViewById(R.id.sign_in_complete_btn);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        textView.setText(name + "님이 회원가입이 완료되었습니다!");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SignInCompleteActivity.this, LoginActivity.class);
                startActivity(intent1);
            }
        });
    }
}
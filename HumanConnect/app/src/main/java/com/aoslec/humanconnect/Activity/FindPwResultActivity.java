package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aoslec.humanconnect.R;

public class FindPwResultActivity extends AppCompatActivity {

    String pw, name;
    TextView result, resultName;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw_result);
        setTitle("비밀번호 찾기 결과");

        Intent intent = getIntent();
        pw = intent.getStringExtra("pw");
        name = intent.getStringExtra("name");

        resultName = findViewById(R.id.find_pw_result_name);
        result = findViewById(R.id.find_pw_result);
        button = findViewById(R.id.fine_pw_result_btn);

        resultName.setText(name + "님의");
        result.setText(pw);

        button.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(FindPwResultActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };

}
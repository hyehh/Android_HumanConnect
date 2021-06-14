package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aoslec.humanconnect.R;

public class SignInTelActivity extends AppCompatActivity {

    String macIP, name = null;
    EditText tel = null;
    Button btnBack, btnNext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_tel);
        setTitle("전화번호 입력");

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        name = intent.getStringExtra("name");

        tel = findViewById(R.id.sign_in_tel_edit);
        btnBack = findViewById(R.id.sign_in_tel_btn_back);
        btnNext = findViewById(R.id.sign_in_tel_btn_next);

        btnBack.setOnClickListener(onClickListener);
        btnNext.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            switch (v.getId()){
                case R.id.sign_in_tel_btn_back:
                    intent = new Intent(SignInTelActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sign_in_tel_btn_next:
                    if(tel.getText().toString().isEmpty()){
                        Toast.makeText(SignInTelActivity.this, "전화번호를 입력한 후, 버튼을 클릭해주세요!", Toast.LENGTH_SHORT).show();
                    }else {
                        int editTel = Integer.parseInt(tel.getText().toString());
                        intent = new Intent(SignInTelActivity.this, SignInPwActivity.class);
                        intent.putExtra("macIP", macIP);
                        intent.putExtra("name", name);
                        intent.putExtra("tel", editTel);
                        startActivity(intent);
                    }
                    break;
            }
        }
    };
}
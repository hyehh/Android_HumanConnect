package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aoslec.humanconnect.R;

public class LoginActivity extends AppCompatActivity {

    String macIP;
    EditText id, pw;
    Button btnLogin, btnSignIn, btnFindPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginListener();
    }

    private void loginListener(){

        macIP = "192.168.0.2";
        id = findViewById(R.id.login_id);
        pw = findViewById(R.id.login_pw);
        btnLogin = findViewById(R.id.login_btn_login);
        btnSignIn = findViewById(R.id.login_btn_sign_in);
        btnFindPw = findViewById(R.id.login_btn_find_pw);

        btnLogin.setOnClickListener(onClickListener);
        btnSignIn.setOnClickListener(onClickListener);
        btnFindPw.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            switch (v.getId()){
                case R.id.login_btn_login:
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("id", id.getText().toString());
                    intent.putExtra("pw", pw.getText().toString());
                    startActivity(intent);
                    break;
                case R.id.login_btn_sign_in:
                    intent = new Intent(LoginActivity.this, SignInNameActivity.class);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
                    break;
                case R.id.login_btn_find_pw:
                    intent = new Intent(LoginActivity.this, FindPwActivity.class);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
                    break;
            }
        }
    };
}
package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aoslec.humanconnect.NetworkTask.NetworkTask;
import com.aoslec.humanconnect.R;

public class LoginActivity extends AppCompatActivity {

    String macIP, urlAddr;
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

        urlAddr = "http://" + macIP + ":8080/humanconnect/login.jsp?";

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
                    urlAddr = "http://" + macIP + ":8080/humanconnect/login.jsp?";
                    urlAddr = urlAddr + "mid=" + Integer.parseInt(id.getText().toString()) + "&pw=" + pw.getText().toString();
                    Log.v("Message", urlAddr);

                    String result = connectLoginData();
                    if(result.equals("true")){
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("macIP", macIP);
                        intent.putExtra("mid", id.getText().toString());
                        intent.putExtra("pw", pw.getText().toString());
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "로그인 되었습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "아이디나 비밀번호를 잘못 입력하셨습니다", Toast.LENGTH_SHORT).show();
                    }
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

    private String connectLoginData(){
        Log.v("Message", "result");
        String result = null;
        try {
            // NetworkTask 로 넘겨줌
            NetworkTask networkTask = new NetworkTask(LoginActivity.this, urlAddr, "Login");
            Object obj = networkTask.execute().get();
            // true 들어오면 성공한 것, 만약 그 이외의 숫자면 실패한 것
            result = (String) obj;
            Log.v("Message", result);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "실패", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
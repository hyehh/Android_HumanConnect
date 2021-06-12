package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aoslec.humanconnect.NetworkTask.NetworkTask;
import com.aoslec.humanconnect.R;

public class SignInPwActivity extends AppCompatActivity {

    String macIP, name, urlAddr = null;
    int tel = 0;
    EditText pw = null;
    Button btnBack, btnNext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_pw);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        name = intent.getStringExtra("name");
        tel = intent.getIntExtra("tel", 0);

        urlAddr = "http://" + macIP + ":8080/humanconnect/signIn.jsp?";

        pw = findViewById(R.id.sign_in_pw_edit);
        btnBack = findViewById(R.id.sign_in_pw_btn_back);
        btnNext = findViewById(R.id.sign_in_pw_btn_next);

        btnBack.setOnClickListener(onClickListener);
        btnNext.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String editPw = pw.getText().toString();
            Intent intent = null;
            urlAddr = urlAddr + "name=" + name + "&tel=" + tel + "&pw=" + pw;

            switch (v.getId()){
                case R.id.sign_in_tel_btn_back:
                    intent = new Intent(SignInPwActivity.this, LoginActivity.class);
                    break;
                case R.id.sign_in_tel_btn_next:

                    String result = connectSignInData();
                    if(result.equals("1")){
                        intent = new Intent(SignInPwActivity.this, SignInCompleteActivity.class);
                        intent.putExtra("macIP", macIP);
                        intent.putExtra("name", name);
                        intent.putExtra("tel", editPw);
                        Toast.makeText(SignInPwActivity.this, "입력이 완료되었습니다", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }else {
                        Toast.makeText(SignInPwActivity.this, "입력이 실패 되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private String connectSignInData(){
        String result = null;
        try {
            // NetworkTask 로 넘겨줌
            NetworkTask networkTask = new NetworkTask(SignInPwActivity.this, urlAddr, "signIn");
            Object obj = networkTask.execute().get();
            // 1이 들어오면 성공한 것, 만약 그 이외의 숫자면 실패한 것
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
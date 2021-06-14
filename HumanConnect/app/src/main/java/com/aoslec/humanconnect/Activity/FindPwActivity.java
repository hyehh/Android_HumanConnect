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

public class FindPwActivity extends AppCompatActivity {

    String macIP, urlAddr, urlAddr2, name, mmid = null;
    int mid = 0;
    EditText ename, emid;
    Button cancel, find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);
        setTitle("비밀번호 찾기");

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");

        urlAddr = "http://" + macIP + ":8080/humanconnect/findPw.jsp?";

        ename = findViewById(R.id.find_pw_name);
        emid = findViewById(R.id.find_pw_id);
        cancel = findViewById(R.id.fine_pw_cancel);
        find = findViewById(R.id.fine_pw_search);

        name = ename.getText().toString();
        //mid = 0;
        //mmid = emid.getText().toString();
        Log.v("Message", emid.getText().toString());
        //Log.v("Message", "emid" + Integer.parseInt(emid.getText().toString()));
        //mid = Integer.parseInt(emid.getText().toString());

        cancel.setOnClickListener(onClickListener);
        find.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            urlAddr = "http://" + macIP + ":8080/humanconnect/findPw.jsp?";
            urlAddr = urlAddr + "mid=" + Integer.parseInt(emid.getText().toString()) + "&name=" + ename.getText().toString();
            Log.v("Message", urlAddr);
            String password = getRamdomPassword(8);
            urlAddr2 = "http://" + macIP + ":8080/humanconnect/findPw2.jsp?";
            urlAddr2 = urlAddr2 + "pw=" + password + "&mid=" + Integer.parseInt(emid.getText().toString());
            Log.v("Message", urlAddr2);
            switch (v.getId()){
                case R.id.fine_pw_cancel:
                    finish();
                    break;
                case R.id.fine_pw_search:
                    String result = connectFindPwData();
                    if(result.equals("true")){
                        String result2 = connectFindPwData2();
                        Log.v("Message", "ㄱㄱㄷ" + result2);
                        if(result2.equals("1")){
                            intent = new Intent(FindPwActivity.this, FindPwResultActivity.class);
                            intent.putExtra("macIP", macIP);
                            intent.putExtra("name", ename.getText().toString());
                            intent.putExtra("pw", password);
                            startActivity(intent);
                        }

                    }else {
                        Toast.makeText(FindPwActivity.this, "아이디나 비밀번호를 잘못 입력하셨습니다", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private String connectFindPwData(){
        Log.v("Message", "result");
        String result = null;
        try {
            // NetworkTask 로 넘겨줌
            NetworkTask networkTask = new NetworkTask(FindPwActivity.this, urlAddr, "FindPw");
            Object obj = networkTask.execute().get();
            // true 들어오면 성공한 것, 만약 그 이외의 숫자면 실패한 것
            result = (String) obj;
            Log.v("Message", result);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(FindPwActivity.this, "실패", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private String connectFindPwData2(){
        Log.v("Message", "result");
        String result = null;
        try {
            // NetworkTask 로 넘겨줌
            NetworkTask networkTask = new NetworkTask(FindPwActivity.this, urlAddr2, "Password");
            Object obj = networkTask.execute().get();
            // 1이 들어오면 성공한 것, 만약 그 이외의 숫자면 실패한 것
            result = (String) obj;
            Log.v("Message", result);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(FindPwActivity.this, "실패", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public static String getRamdomPassword(int len) {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z' };

        int idx = 0;
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < len; i++) {

            idx = (int) (charSet.length * Math.random()); // 36 * 생성된 난수를  Int로 추출 (소숫점제거)
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }
}
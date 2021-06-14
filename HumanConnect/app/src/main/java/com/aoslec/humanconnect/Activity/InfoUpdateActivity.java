package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.humanconnect.NetworkTask.NetworkTask;
import com.aoslec.humanconnect.R;

public class InfoUpdateActivity extends AppCompatActivity {

    String macIP, urlAddr, name, pw = null;
    int mid = 0;
    TextView tid;
    EditText ename, epw;
    Button back, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_update);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        pw = intent.getStringExtra("pw");
        mid = intent.getIntExtra("mid", 0);
        macIP = intent.getStringExtra("macIP");

        tid = findViewById(R.id.info_update_id);
        epw = findViewById(R.id.info_update_pw);
        ename = findViewById(R.id.info_update_name);
        update = findViewById(R.id.info_update_complete);
        back = findViewById(R.id.info_update_back);

        epw.setText(pw);
        ename.setText(name);
        tid.setText(Integer.toString(mid));

        update.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            urlAddr = "http://" + macIP + ":8080/humanconnect/infoUpdate.jsp?";
            urlAddr = urlAddr + "mid=" + mid;
            switch (v.getId()){
                case R.id.info_update_complete:
                    Toast.makeText(InfoUpdateActivity.this, "회원정보가 수정되었습니다", Toast.LENGTH_SHORT).show();
                    intent = new Intent(InfoUpdateActivity.this, InfoActivity.class);
                    intent.putExtra("name", ename.getText().toString());
                    intent.putExtra("pw", epw.getText().toString());
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("mid", mid);
                    startActivity(intent);
                    break;
                case R.id.info_update_back:
                    finish();
                    break;
            }
        }
    };

    private String connectUpdateData(){
        Log.v("Message", "result");
        String result = null;
        try {
            // NetworkTask 로 넘겨줌
            NetworkTask networkTask = new NetworkTask(InfoUpdateActivity.this, urlAddr, "signOut");
            Object obj = networkTask.execute().get();
            // 1이 들어오면 성공한 것, 만약 그 이외의 숫자면 실패한 것
            result = (String) obj;
            Log.v("Message", result);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(InfoUpdateActivity.this, "실패", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
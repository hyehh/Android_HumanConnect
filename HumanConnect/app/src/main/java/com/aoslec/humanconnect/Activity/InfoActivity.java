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

public class InfoActivity extends AppCompatActivity {

    String macIP, urlAddr, name, pw = null;
    int mid = 0;
    TextView tname, tid, tpw;
    Button delete, update, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        pw = intent.getStringExtra("pw");
        mid = intent.getIntExtra("mid", 0);
        macIP = intent.getStringExtra("macIP");

        tname = findViewById(R.id.info_name);
        tid = findViewById(R.id.info_id);
        tpw = findViewById(R.id.info_pw);
        delete = findViewById(R.id.info_delete);
        update = findViewById(R.id.info_update);
        logout = findViewById(R.id.info_logout);

        tname.setText(name);
        tpw.setText(pw);
        tid.setText(Integer.toString(mid));

        delete.setOnClickListener(onClickListener);
        update.setOnClickListener(onClickListener);
        logout.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            urlAddr = "http://" + macIP + ":8080/humanconnect/signout.jsp?";
            urlAddr = urlAddr + "mid=" + mid;
            switch (v.getId()){
                case R.id.info_delete:
                    String result = connectSignOutData();
                    if(result.equals("1")){
                        intent = new Intent(InfoActivity.this, LoginActivity.class);
                        intent.putExtra("macIP", macIP);
                        startActivity(intent);
                    }else {
                        Toast.makeText(InfoActivity.this, "입력이 실패 되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.info_logout:
                    intent = new Intent(InfoActivity.this, LoginActivity.class);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
                    break;
                case R.id.info_update:
                    intent = new Intent(InfoActivity.this, InfoUpdateActivity.class);
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("name", name);
                    intent.putExtra("pw", pw);
                    intent.putExtra("mid", mid);
                    startActivity(intent);
                    break;
            }
        }
    };

    private String connectSignOutData(){
        Log.v("Message", "result");
        String result = null;
        try {
            // NetworkTask 로 넘겨줌
            NetworkTask networkTask = new NetworkTask(InfoActivity.this, urlAddr, "signOut");
            Object obj = networkTask.execute().get();
            // 1이 들어오면 성공한 것, 만약 그 이외의 숫자면 실패한 것
            result = (String) obj;
            Log.v("Message", result);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(InfoActivity.this, "실패", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
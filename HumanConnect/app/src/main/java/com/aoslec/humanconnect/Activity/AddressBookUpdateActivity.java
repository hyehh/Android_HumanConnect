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

import com.aoslec.humanconnect.NetworkTask.NetworkTaskSelect;
import com.aoslec.humanconnect.R;

public class AddressBookUpdateActivity extends AppCompatActivity {

    String macIP, urlAddr = null;
    String name, email, filePath;
    int phone, acode, mid;
    EditText ename, eemail, ephone;
    String uname, uemail, ufilePath;
    int uphone;
    Button cancel, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book_update);
        setTitle("연락처 수정");

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        filePath = intent.getStringExtra("filePath");
        phone = intent.getIntExtra("phone", 0);
        acode = intent.getIntExtra("acode", 0);
        macIP = intent.getStringExtra("macIP");
        mid = intent.getIntExtra("mid", 0);

        ename = findViewById(R.id.update_name);
        eemail = findViewById(R.id.update_email);
        ephone = findViewById(R.id.update_phone);
        cancel = findViewById(R.id.update_back);
        update = findViewById(R.id.update_complete);

        ename.setText(name);
        eemail.setText(email);
        ephone.setText(Integer.toString(phone));

        cancel.setOnClickListener(onClickListener);
        update.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.update_back:
                    break;
                case R.id.update_complete:
                    uname = ename.getText().toString();
                    uphone = Integer.parseInt(ephone.getText().toString());
                    uemail = eemail.getText().toString();
                    ufilePath = filePath;
                    urlAddr = "http://" + macIP + ":8080/humanconnect/addressBookUpdate.jsp?";
                    urlAddr = urlAddr + "name=" + uname + "&phone=" + uphone + "&email=" + uemail + "&filePath=" + ufilePath + "&acode=" + acode;
                    Log.v("Message", "url= " + urlAddr);
                    String result = connectUpdateData();
                    Log.v("Message", "result = " + result);
                    if (result.equals("2")){
                        Toast.makeText(AddressBookUpdateActivity.this, "수정되었습니다", Toast.LENGTH_SHORT).show();
                        intent = new Intent(AddressBookUpdateActivity.this, AddressBookDetailActivity.class);
                        intent.putExtra("name", uname);
                        intent.putExtra("phone", uphone);
                        intent.putExtra("email", uemail);
                        intent.putExtra("filePath", filePath);
                        intent.putExtra("mid", mid);
                        intent.putExtra("macIP", macIP);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(AddressBookUpdateActivity.this, "수정이 실패 되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    // finish하면 메인화면으로 이동함! (전화면으로 이동하는 것임 - insert화면은 없어짐)
                    break;
            }
        }
    };

    private String connectUpdateData(){
        String result = null;
        try {
            // NetworkTask 로 넘겨줌
            NetworkTaskSelect networkTask = new NetworkTaskSelect(AddressBookUpdateActivity.this, urlAddr, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
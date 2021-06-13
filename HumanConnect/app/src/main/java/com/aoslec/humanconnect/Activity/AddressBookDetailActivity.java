package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.humanconnect.Adapter.AddressBookAdapter;
import com.aoslec.humanconnect.Bean.AddressBook;
import com.aoslec.humanconnect.NetworkTask.NetworkTaskSelect;
import com.aoslec.humanconnect.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddressBookDetailActivity extends AppCompatActivity {

    String macIP, urlAddr = null;
    String name, email, filePath;
    int phone, acode;
    TextView tname, temail, tphone;
    Button delete, update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book_detail);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        filePath = intent.getStringExtra("filePath");
        phone = intent.getIntExtra("phone", 0);
        acode = intent.getIntExtra("acode", 0);
        macIP = intent.getStringExtra("macIP");

        tname = findViewById(R.id.detail_name);
        temail = findViewById(R.id.detail_email);
        tphone = findViewById(R.id.detail_phone);
        delete = findViewById(R.id.detail_delete);
        update = findViewById(R.id.detail_update);

        tname.setText(name);
        temail.setText(email);
        tphone.setText(Integer.toString(phone));

        delete.setOnClickListener(onClickListener);
        update.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.detail_delete:
                    urlAddr = "http://" + macIP + ":8080/humanconnect/addressBookDelete.jsp?";
                    urlAddr = urlAddr + "acode=" + acode;
                    String result = connectDeleteData();
                    if (result.equals("1")){
                        Toast.makeText(AddressBookDetailActivity.this, "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AddressBookDetailActivity.this, "삭제가 실패 되었습니다", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                    break;
                case R.id.detail_update:
                    intent = new Intent(AddressBookDetailActivity.this, AddressBookUpdateActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("filePath", filePath);
                    intent.putExtra("phone", phone);
                    intent.putExtra("acode", acode);
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
                    break;
            }
        }
    };

    private String connectDeleteData(){
        String result = null;
        try {
            // NetworkTask 로 넘겨줌
            NetworkTaskSelect networkTask = new NetworkTaskSelect(AddressBookDetailActivity.this, urlAddr, "delete");
            Object obj = networkTask.execute().get();
            // 1이 들어오면 성공한 것, 만약 그 이외의 숫자면 실패한 것
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
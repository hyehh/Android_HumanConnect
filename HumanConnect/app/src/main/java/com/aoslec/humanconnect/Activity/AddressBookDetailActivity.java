package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.aoslec.humanconnect.Adapter.AddressBookAdapter;
import com.aoslec.humanconnect.Bean.AddressBook;
import com.aoslec.humanconnect.NetworkTask.NetworkTaskSelect;
import com.aoslec.humanconnect.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddressBookDetailActivity extends AppCompatActivity {

    String macIP, urlAddr = null;
    String name, email, filePath;
    int phone, acode, mid;
    TextView tname, temail, tphone;
    Button delete, update, call, message, mail, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book_detail);

        setTitle(name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData(){
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        filePath = intent.getStringExtra("filePath");
        phone = intent.getIntExtra("phone", 0);
        acode = intent.getIntExtra("acode", 0);
        macIP = intent.getStringExtra("macIP");
        mid = intent.getIntExtra("mid", 0);

        tname = findViewById(R.id.detail_name);
        temail = findViewById(R.id.detail_email);
        tphone = findViewById(R.id.detail_phone);
        delete = findViewById(R.id.detail_delete);
        update = findViewById(R.id.detail_update);
        call = findViewById(R.id.detail_call);
        message = findViewById(R.id.detail_message);
        mail = findViewById(R.id.detail_mail);
        back = findViewById(R.id.detail_back);

        tname.setText(name);
        temail.setText(email);
        tphone.setText(Integer.toString(phone));

        delete.setOnClickListener(onClickListener);
        update.setOnClickListener(onClickListener);
        call.setOnClickListener(onClickListener);
        message.setOnClickListener(onClickListener);
        mail.setOnClickListener(onClickListener);
        back.setOnClickListener(onClickListener);
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
                    intent.putExtra("mid", mid);
                    startActivity(intent);
                    break;
                case R.id.detail_call:
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010"+phone));
                    startActivity(intent);
                    break;
                case R.id.detail_message:
                    Uri uri = Uri.parse("smsto:010" + phone);
                    intent = new Intent(Intent.ACTION_SENDTO, uri);
                    intent.putExtra("sms_body", " ");
                    startActivity(intent);
                    break;
                case R.id.detail_mail:
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    String[] address = {email};
                    intent.putExtra(Intent.EXTRA_EMAIL, address);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(intent);
                    break;
                case R.id.detail_back:
                    intent = new Intent(AddressBookDetailActivity.this, MainActivity.class);
                    intent.putExtra("mid", mid);
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
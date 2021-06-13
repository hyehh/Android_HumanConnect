package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aoslec.humanconnect.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddressBookInsertActivity extends AppCompatActivity {

    String macIP, pw = null;
    int mid = 0;
    Button choose, back, complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_book_insert);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        pw = intent.getStringExtra("pw");
        mid = intent.getIntExtra("mid", 0);

        choose = findViewById(R.id.insert_choose_btn);
        back = findViewById(R.id.insert_back);
        complete = findViewById(R.id.insert_complete);

        //choose.setOnClickListener(onClickListener);
    }

}
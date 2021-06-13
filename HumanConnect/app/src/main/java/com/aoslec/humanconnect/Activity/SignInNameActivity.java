package com.aoslec.humanconnect.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aoslec.humanconnect.R;

public class SignInNameActivity extends AppCompatActivity {

    String macIP = null;
    EditText name = null;
    Button btnBack, btnNext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_name);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");

        name = findViewById(R.id.sign_in_name_edit);
        btnBack = findViewById(R.id.sign_in_name_btn_back);
        btnNext = findViewById(R.id.sign_in_name_btn_next);

        btnBack.setOnClickListener(onClickListener);
        btnNext.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String editName = name.getText().toString();
            Intent intent = null;

            switch (v.getId()){
                case R.id.sign_in_name_btn_back:
                    intent = new Intent(SignInNameActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sign_in_name_btn_next:
                    intent = new Intent(SignInNameActivity.this, SignInTelActivity.class);
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("name", editName);
                    startActivity(intent);
                    break;
            }
        }
    };

}
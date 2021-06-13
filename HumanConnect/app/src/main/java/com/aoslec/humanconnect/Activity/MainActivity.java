package com.aoslec.humanconnect.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.aoslec.humanconnect.Adapter.AddressBookAdapter;
import com.aoslec.humanconnect.Bean.AddressBook;
import com.aoslec.humanconnect.NetworkTask.NetworkTaskSelect;
import com.aoslec.humanconnect.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//public class MainActivity extends AppCompatActivity implements AddressBookAdapter.OnListItemLongSelectedInterface, AddressBookAdapter.OnListItemSelectedInterface{

    String macIP, pw, urlAddr = null;
    int mid = 0;
    FloatingActionButton fab = null;
    RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;
    ArrayList<AddressBook> addressBooks = null;
    AddressBookAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        adapter = new AddressBookAdapter(this, this, this);

        fab = findViewById(R.id.main_f_btn);
        recyclerView = findViewById(R.id.main_recycler);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
//        pw = intent.getStringExtra("pw");
//        mid = intent.getIntExtra("mid", 0);
        urlAddr = "http://" + macIP + ":8080/humanconnect/addressBookSearch.jsp";

        fab.setOnClickListener(onClickListener);

    }


//    @Override
//    public void onItemSelected(View v, int position){
//        Log.v("Message", "onItemSelected");
//        AddressBookAdapter.ViewHolder viewHolder = (AddressBookAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
//        Toast.makeText(MainActivity.this, "클릭", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(MainActivity.this, AddressBookDetailActivity.class);
//            intent.putExtra("name", addressBooks.get(position).getName());
//            intent.putExtra("phone", addressBooks.get(position).getPhone());
//            intent.putExtra("email", addressBooks.get(position).getEmail());
//            intent.putExtra("filePath", addressBooks.get(position).getFilePath());
//            intent.putExtra("macIP", macIP);
//            startActivity(intent);
//    }
//
//    @Override
//    public void onItemLongSelected(View v, int position){
//    }

    @Override
    protected void onResume() {
        super.onResume();
        // 여기 있어야 데이터가 처음 실행될 때도 업데이트 되고, 수정버튼 누르고 다시 돌아왔을 때도 업데이트 된다!
        connectGetData();
    }

    private void connectGetData(){
        try {
            Log.v("Message", urlAddr);
            NetworkTaskSelect networkTask = new NetworkTaskSelect(MainActivity.this, urlAddr, "selectAll");
            Object obj = networkTask.execute().get();
            addressBooks = (ArrayList<AddressBook>) obj;
            // NetworkTask 일 끝남

            // 이제 adapter 일 시작
            adapter = new AddressBookAdapter(MainActivity.this, R.layout.main_layout, addressBooks);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new AddressBookAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    Intent intent = null;
                    intent = new Intent(MainActivity.this, AddressBookDetailActivity.class);
                    intent.putExtra("acode", addressBooks.get(pos).getAcode());
                    intent.putExtra("name", addressBooks.get(pos).getName());
                    intent.putExtra("phone", addressBooks.get(pos).getPhone());
                    intent.putExtra("email", addressBooks.get(pos).getEmail());
                    intent.putExtra("filePath", addressBooks.get(pos).getFilePath());
                    intent.putExtra("macIP", macIP);
                    startActivity(intent);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

//    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
//        Intent intent = null;
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            intent = new Intent(SelectAllActivity.this, UpdateActivity.class);
//            intent.putExtra("code", students.get(position).getCode());
//            intent.putExtra("name", students.get(position).getName());
//            intent.putExtra("dept", students.get(position).getDept());
//            intent.putExtra("phone", students.get(position).getPhone());
//            intent.putExtra("macIP", macIP);
//            startActivity(intent);
//        }
//    };
//
//    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
//        Intent intent = null;
//        @Override
//        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//            intent = new Intent(SelectAllActivity.this, DeleteActivity.class);
//            intent.putExtra("code", students.get(position).getCode());
//            intent.putExtra("name", students.get(position).getName());
//            intent.putExtra("dept", students.get(position).getDept());
//            intent.putExtra("phone", students.get(position).getPhone());
//            intent.putExtra("macIP", macIP);
//            startActivity(intent);
//            return true;
//        }
//    };


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            intent = new Intent(MainActivity.this, AddressBookInsertActivity.class);
            intent.putExtra("macIP", macIP);
            intent.putExtra("pw", pw);
            intent.putExtra("mid", mid);
            startActivity(intent);
        }
    };
}
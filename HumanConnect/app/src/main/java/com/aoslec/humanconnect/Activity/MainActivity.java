package com.aoslec.humanconnect.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.aoslec.humanconnect.Adapter.AddressBookAdapter;
import com.aoslec.humanconnect.Bean.AddressBook;
import com.aoslec.humanconnect.Bean.Member;
import com.aoslec.humanconnect.NetworkTask.NetworkTask;
import com.aoslec.humanconnect.NetworkTask.NetworkTaskSelect;
import com.aoslec.humanconnect.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//public class MainActivity extends AppCompatActivity implements AddressBookAdapter.OnListItemLongSelectedInterface, AddressBookAdapter.OnListItemSelectedInterface{

    String macIP, pw, urlAddr, urlAddr2 = null;
    int mid = 0;
    FloatingActionButton fab, fab2, fab3 = null;
    RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;
    ArrayList<AddressBook> addressBooks, filteredList = null;
    ArrayList<Member> members = null;
    AddressBookAdapter adapter = null;
    EditText editText;
    //ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void searchFilter(String searchText) {
        filteredList.clear();

        for (int i = 0; i < addressBooks.size(); i++) {
            if (addressBooks.get(i).getName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(addressBooks.get(i));
            }
        }

        adapter.filterList(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 여기 있어야 데이터가 처음 실행될 때도 업데이트 되고, 수정버튼 누르고 다시 돌아왔을 때도 업데이트 된다!
        connectGetData();
    }

    private void connectGetData(){
        try {

            fab = findViewById(R.id.main_f_btn);
            fab2 = findViewById(R.id.main_f_update);
            fab3 = findViewById(R.id.main_f_up);
            recyclerView = findViewById(R.id.main_recycler);
            layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            editText = findViewById(R.id.main_edit);

            filteredList = new ArrayList<>();

            Intent intent = getIntent();
            macIP = intent.getStringExtra("macIP");
            mid = intent.getIntExtra("mid", 0);
            urlAddr = "http://" + macIP + ":8080/humanconnect/addressBookSearch.jsp?";
            urlAddr = urlAddr + "mid=" + mid;
            Log.v("Message", urlAddr);
            fab.setOnClickListener(onClickListener);
            fab2.setOnClickListener(onClickListener);

            setFloatingActionButton(recyclerView);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String searchText = editText.getText().toString();
                    searchFilter(searchText);
                }
            });

            Log.v("Message", "try " + urlAddr);

            NetworkTaskSelect networkTask = new NetworkTaskSelect(MainActivity.this, urlAddr, "selectAll");
            Object obj = networkTask.execute().get();
            addressBooks = (ArrayList<AddressBook>) obj;
            // NetworkTask 일 끝남

            // 이제 adapter 일 시작
            adapter = new AddressBookAdapter(MainActivity.this, R.layout.main_layout, addressBooks);
            recyclerView.setAdapter(adapter);

            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    if(!recyclerView.canScrollVertically(-1)){
                        Log.v("Message", "Top");
                    }
                }
            });

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
                    intent.putExtra("mid", mid);
                    startActivity(intent);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setFloatingActionButton(final View view) {
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "up", Toast.LENGTH_SHORT).show();
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.main_f_btn:
                    intent = new Intent(MainActivity.this, AddressBookInsertActivity.class);
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("pw", pw);
                    intent.putExtra("mid", mid);
                    startActivity(intent);
                    break;
                case R.id.main_f_update:
                    urlAddr2 = "http://" + macIP + ":8080/humanconnect/infoSelect.jsp?";
                    urlAddr2 = urlAddr2 + "mid=" + mid;

                    try {
                        NetworkTask networkTask = new NetworkTask(MainActivity.this, urlAddr2, "select");
                        Object obj = networkTask.execute().get();
                        members = (ArrayList<Member>) obj;

                        intent = new Intent(MainActivity.this, InfoActivity.class);
                        Log.v("Message", "memberName " + macIP);
                        intent.putExtra("macIP", macIP);
                        Log.v("Message", "memberName " + members.get(0).getName());
                        intent.putExtra("name", members.get(0).getName());
                        intent.putExtra("pw", members.get(0).getPw());
                        intent.putExtra("mid", members.get(0).getMid());
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };

}
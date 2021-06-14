package com.aoslec.humanconnect.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aoslec.humanconnect.Adapter.AddressBookAdapter;
import com.aoslec.humanconnect.Bean.AddressBook;
import com.aoslec.humanconnect.NetworkTask.NetworkTaskSelect;
import com.aoslec.humanconnect.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    public MainFragment(){

    }
    String macIP, pw, urlAddr = null;
    int mid = 0;
    FloatingActionButton fab = null;
    RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;
    ArrayList<AddressBook> addressBooks, filteredList = null;
    AddressBookAdapter adapter = null;
    EditText editText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        connectGetData();

        return inflater.inflate(R.layout.fragment_main, container, false);

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


    private void connectGetData(){
        try {
            fab = getView().findViewById(R.id.main_f_btn);
            recyclerView = getView().findViewById(R.id.main_recycler);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            editText = getView().findViewById(R.id.main_edit);

            filteredList = new ArrayList<>();

            //Intent intent = getIntent();
            //macIP = intent.getStringExtra("macIP");
            //mid = intent.getIntExtra("mid", 0);
            urlAddr = "http://" + macIP + ":8080/humanconnect/addressBookSearch.jsp?";
            urlAddr = urlAddr + "mid=" + mid;
            Log.v("Message", urlAddr);
            fab.setOnClickListener(onClickListener);

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

            NetworkTaskSelect networkTask = new NetworkTaskSelect(getActivity(), urlAddr, "selectAll");
            Object obj = networkTask.execute().get();
            addressBooks = (ArrayList<AddressBook>) obj;
            // NetworkTask 일 끝남

            // 이제 adapter 일 시작
            adapter = new AddressBookAdapter(getActivity(), R.layout.main_layout, addressBooks);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new AddressBookAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    Intent intent = null;
                    intent = new Intent(getActivity(), AddressBookDetailActivity.class);
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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            intent = new Intent(getActivity(), AddressBookInsertActivity.class);
            intent.putExtra("macIP", macIP);
            intent.putExtra("pw", pw);
            intent.putExtra("mid", mid);
            startActivity(intent);
        }
    };
}
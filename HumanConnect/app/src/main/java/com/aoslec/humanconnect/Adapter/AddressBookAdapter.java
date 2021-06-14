package com.aoslec.humanconnect.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.humanconnect.Activity.AddressBookDetailActivity;
import com.aoslec.humanconnect.Activity.MainActivity;
import com.aoslec.humanconnect.Bean.AddressBook;
import com.aoslec.humanconnect.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.ViewHolder> {

    private Context mContext = null;
    private int layout = 0;
    private ArrayList<AddressBook> addressBooks = null;
    private LayoutInflater inflater = null;
//    private OnListItemSelectedInterface mListener;
//    private OnListItemLongSelectedInterface mLongListener;
    private OnItemClickListener mListener = null;
    private OnItemLongClickListener mLongListener = null;
    private ArrayList<AddressBook> unFilteredlist;
    private ArrayList<AddressBook> filteredList;

    public AddressBookAdapter(Context mContext, int layout, ArrayList<AddressBook> addressBooks){
        this.mContext = mContext;
        this.layout = layout;
        this.addressBooks = addressBooks;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void  filterList(ArrayList<AddressBook> filteredList) {
        addressBooks = filteredList;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener
    {
        void onItemClick(View v, int pos);
    }

    public interface OnItemLongClickListener
    {
        void onItemLongClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener)
    {
        this.mLongListener = listener;
    }

//    public AddressBookAdapter(Context mContext, OnListItemSelectedInterface listener, OnListItemLongSelectedInterface longListener){
//        this.mContext = mContext;
//        this.mListener = listener;
//        this.mLongListener = longListener;
//    }
//
//    public interface OnListItemLongSelectedInterface {
//        void onItemLongSelected(View v, int position);
//    }
//
//    public interface OnListItemSelectedInterface {
//        void onItemSelected(View v, int position);
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public ViewHolder(View addressBookView){
            super(addressBookView);
            name = addressBookView.findViewById(R.id.main_name_list);

            addressBookView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
//                        Intent intent = null;
//                        intent = new Intent(MainActivity.class, AddressBookDetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.putExtra("name", addressBooks.get(position).getName());
//                        mContext.startActivity(intent);
                        mListener.onItemClick(v, position);
                    }

                    //Snackbar.make(addressBookView, addressBooks.get(position).getName() + "is Clicked", Snackbar.LENGTH_SHORT).setAction("", null).show();
//                    Toast.makeText(addressBookView, addressBooks.get(position).getName() + "is Clicked", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_layout, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressBookAdapter.ViewHolder holder, int position) {
        holder.name.setText(addressBooks.get(position).getName());

        Log.v("Message", "setText " + addressBooks.get(position).getName());
    }

    @Override
    public int getItemCount() {
        Log.v("Message", "Count " + Integer.toString(addressBooks.size()));
        return addressBooks.size();
    }
}

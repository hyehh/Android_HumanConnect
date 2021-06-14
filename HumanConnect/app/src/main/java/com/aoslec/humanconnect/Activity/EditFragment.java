package com.aoslec.humanconnect.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aoslec.humanconnect.R;

public class EditFragment extends Fragment {

    public EditFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v("Message", "onCreateView_Tab1");
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }
}
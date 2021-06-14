package com.aoslec.humanconnect.Activity;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {
    // 탭 넘겨주는 adapter 생성

    // Property
    int tabCount;

    // Constructor
    public TabPagerAdapter(FragmentManager fm, int behavior){
        super(fm, behavior);
        this.tabCount = behavior;
    }

    @Override
    public Fragment getItem(int position) {
        Log.v("Message", "getItem_Adapter");
        switch (position){
            case 0:
                MainFragment mainFragment = new MainFragment();
                return mainFragment;
            case 1:
                EditFragment editFragment = new EditFragment();
                return editFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        Log.v("Message", "getCount_Adapter");
        return tabCount;
    }
}
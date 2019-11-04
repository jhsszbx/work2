package com.example.myapplication.work2.informationFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MessageFragmentAdapter extends FragmentPagerAdapter {

    public MessageFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0){
            return new MessageFragment();
        }
        return new ManageFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}

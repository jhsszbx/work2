package com.example.myapplication.work2.informationFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class StaffFragmentAdapter extends FragmentPagerAdapter {


    public StaffFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0){
            return new InOutMessageFragment();
        }else if(i==1){
            return new ApplyForMessageFragment();
        }else{

            return new StaffmanageFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}

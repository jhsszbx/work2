package com.example.myapplication.work2.informationFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.work2.R;
import com.flyco.tablayout.SlidingTabLayout;


// 在InformationFragment中再嵌套Fragment

public class ManageFragment extends Fragment {

    private SlidingTabLayout slidingTabLayout;
    private NoScrollViewPager viewPager;
    private String[] titles = {"出入消息","申请消息","员工管理"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage, container, false);
        slidingTabLayout = view.findViewById(R.id.slidingTabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager.setAdapter(new StaffFragmentAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(titles.length);
        slidingTabLayout.setViewPager(viewPager,titles);
        slidingTabLayout.setCurrentTab(0);

    }
}

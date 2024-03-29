package com.example.myapplication.work2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.work2.R;
import com.example.myapplication.work2.informationFragment.MessageFragmentAdapter;
import com.example.myapplication.work2.informationFragment.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;

import io.reactivex.annotations.Nullable;

public class InformationFragment extends Fragment {

    private View view;
    private SlidingTabLayout slidingTabLayout;
    private NoScrollViewPager viewPager;
    private String[] titles = {"消息","管理"};

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_information, container, false);
        slidingTabLayout = view.findViewById(R.id.slidingTabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager.setAdapter(new MessageFragmentAdapter(getChildFragmentManager()));
        slidingTabLayout.setViewPager(viewPager,titles);
        slidingTabLayout.setCurrentTab(0);
    }
}

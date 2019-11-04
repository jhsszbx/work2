package com.example.myapplication.work2.informationFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.work2.R;

import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MessageFragment extends Fragment {

    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private CommonAdapter commonAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_message, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commonAdapter = new CommonAdapter(new ArrayList<String>());
        recyclerView.setAdapter(commonAdapter);
        LinearDividerDecoration linearDividerDecoration = new LinearDividerDecoration(commonAdapter, new ColorDrawable(Color.GRAY), 1);
        recyclerView.addItemDecoration(linearDividerDecoration);
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<String> list = new ArrayList<>();
                for(int i=0; i<10; i++){
                    list.add("测试消息。。。。"+i);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commonAdapter.setNewData(list);
                    }
                });
            }
        }).start();
    }
}

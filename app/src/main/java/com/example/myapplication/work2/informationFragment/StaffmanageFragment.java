package com.example.myapplication.work2.informationFragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.work2.R;
import com.example.myapplication.work2.informationFragment.CommonAdapter;
import com.example.myapplication.work2.informationFragment.LinearDividerDecoration;

import java.util.ArrayList;
import java.util.List;

/*
* 员工管理
* */
public class StaffmanageFragment extends Fragment {

    private RecyclerView recyclerView;
    private CommonAdapter commonAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_manage, container, false);
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
                    list.add("员工：张三"+i+"工龄："+i);
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

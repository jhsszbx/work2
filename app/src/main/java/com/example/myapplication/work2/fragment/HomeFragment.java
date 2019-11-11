package com.example.myapplication.work2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.work2.HomeListAdapter;
import com.example.myapplication.work2.R;
import com.example.myapplication.work2.code.QrCodeActivity;
import com.example.myapplication.work2.recycleView.HomeListRecyclerView;
import com.example.myapplication.work2.staticTable.Home;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeListRecyclerView homeListRecyclerView;
    private List<Home> homeList = new ArrayList<>();

    private View view;

    private String userAccount;
    private String userPassword;
    private String userId;
    private String userType;

    private Button btQrCode;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init() {
        btQrCode = getView().findViewById(R.id.home_bt_qrcode);
        btQrCode.setOnClickListener(this);

        homeListRecyclerView = getView().findViewById(R.id.home_recyclerView);
        Home shenqing = new Home("申请进出权限", R.drawable.ic_home_black_24dp);
        homeList.add(shenqing);

        HomeListAdapter homeListAdapter = new HomeListAdapter(getContext(), homeList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        homeListRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        homeListRecyclerView.setAdapter(homeListAdapter);
    }

    // 接收数据库发来的数据，但是要到MainActivity中调用
    // 关键：因为系统运行的速度要比网络请求的速度要快，可能系统运行完了，网络还没请求到数据
    public void initData() {


        // 接收登录的账号
        userAccount = getArguments().getString("userAccount");
        userPassword = getArguments().getString("userPassword");
        userId = getArguments().getString("userId");
        userType = getArguments().getString("userType");
        Log.e("Main接收登录的账号", ""+userAccount + userPassword +userId);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_bt_qrcode:
                Intent itQrcode = new Intent(getActivity(), QrCodeActivity.class);
                itQrcode.putExtra("userPhone", userAccount);
                itQrcode.putExtra("userPassword", userPassword);
                itQrcode.putExtra("userId", userId);
                itQrcode.putExtra("userAccountAndPassword", userAccount + userPassword +userId);
                itQrcode.putExtra("userType", userType);
                startActivity(itQrcode);
                break;
        }
    }
}

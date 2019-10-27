package com.example.myapplication.work2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.work2.R;
import com.example.myapplication.work2.code.QrCodeActivity;

public class HomeFragment extends Fragment implements View.OnClickListener{

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

    }

    public void init() {

        // 接收登录的账号
        userAccount = getArguments().getString("userAccount");
        userPassword = getArguments().getString("userPassword");
        userId = getArguments().getString("userId");
        userType = getArguments().getString("userType");
        Log.e("Main接收登录的账号", ""+userAccount + userPassword +userId);


        btQrCode = getView().findViewById(R.id.home_bt_qrcode);

        btQrCode.setOnClickListener(this);
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

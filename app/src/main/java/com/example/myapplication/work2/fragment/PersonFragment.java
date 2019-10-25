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

import com.example.myapplication.work2.MainActivity;
import com.example.myapplication.work2.R;
import com.example.myapplication.work2.code.CustomZxingView;
import com.example.myapplication.work2.code.QrCodeActivity;
import com.example.myapplication.work2.code.ScanActivity;

import static android.support.constraint.Constraints.TAG;

public class PersonFragment extends Fragment implements View.OnClickListener{


    private View view;

    private TextView tvUserAccount;
    private Button btScan;
    private Button btZxingScan;

    private String userAccount;
    private String userPassword;

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_person, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init() {
//        tvUserAccount = view.findViewById(R.id.person_account);
        tvUserAccount = getView().findViewById(R.id.person_account);
        btScan = getView().findViewById(R.id.person_scan);
        btZxingScan = getView().findViewById(R.id.person_scan_zxing);
        // 接收登录的账号
        userAccount = getArguments().getString("userAccount");
        userPassword = getArguments().getString("userPassword");
        Log.e("接收登录的账号", ""+userAccount + userPassword);

        tvUserAccount.setText(userAccount);
        btScan.setOnClickListener(this);
        btZxingScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.person_scan:
                Intent itScan = new Intent(getActivity(), ScanActivity.class);
                startActivity(itScan);
                break;
            case R.id.person_scan_zxing:
                Intent itZxingScan = new Intent(getActivity(), CustomZxingView.class);
                startActivity(itZxingScan);
        }
    }
}

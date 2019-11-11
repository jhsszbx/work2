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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.work2.personalConter.PersonalCenterActivity;
import com.example.myapplication.work2.staticTable.Person;
import com.example.myapplication.work2.PersonListAdapter;
import com.example.myapplication.work2.R;
import com.example.myapplication.work2.code.ScanningActivity;
import com.example.myapplication.work2.recycleView.PersonListRecyclerView;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

public class PersonFragment extends Fragment implements View.OnClickListener{

    private List<Person> personList = new ArrayList<>();

    //自定义recyclerveiw的适配器
    private PersonListRecyclerView personListRecyclerView;

    private View view;

    private TextView tvUserAccount;
    private Button btScan;
    private ImageView ivScan;
    private Button btZxingScan;
    private ImageView ivPersonalConter;

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

    private void initPerson() {
        personListRecyclerView = getView().findViewById(R.id.person_list);

        Person shezhi = new Person("设置", R.drawable.black_background, R.drawable.black_background);
        personList.add(shezhi);
        Person guanyu = new Person("关于", R.drawable.black_background, R.drawable.black_background);
        personList.add(guanyu);
//        Log.e("查看", ""+personList);
        PersonListAdapter personListAdapterAdapter = new PersonListAdapter(getContext(), personList);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        personListRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        personListRecyclerView.setAdapter(personListAdapterAdapter);
    }

    private void init() {
//        tvUserAccount = view.findViewById(R.id.person_account);
        tvUserAccount = getView().findViewById(R.id.person_phone);
        ivScan = getView().findViewById(R.id.person_scan);
        ivPersonalConter = getView().findViewById(R.id.person_personal_center);

        initPerson();
//        btZxingScan = getView().findViewById(R.id.person_scan_zxing);
        // 接收登录的账号
        userAccount = getArguments().getString("userAccount");
        userPassword = getArguments().getString("userPassword");
        Log.e("接收登录的账号", ""+userAccount + userPassword);

        tvUserAccount.setText(userAccount);
        ivScan.setOnClickListener(this);
        ivPersonalConter.setOnClickListener(this);
//        btZxingScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.person_scan:
//                Intent itScan = new Intent(getActivity(), ScanActivity.class);
//                startActivity(itScan);
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
                // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setCaptureActivity(ScanningActivity.class);
                integrator.setPrompt("将二维码放到框内即可"); //底部的提示文字，设为""可以置空
                integrator.setCameraId(0); //前置或者后置摄像头
                integrator.setBeepEnabled(true); //扫描成功的「哔哔」声，默认开启
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
                break;
            case R.id.person_personal_center:
                Intent itPersonalConter = new Intent(getActivity(), PersonalCenterActivity.class);
                startActivity(itPersonalConter);
                break;
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        //扫码结果
//        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//        String scanId = intentResult.getContents();
//        String a = scanId.substring(10);
//
//        Toast.makeText(getContext(), "扫码结果" +a, Toast.LENGTH_LONG).show();
//
//        if (intentResult != null) {
//            if (intentResult.getContents() == null) {
//                //扫码失败
//            } else {
//                String result = intentResult.getContents();//返回值
//            }
//        }
//    }
}

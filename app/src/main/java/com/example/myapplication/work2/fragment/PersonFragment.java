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
import android.widget.Toast;

import com.example.myapplication.work2.MainActivity;
import com.example.myapplication.work2.R;
import com.example.myapplication.work2.code.CustomZxingView;
import com.example.myapplication.work2.code.QrCodeActivity;
import com.example.myapplication.work2.code.ScanActivity;
import com.example.myapplication.work2.code.ScanningActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

//                new IntentIntegrator(this)
//                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)// 扫码的类型,可选：一维码，二维码，一/二维码
//                        //.setPrompt("请对准二维码")// 设置提示语
//                        .setCameraId(0)// 选择摄像头,可使用前置或者后置
//                        .setBeepEnabled(true)// 是否开启声音,扫完码之后会"哔"的一声
//                        .initiateScan();// 初始化扫码

                break;
            case R.id.person_scan_zxing:
//                Intent itZxingScan = new Intent(getActivity(), CustomZxingView.class);
//                startActivity(itZxingScan);

                IntentIntegrator integrator2 = IntentIntegrator.forSupportFragment(this);
                // 设置要扫描的条码类型，ONE_D_CODE_TYPES：一维码，QR_CODE_TYPES-二维码
                integrator2.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator2.setPrompt("将二维码放到框内即可"); //底部的提示文字，设为""可以置空
                integrator2.setCameraId(0); //前置或者后置摄像头
                integrator2.setBeepEnabled(true); //扫描成功的「哔哔」声，默认开启
                integrator2.setBarcodeImageEnabled(true);
                integrator2.initiateScan();
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

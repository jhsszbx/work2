package com.example.myapplication.work2.code;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.work2.HomeService;
import com.example.myapplication.work2.MainActivity;
import com.example.myapplication.work2.R;
import com.example.myapplication.work2.registrationAndLogin.Login;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

/**
 * 扫描二维码
 * Created by ypp on 2018/5/16.
 */

public class ScanningActivity extends AppCompatActivity {

    private final String TAG="ScanningActivity";
    private CustomerCaptureManager captureManager;//扫描
    private DecoratedBarcodeView dbv_custom;

    private String userPhoneAndPasswordAndId;
    private boolean bl;

    /**
     * 当前闪光灯是否打开了
     */
    private boolean isTorchOn=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scan);
        dbv_custom=(DecoratedBarcodeView)findViewById(R.id.decoratedBarcodeView);

        //重要代码，初始化捕获
        initCapture();
//        isScanHandler.sendEmptyMessageDelayed(0,30000);//30秒未扫描到，提示

    }

    public void onClick_selectTheUser(String userPhoneAndPasswordAndId) {
        //step1:实例化Retrofit对象
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(HomeService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        //step2:获取APIService实例
        HomeService apiService = retrofit.create(HomeService.class);

        //step3:通过apiService调用call  http://gank.io/api/data/Android/10/1
        Call<Boolean> gankCall = apiService.selectTheUser(userPhoneAndPasswordAndId);
        Log.d("CallGoods", gankCall + "");


        //step4:通过异步获取数据
        gankCall.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.e(TAG, "onResponse: 地址请求成功！！！");
                // 登录接收服务端的判断是否有这个用户
                bl = response.body();
                if (bl == true){
                    Toast.makeText(ScanningActivity.this, "登录接收服务端的判断是否有这个用户:"+bl, Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ScanningActivity.this, "登录接收服务端的判断是否有这个用户:"+bl, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e(TAG, "onFailure: 请求失败！！！！~~~~~");
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }


    /**
     * 初始化捕捉
     */
    private void initCapture(){
        if(captureManager!=null){
            captureManager.onDestroy();
            captureManager=null;
        }
        captureManager = new CustomerCaptureManager(this,dbv_custom,scanningListener);
        captureManager.initializeFromIntent(getIntent(),null);
        captureManager.decode();
    }
    /**
     * 扫描结果监听
     */
    private CustomerCaptureManager.ScanningListener scanningListener=new CustomerCaptureManager.ScanningListener(){

        @Override
        public void scanResult(String result) {
            // 获取二维码的字符串result
            userPhoneAndPasswordAndId = result.substring(10);
            onClick_selectTheUser(userPhoneAndPasswordAndId);
            Toast.makeText(ScanningActivity.this, userPhoneAndPasswordAndId, Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initCapture();
                }
            },500);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return dbv_custom.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
    public void onResume()
    {
        super.onResume();
        captureManager.onResume();
    }

    public void onPause()
    {
        super.onPause();
        captureManager.onPause();
    }

    /**
     * 切换闪光灯
     * @param view
     */
    public void openOrCloseTorch(View view) {
        if(isTorchOn){
            if(hasTorch()){
//                TextView flash_btn=(TextView)findViewById(R.id.flash_btn);
//                flash_btn.setBackgroundResource(R.drawable.close_flash_light_bg);
//                flash_btn.setText(getResources().getString(R.string.open_flash_light));
                dbv_custom.setTorchOff();
            }

        }else{
            if(hasTorch()){
//                TextView flash_btn=(TextView)findViewById(R.id.flash_btn);
//                flash_btn.setBackgroundResource(R.drawable.open_flash_light_bg);
//                flash_btn.setText(getResources().getString(R.string.close_flash_light));
                dbv_custom.setTorchOn();
            }

        }
        isTorchOn=!isTorchOn;
    }
    /**
     * 判断是否有闪光灯
     * @return
     */
    public boolean hasTorch(){
        return getApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
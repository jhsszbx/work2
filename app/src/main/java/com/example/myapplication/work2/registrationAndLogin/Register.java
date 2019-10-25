package com.example.myapplication.work2.registrationAndLogin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.myapplication.work2.HomeService;
import com.example.myapplication.work2.MainActivity;
import com.example.myapplication.work2.R;
import com.example.myapplication.work2.table.User;
import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText etPhoneNumber;        // 电话号码
    private EditText etVerificationCode;   // 验证码
    private EditText etUserPassword;       // 用户密码

    private Button sendVerificationCode;   // 发送验证码
    private Button nextStep;               // 下一步

    private String phoneNumber;         // 电话号码
    private String userPassword;        // 用户密码
    private String verificationCode;    // 验证码

    private boolean flag;   // 操作是否成功

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init(); // 初始化控件、注册点击事件

        final Context context = Register.this;                       // context
        final String AppKey = "2c75818a5491a";                       // AppKey
        final String AppSecret = "598f98cdb98b58ea593095833df4fc2c"; // AppSecret

        MobSDK.init(context,AppKey,AppSecret);                 // 初始化 SDK 单例，可以多次调用     ( 新版本 )

        //SMSSDK.initSDK(context, AppKey, AppSecret);           // 初始化 SDK 单例，可以多次调用    (3.0 后官方换了一种方法)

        EventHandler eventHandler = new EventHandler(){       // 操作回调
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);     // 注册回调接口


        initToolbar();

    }


    private void initToolbar() {
        // Toolbar 结束返回上级
        toolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //添加点击返回箭头事件
        // 设置导航（返回图片）的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出当前页面
                finish();
            }
        });
    }

    private void init() {
        etPhoneNumber = (EditText) findViewById(R.id.edit_phone_number);
        sendVerificationCode = (Button) findViewById(R.id.btn_send_verification_code);
        etVerificationCode = (EditText) findViewById(R.id.edit_verification_code);
        etUserPassword = findViewById(R.id.edit_password_number);
        nextStep = (Button) findViewById(R.id.btn_next_step);
        sendVerificationCode.setOnClickListener(this);
        nextStep.setOnClickListener(this);
    }

    public void onClick_addUserPhone(String userPhone, String userPassword) {
        //step1:实例化Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HomeService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //step2:获取APIService实例
        HomeService apiService = retrofit.create(HomeService.class);

        //step3:通过apiService调用call  http://gank.io/api/data/Android/10/1
        final Call<User> gankCall= apiService.addUserPhone(userPhone, userPassword);
        Log.d("CallGoods",gankCall + "");

        //step4:通过异步获取数据
        gankCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e(TAG, "onResponse: 地址请求成功！！！" );
                //商品
                Log.d("111111", "onResponse: "+ response.body());

                User user = response.body();

                Log.d("Goodsgoods",user + "");
                Log.d("Name",""+user.getUserPhone());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: 请求失败！！！！~~~~~" );
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_verification_code:
                if (!TextUtils.isEmpty(etPhoneNumber.getText())) {
                    if (etPhoneNumber.getText().length() == 11) {
                        phoneNumber = etPhoneNumber.getText().toString();
                        SMSSDK.getVerificationCode("86", phoneNumber); // 发送验证码给号码的 phoneNumber 的手机
                        Toast.makeText(this, "验证码已发送请等待！", Toast.LENGTH_SHORT).show();
                        etVerificationCode.requestFocus();
                    }
                    else {
                        Toast.makeText(this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();
                        etPhoneNumber.requestFocus();
                    }
                } else {
                    Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    etPhoneNumber.requestFocus();
                }
                break;

            case R.id.btn_next_step:
                if (!TextUtils.isEmpty(etVerificationCode.getText())) {
                    if (etVerificationCode.getText().length() == 4) {
                        verificationCode = etVerificationCode.getText().toString();
                        SMSSDK.submitVerificationCode("86", phoneNumber, verificationCode);
                        flag = false;
                    } else {
                        Toast.makeText(this, "请输入完整的验证码", Toast.LENGTH_SHORT).show();
                        etVerificationCode.requestFocus();
                    }
                } else {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    etVerificationCode.requestFocus();
                }
                break;

            default:
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;

            if (result == SMSSDK.RESULT_COMPLETE) {
                // 如果操作成功
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 校验验证码，返回校验的手机和国家代码
                    Toast.makeText(Register.this, "验证成功", Toast.LENGTH_SHORT).show();
                    phoneNumber = etPhoneNumber.getText().toString();
                    userPassword = etUserPassword.getText().toString();
                    onClick_addUserPhone(phoneNumber, userPassword);

                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 获取验证码成功，true为智能验证，false为普通下发短信
                    Toast.makeText(Register.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    // 返回支持发送验证码的国家列表
                }
            } else {
                // 如果操作失败
                if (flag) {
                    Toast.makeText(Register.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    etPhoneNumber.requestFocus();
                } else {
                    ((Throwable) data).printStackTrace();
                    Toast.makeText(Register.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();  // 注销回调接口
    }

}

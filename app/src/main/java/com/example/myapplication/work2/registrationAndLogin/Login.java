package com.example.myapplication.work2.registrationAndLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.work2.HomeService;
import com.example.myapplication.work2.MainActivity;
import com.example.myapplication.work2.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button next;
    private EditText etUserAccount;
    private EditText etUserPassword;

    private String userPhone;
    private String userPassword;

    private Toolbar toolbar;

    private Boolean bl;
    private int userId;
    private String userType;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        initToolbar();
    }

    private void init() {
        next = findViewById(R.id.login_btn_next_step);
        etUserAccount = findViewById(R.id.login_edit_account);
        etUserPassword = findViewById(R.id.login_edit_password);
        next.setOnClickListener(this);
    }

    private void initToolbar() {
        // Toolbar 结束返回上级
        toolbar = findViewById(R.id.login_toolbar);
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

    public void onClick_getUserId(final String userPhone, final String userPassword) {
        //step1:实例化Retrofit对象
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(HomeService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        //step2:获取APIService实例
        HomeService apiService = retrofit.create(HomeService.class);

        //step3:通过apiService调用call  http://gank.io/api/data/Android/10/1
        Call<Integer> gankCall = apiService.getUserId(userPhone, userPassword);
        Log.d("CallGoods", gankCall + "");


        //step4:通过异步获取数据
        gankCall.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.e(TAG, "onResponse: 地址请求成功！！！");
                // 登录接收服务端的id
                userId = response.body();

                Log.e("登录接收服务端的id", "" + userId);
                if (userId != 0) {
                    Toast.makeText(Login.this, "登录成功" + bl, Toast.LENGTH_LONG).show();
                    Intent itLogin = new Intent(Login.this, MainActivity.class);

                    // 使用Bundle传递值
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", "" + userId);
                    bundle.putString("userPhone", userPhone);
                    bundle.putString("userPassword", userPassword);
                    Log.e("使用Bundle传递值userId", "" + userId);

                    Log.e("使用Bundle传递值", "" + userPhone);

                    itLogin.putExtras(bundle);


                    startActivity(itLogin);
                } else {
                    Toast.makeText(Login.this, "登录失败" + bl, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e(TAG, "onFailure: 请求失败！！！！~~~~~");
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_next_step:
                userPhone = etUserAccount.getText().toString();
                userPassword = etUserPassword.getText().toString();
                onClick_getUserId(userPhone, userPassword);

                //onClick_login(userAccount, userPassword, userId);

                break;
            default:
                break;
        }
    }
}

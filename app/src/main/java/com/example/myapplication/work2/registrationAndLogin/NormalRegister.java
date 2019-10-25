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

import com.example.myapplication.work2.HomeService;
import com.example.myapplication.work2.R;
import com.example.myapplication.work2.table.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class NormalRegister extends AppCompatActivity implements View.OnClickListener{

    private Button nextButton;
    private Button btExistingAccount;
    private EditText etUserAccount;
    private EditText etUserPassword;

    private String userAccount;
    private String userPassword;

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normalregister);

        init();
        initToolbar();
    }

    private void init() {
        nextButton = findViewById(R.id.normalregister_btn_next_step);
        btExistingAccount = findViewById(R.id.normalregister_btn_existing_account);
        etUserAccount = findViewById(R.id .normalregister_edit_account);
        etUserPassword = findViewById(R.id.normalregister_edit_password);
        nextButton.setOnClickListener(this);
        btExistingAccount.setOnClickListener(this);
    }

    private void initToolbar() {
        // Toolbar 结束返回上级
        toolbar = findViewById(R.id.normalregister_toolbar);
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


    public void onClick_addUser(String userAccount, String userPassword) {
        //step1:实例化Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HomeService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //step2:获取APIService实例
        HomeService apiService = retrofit.create(HomeService.class);

        //step3:通过apiService调用call  http://gank.io/api/data/Android/10/1
        final Call<User> gankCall= apiService.addUser(userAccount, userPassword);
        Log.d("CallGoods",gankCall + "");

        //step4:通过异步获取数据
        gankCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e(TAG, "onResponse: 地址请求成功！！！" );
                //商品
                Log.d("111111", "onResponse: "+ response.body());

                User user = response.body();

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
        switch (v.getId()){
            case R.id.normalregister_btn_next_step:
                userAccount = etUserAccount.getText().toString();
                userPassword = etUserPassword.getText().toString();
                onClick_addUser(userAccount, userPassword);
                Intent intent = new Intent(NormalRegister.this, Login.class);
                startActivity(intent);
                break;
            case R.id.normalregister_btn_existing_account:
                Intent itExistingAccount = new Intent(NormalRegister.this, Login.class);
                startActivity(itExistingAccount);
                break;
            default:
                break;
        }
    }

}

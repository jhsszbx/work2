package com.example.myapplication.work2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;


import com.example.myapplication.work2.fragment.HomeFragment;
import com.example.myapplication.work2.fragment.InformationFragment;
import com.example.myapplication.work2.fragment.PersonFragment;
import com.example.myapplication.work2.table.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOError;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private FragmentTransaction fragmentTransaction;
    private int lastIndex;
    private List<Fragment> fragments;

    private String userPhone;
    private String userPassword;
    private String userId;
    private static String userType;

    Bundle bundle = new Bundle();

    HomeFragment homeFragment = new HomeFragment();
    InformationFragment informationFragment = new InformationFragment();
    PersonFragment personFragment = new PersonFragment();

    Gson gson = new GsonBuilder().setLenient().create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListener();
        initFragment();

    }

    public void setListener() {
        mBottomNavigationView = findViewById(R.id.nav_view);
        // 解决当item大于三个时，非平均布局问题
        // BottomNavigationViewHelper.disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        setFragmentPosition(0);
                        break;
                    case R.id.navigation_dashboard:
                        setFragmentPosition(1);
                        break;
                    case R.id.navigation_person:
                        setFragmentPosition(2);
                        break;
                    default:
                        break;
                }
                // 这里注意返回true,否则点击失效
                return true;
            }
        });
    }

    private void initFragment() {



        fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(informationFragment);
        fragments.add(personFragment);

        // 初始化展示MessageFragment
        setFragmentPosition(0);


        // 接收Login发送过来的账号密码
        Intent intent = getIntent();
        userPhone = intent.getStringExtra("userPhone");
        userPassword = intent.getStringExtra("userPassword");
        userId = intent.getStringExtra("userId");


        Log.e("接收Login发送过来的userAccount", "" + userPhone);
        Log.e("接收Login发送过来的userPassword", "" + userPassword);
        Log.e("接收Login发送过来的userId", "" + userId);
//        Log.e("接收Login发送过来的userType", "" + userType);

        onClick_getUserType(userPhone, userPassword);
//        Log.e("成功从onClick_getUserType接收userType", userType);

    }


    public void onClick_getUserType(final String userPhone, final String userPassword){


        //step1:实例化Retrofit对象
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(HomeService.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        //step2:获取APIService实例
        HomeService apiService = retrofit.create(HomeService.class);

        //step3:通过apiService调用call  http://gank.io/api/data/Android/10/1
        Call<User> gankCall = apiService.getUserType(userPhone, userPassword);
        Log.d("CallGoods", gankCall + "");

        //step4:通过异步获取数据
        gankCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.e(TAG, "方法getUserTypeonResponse: 地址请求成功！！！");
                userType = response.body().getUserType();

                bundle.putString("userAccount", userPhone);
                bundle.putString("userPassword", userPassword);
                bundle.putString("userId", userId);
                bundle.putString("userType", userType);

                //  传递登录成功的信息到“我的”Fragment中
                personFragment.setArguments(bundle);
                homeFragment.setArguments(bundle);
                informationFragment.setArguments(bundle);
                homeFragment.init();

                Log.e("成功从onClick_getUserType接收userType", userType);


//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Message msg=new Message();
//                        msg.obj=type;//message的内容
//                        msg.what=1;//指定message
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                onClick_getUserType(userPhone, userPassword);
//
//                            }
//                        });
//                        handler.sendMessage(msg);//handler发送message
//
//                    }
//                }).start();


                // 登录接收服务端的userType
//
//                Log.e("登录接收服务端的userType", type);
//                try{
//                    userType = type;
//                }catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                }

//                Bundle bundle = new Bundle();
//                Intent intent = new Intent();
//                intent.putExtra("userType", userType);
//                intent.putExtras(bundle);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "登录接收服务端的userType: 请求失败！！！！~~~~~");
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
//        Log.e("接收异步获取数据的userType", userType);

    }

    // 回收 Fragment
    private void setFragmentPosition(int position) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = fragments.get(position);
        Fragment lastFragment = fragments.get(lastIndex);
        lastIndex = position;
        fragmentTransaction.hide(lastFragment);
        if (!currentFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().remove(currentFragment).commit();
            fragmentTransaction.add(R.id.fl_content, currentFragment);
        }
        fragmentTransaction.show(currentFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }


}
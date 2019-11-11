package com.example.myapplication.work2.informationFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.work2.HomeService;
import com.example.myapplication.work2.R;
import com.example.myapplication.work2.recycleView.StaffMessageRecyclerView;
import com.example.myapplication.work2.table.Entryexit;
import com.example.myapplication.work2.table.User;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
* 员工管理
* */
public class StaffManageFragment extends Fragment {

    final static private String TAG = "StaffManageFragment";
    //自定义recyclerveiw的适配器
    private StaffMessageRecyclerView staffMessageRecyclerView;
    private RefreshLayout refreshLayout;
    private View view;

    private List<User> userList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_staff_manage, container, false);
        // 上拉刷新
        shangLaShuaXin();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        staffMessageRecyclerView = view.findViewById(R.id.staffmessage_recycleview);
        // InOutMessageFragment显示进出的人的信息；
        onClick_selectStaff();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void shangLaShuaXin() {
        refreshLayout = view.findViewById(R.id.staffmessage_refreshlayout);
        refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                // 需要刷新的方法
                onClick_selectStaff();
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }


    public void onClick_selectStaff() {
        //step1:实例化Retrofit对象
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(HomeService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        //step2:获取APIService实例
        HomeService apiService = retrofit.create(HomeService.class);

        //step3:通过apiService调用call  http://gank.io/api/data/Android/10/1
        Call<List<User>> gankCall = apiService.selectStaff();
        Log.d("CallGoods", gankCall + "");

        //step4:通过异步获取数据
        gankCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.e(TAG, "onResponse: 地址请求成功！！！");
                // 接收服务端的判断是否有这个用户
                // 扫码接收用户ID
                userList = response.body();

                StaffMessageAdapter staffMessageAdapter = new StaffMessageAdapter(getContext(), userList);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                staffMessageRecyclerView.setLayoutManager(staggeredGridLayoutManager);
                staffMessageRecyclerView.setAdapter(staffMessageAdapter);

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG, "onClick_selectThreeDayEntryexit: 请求失败！！！！~~~~~");
                Log.e(TAG, "onClick_selectThreeDayEntryexit: " + t.getMessage());
            }
        });

    }


}

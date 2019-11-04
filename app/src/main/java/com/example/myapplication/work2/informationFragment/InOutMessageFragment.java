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
import com.example.myapplication.work2.recycleView.InOutMessageRecyclerView;
import com.example.myapplication.work2.table.Entryexit;
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
* 出入消息
* */
public class InOutMessageFragment extends Fragment {


    private final String TAG="InOutMessageFragment";

    private View view;
    private RefreshLayout refreshLayout;

    private List<Entryexit> entryexits = new ArrayList<>();

    //自定义recyclerveiw的适配器
    private InOutMessageRecyclerView inOutMessageRecycleView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_in_out_message, container, false);

        // 上拉刷新
        shangLaShuaXin();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inOutMessageRecycleView = view.findViewById(R.id.inoutmessage_recycleview);
        // InOutMessageFragment显示进出的人的信息；
        onClick_selectThreeDayEntryexit();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void shangLaShuaXin() {
        refreshLayout = view.findViewById(R.id.inoutmessage_refreshlayout);
        refreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                // 需要刷新的方法
                onClick_selectThreeDayEntryexit();
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

    public void onClick_selectThreeDayEntryexit() {
        //step1:实例化Retrofit对象
        final Retrofit retrofit = new Retrofit.Builder().baseUrl(HomeService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        //step2:获取APIService实例
        HomeService apiService = retrofit.create(HomeService.class);

        //step3:通过apiService调用call  http://gank.io/api/data/Android/10/1
        Call<List<Entryexit>> gankCall = apiService.selectEntryexitTime();
        Log.d("CallGoods", gankCall + "");

        //step4:通过异步获取数据
        gankCall.enqueue(new Callback<List<Entryexit>>() {
            @Override
            public void onResponse(Call<List<Entryexit>> call, Response<List<Entryexit>> response) {
                Log.e(TAG, "onResponse: 地址请求成功！！！");
                // 接收服务端的判断是否有这个用户
                // 扫码接收用户ID
                entryexits = response.body();

                InoutMessageAdapterAdapter inoutMessageAdapterAdapter = new InoutMessageAdapterAdapter(getContext(), entryexits);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                inOutMessageRecycleView.setLayoutManager(staggeredGridLayoutManager);
                inOutMessageRecycleView.setAdapter(inoutMessageAdapterAdapter);


            }

            @Override
            public void onFailure(Call<List<Entryexit>> call, Throwable t) {
                Log.e(TAG, "onClick_selectThreeDayEntryexit: 请求失败！！！！~~~~~");
                Log.e(TAG, "onClick_selectThreeDayEntryexit: " + t.getMessage());
            }
        });

    }


}

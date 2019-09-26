package com.example.myapplication.work2;



import com.example.myapplication.work2.table.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HomeService {

    // 宿舍用
    public static final String BASE_URL = "http://192.168.1.103:8080/";

    // 红米wifi用
    public static final String BASE_URL2 = "http://192.168.43.221:8080/";

    // NVCT
    public static final String BASE_URL3 = "http://172.16.60.33:8080/";

    @GET("TestSSM/findAllGoods")
    Call<List<User>> getAllGoods();

    @GET("TestSSM/findGoodsId")
    Call<User> getGoodsId(@Query("commodityId") int commodityId);

    @GET("door2/addUserPhone")
    Call<User> addUserPhone(@Query("userPhone") String userPhone);


}

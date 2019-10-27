package com.example.myapplication.work2;



import com.example.myapplication.work2.table.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HomeService {

    // 宿舍用
    public static final String BASE_URL = "http://192.168.1.103:8080/door2/";

    // 红米wifi用
    public static final String BASE_URL2 = "http://192.168.43.221:8080/";

    // NVCT
    public static final String BASE_URL3 = "http://172.16.60.33:8080/";

    // 409 NCVT
    public static final String BASE_URL4 = "http://172.16.59.82:8080/door2/";

    @GET("TestSSM/findAllGoods")
    Call<List<User>> getAllGoods();

    @GET("TestSSM/findGoodsId")
    Call<User> getGoodsId(@Query("commodityId") int commodityId);

    @GET("addUser")
    Call<User> addUserPhone(String userPhone, String userPassword);

    @POST("addUser")
    Call<User> addUser(@Query("userPhone") String userPhone,@Query("userPassword") String userPassword);

    @POST("login")
    Call<Boolean> login(@Query("userAccount")String userPhone,@Query("userPassword") String userPassword);

    // 用户登录顺便提交Id
    @GET("getUserId")
    Call<Integer> getUserId(@Query("userPhone")String userPhone,@Query("userPassword") String userPassword);

    @POST("getUserType")
    Call<User> getUserType(@Query("userPhone")String userPhone, @Query("userPassword") String userPassword);

    // 扫二维码
    @GET("selectTheUser")
    Call<Boolean> selectTheUser(@Query("userPhoneAndPasswordAndId")String userPhoneAndPasswordAndId);

    @GET("selectUserType")
    Call<Integer> selectUserType(@Query("userPhone") String userPhone);

}

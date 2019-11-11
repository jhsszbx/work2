package com.example.myapplication.work2;



import android.database.Observable;

import com.example.myapplication.work2.table.Entryexit;
import com.example.myapplication.work2.table.User;
import com.google.gson.JsonObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;


public interface HomeService {

    // 宿舍用
    public static final String BASE_URL = "http://192.168.1.103:8080/door2/";

    // 红米wifi用
    public static final String BASE_URL2 = "http://192.168.43.221:8080/door2/";

    // NVCT
    public static final String BASE_URL3 = "http://172.16.60.33:8080/";

    // 409 NCVT
    public static final String BASE_URL4 = "http://172.16.52.18:8080/door2/";

    @GET("TestSSM/findAllGoods")
    Call<List<User>> getAllGoods();

    @GET("TestSSM/findGoodsId")
    Call<User> getGoodsId(@Query("commodityId") int commodityId);

    // 这下面是User的网络请求方法

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
    Call<Integer> selectTheUser(@Query("userPhoneAndPasswordAndId")String userPhoneAndPasswordAndId);

    @GET("selectUserType")
    Call<Integer> selectUserType(@Query("userPhone") String userPhone);

    // 这下面是Entryexit的网络方法

    // 获取进出的信息
    @GET("selectThreeDayEntryexit")
    Call<List<Entryexit>> selectThreeDayEntryexit();

    // 重新写的进出信息方法，可以获取到进出时间
    @GET("selectEntryexitTime")
    Call<List<Entryexit>> selectEntryexitTime();

    // 扫码过后添加信息到Entryexit，但没有添加扫码时间
    // 该方法返回是EntryexitId，可根据EntryexitId添加时间
    @GET("addEntryexit")
    Call<Integer> addEntryexit(@Query("userId")int userId);

    // 根据上个添加信息的方法，接收entryexitId，添加entryexitDate
    @GET("addEntryexitDate")
    Call<Boolean> addEntryexitDate(@Query("entryexitId")int entryexitId);

    // 显示员工管理
    @GET("selectStaff")
    Call<List<User>> selectStaff();



    // 用户上传头像
//    @Multipart
//    @POST("upload")
//    Call<ResponseBody> uploadImage(@Part("file") RequestBody file, int userId);

    //Post文件提交 ，每个键值对都需要用@Part注解键名字
    //Multipart 支持文件上传



}

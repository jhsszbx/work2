package com.example.myapplication.work2.code;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.work2.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QrCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private String randomCode;

    private String userPhone;
    private String userPassword;
    private String userId;
    private String userAccountAndPasswordAndId;
    private String userType;

    private TextView tvRandomCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        init();
    }

    private void init() {
        tvRandomCode = findViewById(R.id.qrcode_text);
        imageView = findViewById(R.id.imageView_zxing);
        imageView.setOnClickListener(this);

        // 接收HomeFragment传递来的数据
        Intent intent = getIntent();
        userAccountAndPasswordAndId = intent.getStringExtra("userAccountAndPassword");
        userPhone = intent.getStringExtra("userPhone");
        userPassword = intent.getStringExtra("userPassword");
        userId = intent.getStringExtra("userId");
        userType = intent.getStringExtra("userType");
        Log.e("接收HomeFragment传递来的数据userPhone:", ""+userPhone);
        Log.e("接收HomeFragment传递来的数据userPassword:", ""+userPassword);
        Log.e("接收HomeFragment传递来的数据userId:", ""+userId);
        Log.e("接收HomeFragment传递来的数据:", ""+userAccountAndPasswordAndId);
        Log.e("接收HomeFragment传递来的数据userType", ""+userType);

        // 生成第一张随机二维码的字符串
        // userAccountAndPassword是该用户的账号密码
        randomCode = getRandomString(10) + userAccountAndPasswordAndId;
        Log.e("生成第一张随机二维码的字符串", ""+randomCode);
        // 显示随机生成的二维码的字符串
        tvRandomCode.setText(randomCode);
        //生成二维码显示在imageView上
        imageView.setImageBitmap(generateBitmap(randomCode, 600, 600));

    }


    //length用户要求产生字符串的长度
    // 随机生成一串String
    public static String getRandomString(int length){
        String str ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }



    /**
     * 生成固定大小的二维码(不需网络权限)
     *
     * @param content 需要生成的内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @return
     */
    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_zxing:
                // 点击图片重新随机二维码
                // userAccountAndPassword是该用户的账号密码
                randomCode = getRandomString(10)+userAccountAndPasswordAndId;
                // 显示随机生成的二维码的字符串
                tvRandomCode.setText(randomCode);
                //生成二维码显示在imageView上
                imageView.setImageBitmap(generateBitmap(randomCode, 600, 600));
            break;
        }
    }


}

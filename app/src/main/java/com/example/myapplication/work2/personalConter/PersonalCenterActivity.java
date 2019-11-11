package com.example.myapplication.work2.personalConter;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.work2.MainActivity;
import com.example.myapplication.work2.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;


public class PersonalCenterActivity extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout constraintLayout;
    private Toolbar toolbar;
    private ImageView userImage;

    private ImageDispose imageDispose = new ImageDispose();
    private int image = R.drawable.ic_launcher_foreground;

    private Bitmap bitmap;
    private byte byteImage[] = new byte[1024*1024];//看你图有多大..自己看着改
    private Bitmap bitmapUserImage;
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        init();
    }

    private void init() {
        toolbar = findViewById(R.id.personal_conter_toolbar);
        constraintLayout = findViewById(R.id.personal_conter_headimage);
        userImage = findViewById(R.id.personal_conter_uploadimage);

        constraintLayout.setOnClickListener(this);
        initToolbar();
    }
    private void initToolbar(){
        setSupportActionBar(toolbar);
        //设置是否有NvagitionIcon（返回图标）
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏标题
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {   //为图标设置监听器
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private static Bitmap getBitmap(Context context,int vectorDrawableId) {
        Bitmap getBitmap = null;
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            getBitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(getBitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        }else {
            getBitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return getBitmap;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_conter_headimage:
                Intent intent = new Intent(PersonalCenterActivity.this, UploadPhotoActivity.class);
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image, null);
                bitmap = imageDispose.getBitmap(PersonalCenterActivity.this, image);
                byteImage = imageDispose.Bitmap2Bytes(bitmap);
                bundle.putByteArray("userImage", byteImage);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }


}

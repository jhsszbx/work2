package com.example.myapplication.work2.personalConter;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myapplication.work2.Main2Activity;
import com.example.myapplication.work2.R;
import com.example.myapplication.work2.volley.MultiPartStack;
import com.example.myapplication.work2.volley.MultiPartStringRequest;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

public class UploadPhotoActivity extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks{

    private final static String TAG = "UploadPhotoActivity";

    private ImageView imageView;
    private Button btYes;
    private Button btNo;
    private Bitmap bitmap;
    private Bitmap bitmapUpImage;

    private String photoPath;
    private byte byteImage[] = new byte[1024*1024];//看你图有多大..自己看着改

    private ImageDispose imageDispose = new ImageDispose();
    private byte buff[] = new byte[1024*1024];//看你图有多大..自己看着改

    //拍照照片路径
    private File cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + ".jpg");
    private Uri uri;//照片uri
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private File upLoadFile;


    private Button btn_string_get, btn_string_post, btn_upload;

    private static ProgressDialog mProgress;

    private RequestQueue requestQueue;
    private static RequestQueue mSingleQueue;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadphoto);

        init();
    }

    private void init() {
        imageView = findViewById(R.id.uploadphoto_headimage);
        btYes = findViewById(R.id.uploadphoto_yes);
        btNo = findViewById(R.id.uploadphoto_no);

        imageView.setOnClickListener(this);
        btYes.setOnClickListener(this);
        btNo.setOnClickListener(this);

        Intent intent=getIntent();
        if(intent!=null)
        {
            buff=intent.getByteArrayExtra("userImage");
            Log.e("接收PersonalCenterActivity的头像", ""+bitmap);
            bitmap = imageDispose.getPicFromBytes(buff, null);
            imageView.setImageBitmap(bitmap);
        }

        getPermission();
    }

    //列表对话框
    public void openlistdialog() {
        String[] uploadWay = new String[]{"拍照", "从相册中选择"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("请选择上传照片的方式：");
        builder.setItems(uploadWay, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int position) {
                // TODO Auto-generated method stub
                Toast.makeText(UploadPhotoActivity.this, "当前条目：" + position, Toast.LENGTH_SHORT).show();
                if(position==0){
                    goCamera();
                }else if (position==1){
                    goPhotoAlbum();
                }
            }
        });
        //创建，显示
        AlertDialog d = builder.create();
        d.show();
    }


    //获取权限
    private void getPermission() {
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //已经打开权限
            Toast.makeText(this, "已经申请相关权限", Toast.LENGTH_SHORT).show();
        } else {
            //没有打开相关权限、申请权限
            EasyPermissions.requestPermissions(this, "需要获取您的相册、照相使用权限", 1, permissions);
        }

    }

    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    //激活相机操作
    private void goCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(UploadPhotoActivity.this, "com.example.hxd.pictest.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        UploadPhotoActivity.this.startActivityForResult(intent, 1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //框架要求必须这么写
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    //成功打开权限
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

        Toast.makeText(this, "相关权限获取成功", Toast.LENGTH_SHORT).show();
    }
    //用户未同意权限
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "请同意相关权限，否则功能无法使用", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
                Log.d(TAG, "onActivityResult: FFFFFFFFFFFFFFFFFFFFFF:"+data.getData());
                //4.4及以上用此方法
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            Glide.with(UploadPhotoActivity.this).load(photoPath).into(imageView);



        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            // 拿到图片显示图片
            photoPath = GetPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            Glide.with(UploadPhotoActivity.this).load(photoPath).into(imageView);
        }

        Toast.makeText(this, "图片中的路径："+photoPath, Toast.LENGTH_LONG).show();
        Log.d("拍照返回图片路径:", photoPath);

    }


    // 确认上传头像
    public void uploadImage(File file){
        // 记住下面这句话
        // Android 图片转换成MultipartFile.getInputStream()
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uploadphoto_headimage:
                openlistdialog();
                break;
            case R.id.uploadphoto_yes:

                Bitmap bm =((BitmapDrawable) (imageView).getDrawable()).getBitmap();
                byteImage = imageDispose.Bitmap2Bytes(bm);
                upLoadFile = ImageDispose.getFileFromBytes(byteImage, photoPath);
                uploadImage(upLoadFile);


        }
    }


}

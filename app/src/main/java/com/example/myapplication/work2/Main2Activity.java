package com.example.myapplication.work2;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.work2.volley.MultiPartStack;
import com.example.myapplication.work2.volley.MultiPartStringRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {


    private static String TAG = "MainActivity";

    private Button btn_string_get, btn_string_post, btn_upload;

    private static ProgressDialog mProgress;

    private RequestQueue requestQueue;
    private static RequestQueue mSingleQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        mSingleQueue = Volley.newRequestQueue(this, new MultiPartStack());
        RequestStringByGet();
        RequestStringByPost();

    }








    private void RequestStringByGet() {
        showProgress();
        String url = "http://nanhai655.cn:80/Uploadfile/stringGetServlet";
        String url2 = url + "?page=%s&count=%s";
        String page = "10";
        String count = "20";
        String strUrl = String.format(url2, page, count);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, strUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Main2Activity.this, "请求成功,返回结果是:" + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "failure" + error.getMessage(), error);
                Toast.makeText(Main2Activity.this, "请求失败", Toast.LENGTH_LONG).show();
            }
        });
        hiddenProgress();
        requestQueue.add(stringRequest);
    }

    private void RequestStringByPost() {
        showProgress();
        String url = "http://nanhai655.cn:80/Uploadfile/stringGetServlet";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Main2Activity.this, "请求成功,返回结果是:" + response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "failure" + error.getMessage(), error);
                Toast.makeText(Main2Activity.this, "请求失败", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("page", "10");
                map.put("count", "20");
                return map;
            }


        };
        hiddenProgress();
        requestQueue.add(stringRequest);

    }

    private void UploadFile() {
        showProgress();
        Map<String, File> files = new HashMap<String, File>();
        files.put("image1", new File(
                "/sdcard/1.png"));
        files.put("image2", new File(
                "/sdcard/2.png"));
//        files.put("image3", new File(
//                "/sdcard/magick.jpg"));
        Map<String, String> params = new HashMap<String, String>();
        params.put("token", "DJrlPbpJQs21rv1lP41yiA==");
        String uri = "http://nanhai655.cn:8080/Uploadfile/uploadServlet";
        addPutUploadFileRequest(
                uri,
                files, params, mResonseListenerString, mErrorListener, null);
    }

    public void openFile() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, 1);

        // BufferedReader reader = new BufferedReader(new InputStreamReader(new FilterInputStream()))
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        Log.d(TAG, "onActivityResult: FFFFFFFFFFFFFFFFFFFFFF:"+data.getData());
                        //4.4及以上用此方法
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }

    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d(TAG, "handleImageOnKitKat: show:"+uri.getPath());
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                Log.d(TAG, "handleImageOnKitKat: inininininininin");
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse(" content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        Log.d(TAG, "handleImageOnKitKat: imagePath" + imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
// 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.
                        Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    Response.Listener<String> mResonseListenerString = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            //返回的数据
            Toast.makeText(Main2Activity.this, "返回的数据:" + response, Toast.LENGTH_SHORT).show();
        }
    };

    Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error != null) {
                if (error.networkResponse != null)
                    Log.i(TAG, " error " + new String(error.networkResponse.data));
                Log.d(TAG, "onResponse: dddddddddddddddddddddddddddddd2");
            }
        }
    };

    public static void addPutUploadFileRequest(final String url,
                                               final Map<String, File> files, final Map<String, String> params,
                                               final Response.Listener<String> responseListener, final Response.ErrorListener errorListener,
                                               final Object tag) {
        if (null == url || null == responseListener) {
            return;
        }

        MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
                Request.Method.POST, url, responseListener, errorListener) {
            @Override
            public Map<String, File> getFileUploads() {
                return files;
            }

            @Override
            public Map<String, String> getStringUploads() {
                return params;
            }

        };
        hiddenProgress();
        mSingleQueue.add(multiPartRequest);
    }

    private void showProgress() {
        mProgress = ProgressDialog
                .show(this, "提示", "请稍等", false);
    }

    private static void hiddenProgress() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.cancel();
        }
    }
}

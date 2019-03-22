package com.luyy.photoview;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class PhotoPickerActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private RecyclerView recycler;
    private static final int PHOTO_REQ=1;
    private PhotoPickerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photopicker);
        findViews();
        initViews();
        GlideUtils.init(getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            getDataFromLocalDb();
        }else{
            EasyPermissions.requestPermissions(this,"我需要你的存储权限",PHOTO_REQ
                    ,Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void findViews() {
        recycler=findViewById(R.id.recycle);
    }

    private void initViews() {
        recycler.setLayoutManager(new GridLayoutManager(this,4));
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState){
                    case RecyclerView.SCROLL_STATE_IDLE:
                        GlideUtils.getManager().resumeRequests();
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        case RecyclerView.SCROLL_STATE_SETTLING:
                        GlideUtils.getManager().pauseAllRequests();
                        break;
                }
            }
        });
    }

    /**
     * 从本地数据库中获取相册、图片数据
     */
    private void getDataFromLocalDb() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver cr= getContentResolver();

                Cursor cursor= cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
                List<String> datas=new ArrayList<>();
                while (cursor.moveToNext()){
                    String data= cursor.getString(cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA));
                    datas.add(data);
                }
                adapter=new PhotoPickerAdapter(PhotoPickerActivity.this,datas);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recycler.setAdapter(adapter);
                    }
                });

            }
        }).start();


    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if(requestCode==PHOTO_REQ)
            getDataFromLocalDb();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }
}

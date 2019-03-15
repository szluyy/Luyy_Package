package com.luyy.photoview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class PhotoViewActivity extends AppCompatActivity {
    private ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview);
        findView();
        initView();
    }

    private void findView() {
        viewPager=findViewById(R.id.viewpager);
    }

    private void initView() {

    }
}

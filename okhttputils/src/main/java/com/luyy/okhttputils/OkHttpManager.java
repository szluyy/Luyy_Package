package com.luyy.okhttputils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpManager {
    //单例
    private static volatile OkHttpClient mInstance;
    private OkHttpManager(){
    }

    public static   OkHttpClient getInstance(){
        if(mInstance==null){
            synchronized (OkHttpManager.class){
                if(mInstance==null){
                    mInstance=new OkHttpClient.Builder()
                                .readTimeout(GlobalParams.READ_TIMEOUT, TimeUnit.MILLISECONDS)
                                .writeTimeout(GlobalParams.WRITE_TIMEOUT,TimeUnit.MILLISECONDS)
                                .connectTimeout(GlobalParams.CONNECT_TIMEOUT,TimeUnit.MILLISECONDS)
                            .build();
                }
            }
        }
        return mInstance;

    }




}

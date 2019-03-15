package com.luyy.okhttputils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {
    /**
     * 同步调用
     * @param url
     */
    public static Response get(String url){
        Request request=new Request.Builder()
                            .url(url).get()
                .build();
        Response response=null;
        try {
            response= OkHttpManager.getInstance().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


}

package com.luyy.photoview;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class GlideUtils {
    private static RequestManager manager;
   public static void init(Context context){
       manager=Glide.with(context);
   }

   public static RequestManager getManager()  {
      return manager;
   }


}

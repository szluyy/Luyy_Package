package com.luyy.photoview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.List;

public class PhotoPickerAdapter extends RecyclerView.Adapter<PhotoPickerAdapter.PhotoPickerViewHolder> {
    private List<String> data;
    private Context context;
    private int imageSize;

    public PhotoPickerAdapter( Context context,List<String> data) {
        this.data = data;
        this.context = context;
        setColumnNumber(context);
    }

    private void setColumnNumber(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        imageSize = widthPixels / 4;
    }

    @NonNull
    @Override
    public PhotoPickerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_photo_layout,null);
        return new PhotoPickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoPickerViewHolder photoPickerViewHolder, int i) {
        try {
            GlideUtils.getManager().asBitmap().load(new File(data.get(i)))
                     .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                     .override(imageSize,imageSize)
                    .into(photoPickerViewHolder.image);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PhotoPickerViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        public PhotoPickerViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);

        }
    }
}

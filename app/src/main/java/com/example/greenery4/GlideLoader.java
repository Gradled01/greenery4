package com.example.greenery4;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;



import java.io.File;


public class GlideLoader {


    /**
     * 默认加载方式
     *
     * @param url
     * @param imageView
     */


    public static void loadImage(Context context, Object url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
              
              ;
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param url
     * @param imageView
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions requestOptions = RequestOptions
                .bitmapTransform(new CircleCrop())

              ;
        Glide.with(context)
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param url
     * @param imageView
     * @param radius    圆角大小
     */
    public static void loadRoundImage(Object url, ImageView imageView, int radius) {
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .dontAnimate()


                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms( new RoundedCorners(radius));

        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }
    /**
     * 加载圆角图片
     *
     * @param url
     * @param imageView
     * @param radius    圆角大小
     */
    public static void loadCenterRoundImage(Object url, ImageView imageView, int radius) {
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .dontAnimate()


                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transforms( new CenterCrop(),new RoundedCorners(radius));

        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载图片指定大小
     *
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void loadSizeImage(String url, ImageView imageView, int width, int height) {
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .override(width, height)


                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(imageView.getContext())
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    /**
     * 加载本地图片文件
     *
     * @param file
     * @param imageView
     */
    public static void loadFileImage(File file, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)


                .centerCrop();

        Glide.with(imageView.getContext())
                .load(file)
                .apply(requestOptions)
                .into(imageView);
    }



    /**
     * 加载gif图
     *
     * @param url
     * @param imageView
     */
    public static void loadGifImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }



}
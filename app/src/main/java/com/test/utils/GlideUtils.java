package com.test.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.test.R;

/**
 * @author 简言
 * @description: 对glide进行封装的工具类
 * @date 2019/2/11
 */

public class GlideUtils {

    /**
     * 直接加载一张图片
     * @param context
     * @param url
     * @param iv
     */
    public static void load(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_default);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    /**
     *   自定义一张占位符图片
     * @param context
     * @param url
     * @param iv
     * @param placeHolderResId
     */
    public static void load(Context context, String url, ImageView iv, int placeHolderResId) {
        if (placeHolderResId == -1) {
            Glide.with(context)
                    .load(url)
                    .into(iv);
            return;
        }
        RequestOptions options = new RequestOptions();
        options.placeholder(placeHolderResId);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }


    /**
     *   圆角图片
     * @param context
     * @param url
     * @param iv
     */
    public static void loadRound(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.ic_circle_default)
                .centerCrop()
                .circleCrop();

        Glide.with(context)//
                .load(url)//
                .apply(options)//
                .into(iv);
    }
}

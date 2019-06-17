package com.test.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.R;
import com.test.model.result.toutiao.News;
import com.test.utils.GlideUtils;
import com.test.utils.tools.VideoPathDecoder;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


/**
 * Created by 简言 on 2019/2/11.
 * 努力吧 ！ 少年 ！
 */

public class VideoListAdapter extends BaseQuickAdapter<News, BaseViewHolder> {


    public VideoListAdapter(@Nullable List<News> news) {
        super(R.layout.item_video_list, news);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {

        JzvdStd jzvdStd = helper.getView(R.id.video_player);

        if (item.video_detail_info.detail_video_large_image.url != null) {
            GlideUtils.load(mContext, item.video_detail_info.detail_video_large_image.url, jzvdStd.thumbImageView, R.color.color_d8d8d8);//设置缩略图
        }

        jzvdStd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VideoPathDecoder decoder = new VideoPathDecoder() {
                    @Override
                    public void onSuccess(String url) {

                        jzvdStd.setUp(url, item.title, Jzvd.SCREEN_WINDOW_LIST);
                    }

                    @Override
                    public void onDecodeError() {

                    }
                };
                Log.i("我得视屏播放地址", ""+item.url);
                decoder.decodePath(item.url);

            }
        });
    }
}

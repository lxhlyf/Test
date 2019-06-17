package com.test.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chaychan.adapter.MultipleItemRvAdapter;
import com.test.model.result.toutiao.News;
import com.test.ui.adapter.provider.CenterPicNewsItemProvider;
import com.test.ui.adapter.provider.RightPicNewsItemProvider;
import com.test.ui.adapter.provider.TextNewsItemProvider;
import com.test.ui.adapter.provider.ThreePicNewsItemProvider;
import com.test.utils.ListUtils;
import java.util.List;

/**
 * Created by 简言 on 2019/2/11.
 * 努力吧 ！ 少年 ！
 */

public class NewsListAdapter extends MultipleItemRvAdapter<News,BaseViewHolder> {

    /**
     * 纯文字布局(文章、广告)
     */
    public static final int TEXT_NEWS = 100;
    /**
     * 居中大图布局(1.单图文章；2.单图广告；3.视频，中间显示播放图标，右侧显示时长)
     */
    public static final int CENTER_SINGLE_PIC_NEWS = 200;
    /**
     * 右侧小图布局(1.小图新闻；2.视频类型，右下角显示视频时长)
     */
    public static final int RIGHT_PIC_VIDEO_NEWS = 300;
    /**
     * 三张图片布局(文章、广告)
     */
    public static final int THREE_PICS_NEWS = 400;


    private String mChannelCode;

    public NewsListAdapter(@Nullable List<News> data, String channelCode) {
        super(data);
        this.mChannelCode = channelCode;

        //在完成初始化调用之前，初始化数值
        finishInitialize();
    }

    @Override
    protected int getViewType(News news) {
        if (news.has_video) {
            //如果有视频
           /* if (news.video_style == 0) {
                //右侧视频
                if (news.middle_image == null || TextUtils.isEmpty(news.middle_image.url)) {
                    return TEXT_NEWS;
                }
                return RIGHT_PIC_VIDEO_NEWS;
            } else if (news.video_style == 2) {
                //居中视频
                return CENTER_SINGLE_PIC_NEWS;
            }*/
        } else {
            //非视频新闻
            if (!news.has_image) {
                //纯文字新闻
                return TEXT_NEWS;
            } else {
                if (ListUtils.isEmpty(news.image_list)) {
                    //图片列表为空，则是右侧图片
                    return RIGHT_PIC_VIDEO_NEWS;
                }

                if (news.gallary_image_count == 3) {
                    //图片数为3，则为三图
                    return THREE_PICS_NEWS;
                }

                //中间大图，右下角显示图数
                return CENTER_SINGLE_PIC_NEWS;
            }
        }
        return TEXT_NEWS;
    }

    @Override
    public void registerItemProvider() {
        //注册itemProvider
        //纯文本
        mProviderDelegate.registerProvider(new TextNewsItemProvider(mChannelCode));
        //居中大图
        mProviderDelegate.registerProvider(new CenterPicNewsItemProvider(mChannelCode));
        //右侧图片类型
        mProviderDelegate.registerProvider(new RightPicNewsItemProvider(mChannelCode));
        //三图类型
        mProviderDelegate.registerProvider(new ThreePicNewsItemProvider(mChannelCode));

    }
}

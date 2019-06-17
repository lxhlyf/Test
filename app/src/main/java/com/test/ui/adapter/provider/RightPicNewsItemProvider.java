package com.test.ui.adapter.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.test.R;
import com.test.model.result.toutiao.News;
import com.test.ui.adapter.NewsListAdapter;
import com.test.utils.GlideUtils;
import com.test.utils.TimeUtils;
import com.test.utils.UIUtils;

/**
 * Created by 简言 on 2019/2/11.
 * 努力吧 ！ 少年 ！
 */

public class RightPicNewsItemProvider extends BaseNewsItemProvider {

    public RightPicNewsItemProvider(String mChannelCode) {
        super(mChannelCode);
    }

    @Override
    public int viewType() {
        return NewsListAdapter.RIGHT_PIC_VIDEO_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_pic_video_news;
    }

    @Override
    protected void setData(BaseViewHolder helper, News news) {

        //右侧小图布局，判断是否有视频
        if (news.has_video) {
//            helper.setVisible(R.id.ll_duration, true);//显示时长
//            helper.setText(R.id.tv_duration, TimeUtils.secToTime(news.video_duration));//设置时长
        } else {
            helper.setVisible(R.id.ll_duration, false);//隐藏时长
        }
        GlideUtils.load(mContext, news.middle_image.url, helper.getView(R.id.iv_img));//右侧图片或视频的图片使用middle_image
    }
}

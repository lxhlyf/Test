package com.test.ui.adapter.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.test.R;
import com.test.model.result.toutiao.News;
import com.test.ui.adapter.NewsListAdapter;
import com.test.utils.GlideUtils;
import com.test.utils.UIUtils;


/**
 * Created by 简言 on 2019/2/11.
 * 努力吧 ！ 少年 ！
 */

public class ThreePicNewsItemProvider extends BaseNewsItemProvider {

    public ThreePicNewsItemProvider(String mChannelCode) {
        super(mChannelCode);
    }

    @Override
    public int viewType() {
        return NewsListAdapter.THREE_PICS_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_three_pics_news;
    }

    @Override
    protected void setData(BaseViewHolder helper, News news) {
        //三张图片的新闻
        GlideUtils.load(mContext, news.image_list.get(0).url, helper.getView(R.id.iv_img1));
        GlideUtils.load(mContext, news.image_list.get(1).url, helper.getView(R.id.iv_img2));
        GlideUtils.load(mContext, news.image_list.get(2).url, helper.getView(R.id.iv_img3));
    }
}

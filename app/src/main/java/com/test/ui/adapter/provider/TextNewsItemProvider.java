package com.test.ui.adapter.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.test.R;
import com.test.model.result.toutiao.News;
import com.test.ui.adapter.NewsListAdapter;

/**
 * Created by 简言 on 2019/2/11.
 * 努力吧 ！ 少年 ！
 */

public class TextNewsItemProvider extends BaseNewsItemProvider {

    public TextNewsItemProvider(String mChannelCode) {
        super(mChannelCode);
    }

    @Override
    public int viewType() {
        return NewsListAdapter.TEXT_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_text_news;
    }

    @Override
    protected void setData(BaseViewHolder helper, News news) {
        //具体实现在BaseNewsItemProvider
    }
}

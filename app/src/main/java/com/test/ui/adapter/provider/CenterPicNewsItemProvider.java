package com.test.ui.adapter.provider;

import android.widget.TextView;

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

public class CenterPicNewsItemProvider extends BaseNewsItemProvider {

    public CenterPicNewsItemProvider(String mChannelCode) {
        super(mChannelCode);
    }

    @Override
    public int viewType() {
        return NewsListAdapter.CENTER_SINGLE_PIC_NEWS;
    }

    @Override
    public int layout() {
        return R.layout.item_center_pic_news;
    }

    @Override
    protected void setData(BaseViewHolder helper, News news) {

        //中间大图布局，判断是否有视频
        TextView tvBottomRight = helper.getView(R.id.tv_bottom_right);
        if (news.has_video) {
//            helper.setVisible(R.id.iv_play, true);//显示播放按钮
//            tvBottomRight.setCompoundDrawables(null, null, null, null);//去除TextView左侧图标
//            helper.setText(R.id.tv_bottom_right, TimeUtils.secToTime(news.video_duration));//设置时长
//            GlideUtils.load(mContext, news.video_detail_info.detail_video_large_image.url, helper.getView(R.id.iv_img));//中间图片使用视频大图
        } else {
            helper.setVisible(R.id.iv_play, false);//隐藏播放按钮
            if (news.gallary_image_count == 1){
                tvBottomRight.setCompoundDrawables(null, null, null, null);//去除TextView左侧图标
            }else{
                tvBottomRight.setCompoundDrawables(mContext.getResources().getDrawable(R.drawable.icon_picture_group), null, null, null);//TextView增加左侧图标
                helper.setText(R.id.tv_bottom_right, news.gallary_image_count + UIUtils.getString(R.string.img_unit));//设置图片数
            }
            GlideUtils.load(mContext, news.image_list.get(0).url.replace("list/300x196", "large"), helper.getView(R.id.iv_img));//中间图片使用image_list第一张
        }
    }
}

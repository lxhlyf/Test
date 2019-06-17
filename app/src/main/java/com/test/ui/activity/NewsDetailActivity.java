package com.test.ui.activity;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.R;
import com.test.constract.NewsDetailConstract;
import com.test.model.response.CommentResponse;
import com.test.model.result.toutiao.NewsDetail;
import com.test.ui.base.NewsDetailBaseActivity;
import com.test.utils.GlideUtils;
import com.test.utils.ListUtils;
import com.test.utils.StatusBarUtil;
import com.test.utils.UIUtils;
import com.test.view.NewsDetailHeaderView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by 简言 on 2019/2/13.
 * 努力吧 ！ 少年 ！
 */
public class NewsDetailActivity extends NewsDetailBaseActivity implements NewsDetailConstract.INewsDetailView {

    //头部左上角的返回按钮
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    //头部中间存放作者的图标和文本的线性布局
    @BindView(R.id.ll_user)
    LinearLayout mLlUser;
    //头部中间存放作者头像的ImageView
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    //头部中间存放作者名字的TextView
    @BindView(R.id.tv_author)
    TextView mTvAuthor;

    @Override
    public int provideViewId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initTitle() {

        StatusBarUtil.statusBarTintColor(this, UIUtils.getColor(R.color.color_BDBDBD));
    }

    @Override
    public void initListener() {
        super.initListener();

        //得到头布局中，用户信息这一栏底部到屏幕顶部的距离
        int llInfoBottom = mHeaderView.mLlInfo.getBottom();
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRvComment.getLayoutManager();
        mRvComment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                //得到存放评论的RecyclerView中的第一条可见Item的位置
                int position = layoutManager.findFirstVisibleItemPosition();
                //得到第一条可见Item的实例
                View firstVisiableChildView = layoutManager.findViewByPosition(position);
                //得到view的高
                int itemHeight = firstVisiableChildView.getHeight();
                //
                int scrollHeight = (position) * itemHeight - firstVisiableChildView.getTop();

                // I/position:: ******************************0
                //I/scrollHeight:: 0
                //I/llInfoBottom:: 0


                //I/position:: ******************************0
                //02-13 20:38:22.943 32023-32023/com.test I/scrollHeight:: 426
                //02-13 20:38:22.943 32023-32023/com.test I/llInfoBottom:: 0


                // I/position:: ******************************0
                //02-13 20:46:56.214 32023-32023/com.test I/scrollHeight:: 6872
                //02-13 20:46:56.214 32023-32023/com.test I/llInfoBottom:: 0

                //******************************0
                //02-13 20:46:59.163 32023-32023/com.test I/scrollHeight:: 10538
                //02-13 20:46:59.163 32023-32023/com.test I/llInfoBottom:: 0

                //I/position:: ******************************1
                //02-13 20:50:54.714 32023-32023/com.test I/scrollHeight:: 792
                //02-13 20:50:54.714 32023-32023/com.test I/llInfoBottom:: 0


//                Log.i("position: ", "******************************" + position);
//                Log.i("scrollHeight: ", "" + scrollHeight);
//                Log.i("llInfoBottom: ", "" + llInfoBottom);

                //动态的判断topbar的用户图标的显示和隐藏
                mLlUser.setVisibility(scrollHeight > llInfoBottom ? View.VISIBLE : View.GONE);//如果滚动超过用户信息一栏，显示标题栏中的用户头像和昵称
            }
        });
    }


    @Override
    public void onGetNewsDetailSuccess(NewsDetail newsDetail) {
        mHeaderView.setDetail(newsDetail, new NewsDetailHeaderView.LoadWebListener() {
            @Override
            public void onLoadFinished() {
                //加载完成后，显示内容布局
                mStateView.showContent();
            }
        });

        //一进入，是隐藏的
        mLlUser.setVisibility(View.GONE);


        if (newsDetail.media_user != null) {
            GlideUtils.loadRound(this, newsDetail.media_user.avatar_url, mIvAvatar);
            mTvAuthor.setText(newsDetail.media_user.screen_name);
        }
    }

    @Override
    public void onBackPressed() {
        postVideoEvent(false);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        postVideoEvent(false);
    }

    @Override
    public void onGetCommentSuccess(CommentResponse response) {

        mCommentResponse = response;

        if (ListUtils.isEmpty(response.data)){
            //没有评论了
            mCommentAdapter.loadMoreEnd();
            return;
        }

        if (response.total_number > 0) {
            //如果评论数大于0，显示红点
            mTvCommentCount.setVisibility(View.VISIBLE);
            mTvCommentCount.setText(String.valueOf(response.total_number));
        }

        mCommentList.addAll(response.data);
        mCommentAdapter.notifyDataSetChanged();

        //如果没有更多的话，就结束加载更多
        if (!response.has_more) {
            mCommentAdapter.loadMoreEnd();
        }else{
            mCommentAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onError() {
        mStateView.showRetry();
    }


}

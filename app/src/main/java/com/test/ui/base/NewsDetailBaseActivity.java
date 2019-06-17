package com.test.ui.base;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.github.nukc.stateview.StateView;
import com.test.R;
import com.test.constract.NewsDetailConstract;
import com.test.data.Constant;
import com.test.model.event.DetailCloseEvent;
import com.test.model.result.toutiao.CommentData;
import com.test.model.response.CommentResponse;
import com.test.model.result.toutiao.NewsDetail;
import com.test.ui.adapter.CommentAdapter;
import com.test.ui.presenter.NewsDetailPresenter;
import com.test.utils.ListUtils;
import com.test.view.NewsDetailHeaderView;
import com.uikit.powerfulrecyclerview.PowerfulRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 简言 on 2019/2/13.
 * 努力吧 ！ 少年 ！
 */
public abstract class NewsDetailBaseActivity extends BaseActivity<NewsDetailPresenter> implements BaseQuickAdapter.RequestLoadMoreListener {

    public static final String CHANNEL_CODE = "channelCode";
    public static final String PROGRESS = "progress";
    public static final String POSITION = "position";
    public static final String DETAIL_URL = "detailUrl";
    public static final String GROUP_ID = "groupId";
    public static final String ITEM_ID = "itemId";

    @BindView(R.id.fl_content)
    public FrameLayout mFlContent;
    @BindView(R.id.rv_comment)
    public PowerfulRecyclerView mRvComment;
    @BindView(R.id.tv_comment_count)
    public TextView mTvCommentCount;

    protected List<CommentData> mCommentList = new ArrayList<>();
    //
    protected StateView mStateView;

    protected CommentAdapter mCommentAdapter;
    protected NewsDetailHeaderView mHeaderView;
    private String mDetalUrl;
    private String mGroupId;
    private String mItemId;
    protected CommentResponse mCommentResponse;

    protected String mChannelCode;
    protected int mPosition;

    @Override
    public NewsDetailPresenter createPresenter() {
        return  new NewsDetailPresenter();
    }

    @Override
    public int provideContentViewId() {
        return provideViewId();
    }


    public abstract int provideViewId();

    @Override
    protected void initView() {
        mStateView = StateView.inject(mFlContent);
        mStateView.setLoadingResource(R.layout.page_loading);
        mStateView.setRetryResource(R.layout.page_net_error);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();

        mChannelCode = intent.getStringExtra(CHANNEL_CODE);
        mPosition = intent.getIntExtra(POSITION, 0);

        mDetalUrl = intent.getStringExtra(DETAIL_URL);
        mGroupId = intent.getStringExtra(GROUP_ID);
        mItemId = intent.getStringExtra(ITEM_ID);
        mItemId = mItemId.replace("i", "");

        mPresenter.getNewsDetail(mDetalUrl);
        loadCommentData();
    }
    private void loadCommentData() {
        mStateView.showLoading();
        mPresenter.getComment(mGroupId, mItemId, 1);
    }

    @Override
    public void initListener() {
        mCommentAdapter = new CommentAdapter(this, R.layout.item_comment, mCommentList);
        mRvComment.setAdapter(mCommentAdapter);

        mHeaderView = new NewsDetailHeaderView(this);
        mCommentAdapter.addHeaderView(mHeaderView);

        mCommentAdapter.setEnableLoadMore(true);
        mCommentAdapter.setOnLoadMoreListener(this, mRvComment);

        mCommentAdapter.setEmptyView(View.inflate(this, R.layout.pager_no_comment, null));
        mCommentAdapter.setHeaderAndEmpty(true);

        mStateView.setOnRetryClickListener(() -> loadCommentData());
    }


    @OnClick({R.id.fl_comment_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_comment_icon:
                //底部评论的图标
                RecyclerView.LayoutManager layoutManager = mRvComment.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    int firstPosition = linearManager.findFirstVisibleItemPosition();
                    int last = linearManager.findLastVisibleItemPosition();
                    if (firstPosition == 0 && last == 0) {
                        //处于头部，滚动到第一个条目
                        mRvComment.scrollToPosition(1);
                    } else {
                        //不是头部，滚动到头部
                        mRvComment.scrollToPosition(0);
                    }
                }
                break;
        }
    }

    @Override
    public void onLoadMoreRequested() {
        //总是去得到下一页的数据
        mPresenter.getComment(mGroupId, mItemId, mCommentList.size() / Constant.COMMENT_PAGE_SIZE + 1);
    }


    /**
     *  发送事件，用于更新上个页面的播放进度以及评论数
     */
    protected void postVideoEvent(boolean isVideoDetail) {

        //定义一个详情页面关闭事件
        DetailCloseEvent event = new DetailCloseEvent();
        event.setChannelCode(mChannelCode);
        event.setPosition(mPosition);

        //如果请求评论数量是成功的话
        if (mCommentResponse != null){
            event.setCommentCount(mCommentResponse.total_number);
        }

//        if (isVideoDetail && JCMediaManager.instance().mediaPlayer != null && JCVideoPlayerManager.getCurrentJcvd() != null){
//            //如果是视频详情
//            int progress = JCMediaManager.instance().mediaPlayer.getCurrentPosition();
//            event.setProgress(progress);
//        }

        EventBus.getDefault().postSticky(event);
        finish();
    }
}

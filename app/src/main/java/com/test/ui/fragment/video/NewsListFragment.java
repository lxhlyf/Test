package com.test.ui.fragment.video;


import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.test.R;
import com.test.constract.NewsListConstract;
import com.test.data.Constant;
import com.test.db.curd.NewsRecordHelper;
import com.test.db.domain.NewsRecord;
import com.test.model.event.DetailCloseEvent;
import com.test.model.event.TabRefreshEvent;
import com.test.model.result.toutiao.News;
import com.test.ui.activity.NewsDetailActivity;
import com.test.ui.activity.VideoDetailActivity;
import com.test.ui.activity.WebViewActivity;
import com.test.ui.adapter.NewsListAdapter;
import com.test.ui.adapter.VideoListAdapter;
import com.test.ui.base.BaseFragment;
import com.test.ui.base.NewsDetailBaseActivity;
import com.test.ui.presenter.NewsListPresenter;
import com.test.utils.ListUtils;
import com.test.utils.NetWorkUtils;
import com.test.utils.UIUtils;
import com.uikit.TipView;
import com.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.uikit.refreshlayout.BGARefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 简言 on 2019/2/8.
 * 努力吧 ！ 少年 ！
 */

public class NewsListFragment extends BaseFragment<NewsListPresenter> implements NewsListConstract.INewsListView, BGARefreshLayout.BGARefreshLayoutDelegate, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.rv_news)
    PowerfulRecyclerView mRvNews;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.refresh_layout)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.tip_view)
    TipView mTipView;

    private BaseQuickAdapter mNewsListAdapter;

    private String mChannelCode;

    private List<News> mNewsList;
    private NewsRecord newsRecord;

    private boolean isRecommendChannel;

    @Override
    public NewsListPresenter createPresenterProxy1() {
        return new NewsListPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.new_list_fragment;
    }

    @Override
    protected View getStateViewRoot() {
        return flContent;
    }

    @Override
    protected void initView(View rootView) {

        //设置上拉刷新和下拉刷新的监听
        mRefreshLayout.setDelegate(this);
        //设置PowerfulRecyclerView 的 布局风格
        mRvNews.setLayoutManager(new GridLayoutManager(context, 1));

        //默认 没有正在进行 上拉加载
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(context, false);

        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.color_F3F5F4);//背景色
        refreshViewHolder.setPullDownRefreshText(getResources().getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(getResources().getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(getResources().getString(R.string.refresh_ing_text));//刷新中的提示文字

        //设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        //如果RecyclerView设置的资源都设置好了， 就判断RecyclerView是否 到底了。
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(mRvNews);
    }

    @Override
    protected void initData() {

        mNewsList = new ArrayList<>();
        mChannelCode = getArguments().getString(Constant.CHANNEL_CODE);

        String[] channelCodes = UIUtils.getStringArr(R.array.channel_code);
        isRecommendChannel = mChannelCode.equals(channelCodes[0]);

        //设置适配器
        mNewsListAdapter = new NewsListAdapter(mNewsList, mChannelCode);

        mRvNews.setAdapter(mNewsListAdapter);
    }

    @Override
    protected void initListener() {

        //1.设置Adapter的上拉加载
        mNewsListAdapter.setEnableLoadMore(true);
        mNewsListAdapter.setOnLoadMoreListener(this, mRvNews);

        //2.adapter 的条目监听器
        mNewsListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                //1.从集合中拿到该位置的新闻条目
                News news = mNewsList.get(position);

                //2.拼接新闻详情的 url。
                String itemId = news.item_id;
                StringBuffer urlSb = new StringBuffer("http://m.toutiao.com/i");
                urlSb.append(itemId).append("/info/");
                String url = urlSb.toString();

                //3.判断新闻的格式
                Intent intent = null;
                if (news.has_video) {

                    //3.1 包含视频 的 新闻
                    UIUtils.showToast("视频新闻");
                    intent = new Intent(mActivity, VideoDetailActivity.class);
                } else {
                    //3.2 不是视屏类新闻
                    if (news.article_type == 1) {
                        //纯文本, Web 类型的文件
                        UIUtils.showToast("Web新闻");
                        intent = new Intent(mActivity, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.URL, news.article_url);
                        startActivity(intent);
                        return;
                    }
                    UIUtils.showToast("其他新闻");
//其他新闻          //其他的不包含视频的 新闻
                    intent = new Intent(mActivity, NewsDetailActivity.class);
                }

                intent.putExtra(NewsDetailBaseActivity.CHANNEL_CODE, mChannelCode);
                intent.putExtra(NewsDetailBaseActivity.POSITION, position);

                intent.putExtra(NewsDetailBaseActivity.DETAIL_URL, url);
                intent.putExtra(NewsDetailBaseActivity.GROUP_ID, news.group_id);
                intent.putExtra(NewsDetailBaseActivity.ITEM_ID, itemId);
                startActivity(intent);
            }
        });

        //3.如果是视频，要监听RecyclerView的滚动
    }

    //当Fragment 第一次显示的时候，加载列表数据。
    @Override
    protected void loadData() {

        //1.显示加载布局
        mStateView.showLoading();

        //2.先从数据库中获取，
        newsRecord = NewsRecordHelper.getLastNewsRecord(mChannelCode);
        if (newsRecord == null) {
            //如果数据库中没有数据
            //1.就新建一张表
            newsRecord = new NewsRecord();
            //2.从网络请求数据
            presenterProxy.getNewsList(mChannelCode);
            return;
        }

        //4.如果数据库中有数据，就从数据库中拉取，并更新UI
        List<News> news = NewsRecordHelper.convertToNewsList(newsRecord.getJson());
        mNewsList.addAll(0, news);
        mNewsListAdapter.notifyDataSetChanged();

        //显示主布局
        mStateView.showContent();

        //5.如果数据库中保存的数据超过了10分钟就自动去刷新
        if (System.currentTimeMillis() - newsRecord.getTime() > 10 * 60 * 1000) {
            mRefreshLayout.beginRefreshing();
        }

    }

    @Override
    public void onError(Throwable e) {
        //显示信息刷新提示信息
        mTipView.show();

        //判断集合中是否有数据，如果没有数据
        if (ListUtils.isEmpty(mNewsList)) {
            mStateView.showRetry();
        }

        //判断下拉刷新是否正在刷新
        if (mRefreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
            mRefreshLayout.endRefreshing();
        }

        throw new RuntimeException(e);
    }

    @Override
    public void onSuccess(List<News> newsList, String tipInfo) {
        mRefreshLayout.endRefreshing();

        //处理可能没有数据的情况
        if (ListUtils.isEmpty(mNewsList)) {
            if (ListUtils.isEmpty(newsList)) {
                mStateView.showEmpty();
                return;
            }
            mStateView.showContent();
        }

        if (ListUtils.isEmpty(newsList)) {
            UIUtils.showToast(UIUtils.getString(R.string.no_news_now));
            return;
        }

        //处理一定有了数据的情况
        //1.
        if (TextUtils.isEmpty(newsList.get(0).title)) {
            //由于汽车、体育等频道第一条属于导航的内容，所以如果第一条没有标题，则移除
            newsList.remove(0);
        }

       //
        if (ListUtils.isEmpty(newsList)){
            int size = newsList.size();
            for (int i=0; i<size; i++){

                if (newsList.get(i).has_video){
                    newsList.remove(i);
                    --size;
                }
            }
        }

        //2.处理置顶新闻和广告重复的问题
        dealRepeat(newsList);

        //3.显示数据
        mNewsList.addAll(0, newsList);
        mNewsListAdapter.notifyDataSetChanged();

        //4.显示更新的消息
        mTipView.show(tipInfo);

        //5.将获取的数据，保存到数据库当中
        NewsRecordHelper.save(mChannelCode, newsList);
    }


    /**
     * 处理置顶新闻和广告重复
     */
    private void dealRepeat(List<News> newList) {
        if (isRecommendChannel && !ListUtils.isEmpty(mNewsList)) {
            //如果是推荐频道并且数据列表已经有数据,处理置顶新闻或广告重复的问题
            mNewsList.remove(0);//由于第一条新闻是重复的，移除原有的第一条
            //新闻列表通常第4个是广告,除了第一次有广告，再次获取的都移除广告
            if (newList.size() >= 4) {
                News fourthNews = newList.get(3);
                //如果列表第4个和原有列表第4个新闻都是广告，并且id一致，移除
                if (fourthNews.tag.equals(Constant.ARTICLE_GENRE_AD)) {
                    newList.remove(fourthNews);
                }
            }
        }
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (!NetWorkUtils.isNetworkAvailable(context)) {
            mTipView.show();
            if (refreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING) {
                mRefreshLayout.endRefreshing();
            }
            return;
        }
        presenterProxy.getNewsList(mChannelCode);
    }

    /**
     * 该事件由 MainActivity 发送过来，请求进行刷新页面。
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(TabRefreshEvent event) {


    }


    /**
     * 适配器的上拉加载
     */
    @Override
    public void onLoadMoreRequested() {

        //1.数据库中存有一页以上才能触发上拉加载的监听
        if (newsRecord.getPage() == 0 || newsRecord.getPage() == 1) {

            mNewsListAdapter.loadMoreEnd();
            return;
        }

        //2.如果数据库中有超过俩页的数据，就从数据库中读取
        NewsRecord lastNewsRecord = NewsRecordHelper.getLastNewsRecord(mChannelCode);
        if (lastNewsRecord == null) {

            mNewsListAdapter.loadMoreEnd();
        }

        //如果不为空
        newsRecord = lastNewsRecord;

        long startTime = System.currentTimeMillis();

        List<News> news = NewsRecordHelper.convertToNewsList(newsRecord.getJson());

        if (isRecommendChannel) {
            news.remove(0);  //推荐第一条广告重复
        }

        long endTime = System.currentTimeMillis();

        if (endTime - startTime < 1000) {

            UIUtils.postTaskDelay(new Runnable() {
                @Override
                public void run() {
                    Log.i("TAG", "延迟了吗？---:" + (endTime - startTime));
                    mNewsListAdapter.loadMoreComplete();
                    mNewsList.addAll(mNewsList.size() - 1, news);
                    mNewsListAdapter.notifyDataSetChanged();
                }
            }, (int) (1000 - (endTime - startTime)));
        }

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    /**
     * 详情页关闭后传递过来的事件,更新评论数播放进度等
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDetailCloseEvent(DetailCloseEvent event) {
        if (!event.getChannelCode().equals(mChannelCode)) {
            //如果频道不一致，不用处理
            return;
        }

        int position = event.getPosition();
        int commentCount = event.getCommentCount();

        News news = mNewsList.get(position);
        news.comment_count = commentCount;

        if (news.video_detail_info != null) {
            //如果有视频
            int progress = event.getProgress();
            news.video_detail_info.progress = progress;
        }

        //刷新adapter
        mNewsList.set(position, news);
        mNewsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterEventBus(this);
    }
}

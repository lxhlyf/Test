package com.test.ui.fragment.daily;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.test.R;
import com.test.constract.YouDiaryConstract;
import com.test.data.Constant;
import com.test.db.curd.DanDuRecordHelper;
import com.test.db.curd.NewsRecordHelper;
import com.test.db.domain.DanDuRecord;
import com.test.db.domain.NewsRecord;
import com.test.model.result.dandu.Item;
import com.test.model.result.toutiao.News;
import com.test.ui.activity.VideoDetailActivity;
import com.test.ui.adapter.provider.YouDanDuAdapter;
import com.test.ui.base.BaseFragment;
import com.test.ui.presenter.YouDiaryPresenter;
import com.test.utils.ListUtils;
import com.test.utils.NetWorkUtils;
import com.test.utils.UIUtils;
import com.uikit.TipView;
import com.uikit.powerfulrecyclerview.PowerfulRecyclerView;
import com.uikit.refreshlayout.BGANormalRefreshViewHolder;
import com.uikit.refreshlayout.BGARefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 简言 on 2019/2/5.
 * 努力吧 ！ 少年 ！
 */

public class YouDiaryPage extends BaseFragment<YouDiaryPresenter> implements YouDiaryConstract.IYouDiaryView, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.you_diary_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.you_diary_prv)
    PowerfulRecyclerView yRecyclerView;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.tip_view)
    TipView mTipView;

    private int page = 0;
    private int mode = 2;

    private  DanDuRecord danDuRecord;

    private List<Item> items;

    private YouDanDuAdapter youDanDuAdapter;

    @Override
    public YouDiaryPresenter createPresenterProxy1() {
        return new YouDiaryPresenter();
    }

    @Override
    protected View getStateViewRoot() {
        return flContent;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.you_diary_fragment;
    }

    @Override
    protected void initView(View rootView) {

        //设置上拉刷新和下拉刷新的监听
        mRefreshLayout.setDelegate(this);
        //设置PowerfulRecyclerView 的 布局风格
        yRecyclerView.setLayoutManager(new GridLayoutManager(context, 1));

        //默认 没有正在进行 上拉加载
        BGANormalRefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(context, true);

        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.color_F3F5F4);//背景色
        refreshViewHolder.setPullDownRefreshText(getResources().getString(R.string.refresh_pull_down_text));//下拉的提示文字
        refreshViewHolder.setReleaseRefreshText(getResources().getString(R.string.refresh_release_text));//松开的提示文字
        refreshViewHolder.setRefreshingText(getResources().getString(R.string.refresh_ing_text));//刷新中的提示文字

        //设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        //如果RecyclerView设置的资源都设置好了， 就判断RecyclerView是否 到底了。
        mRefreshLayout.shouldHandleRecyclerViewLoadingMore(yRecyclerView);
    }

    @Override
    protected void initData() {

        items = new ArrayList<>();

        youDanDuAdapter = new YouDanDuAdapter(context, items);

        yRecyclerView.setAdapter(youDanDuAdapter);
    }

    @Override
    protected void initListener() {

        youDanDuAdapter.setOnItemClickListener(new YouDanDuAdapter.OnItemClickListener() {
            @Override
            public void onClick(YouDanDuAdapter.MyViewHolder holder, int position) {

                Intent intent = new Intent(context, VideoDetailActivity.class);
                intent.putExtra("item", items.get(position));
                startActivity(intent);
                //Toast.makeText(context, "点击了item :"+position , Toast.LENGTH_LONG).show();
            }
        });

        youDanDuAdapter.setOnItemLongClickListener(new YouDanDuAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(YouDanDuAdapter.MyViewHolder holder, int position) {

                Toast.makeText(context, "爱，或不自知" , Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void loadData() {
        mStateView.showLoading();

        danDuRecord = DanDuRecordHelper.getLastNewsRecord(Constant.DanDuVideo);
        if (danDuRecord == null){
            danDuRecord = new DanDuRecord();
            presenterProxy.getYouDiaryResult(page,2, "0", UIUtils.getDeviceId(context), "0");
            return;
        }

        List<Item> dBList = DanDuRecordHelper.convertToNewsList(danDuRecord.getJson());
        items.addAll(0, dBList);
        youDanDuAdapter.notifyDataSetChanged();

        mStateView.showContent();

        if (System.currentTimeMillis() - danDuRecord.getTime() > 10*60*1000){
            mRefreshLayout.beginRefreshing();
        }
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError(Throwable e) {

        Toast.makeText(context, ""+e, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSucceed(List<Item> datas) {
        mRefreshLayout.endRefreshing();

        if (ListUtils.isEmpty(items)){
            if (ListUtils.isEmpty(datas)){

                mStateView.showEmpty();
                return;
            }
            mStateView.showContent();
        }

        if (ListUtils.isEmpty(datas)) {
            UIUtils.showToast(UIUtils.getString(R.string.no_news_now));
            return;
        }

        UIUtils.showToast("原始数据有："+items.size());

        items.clear();
        items.addAll(0,datas);
        youDanDuAdapter.notifyDataSetChanged();

        //4.显示更新的消息
        mTipView.show("从网络请求了"+datas.size());

        //保存到数据库
        DanDuRecordHelper.save(Constant.DanDuVideo, datas);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

        if (!NetWorkUtils.isNetworkAvailable(context)){
            mTipView.show();
            if (refreshLayout.getCurrentRefreshStatus() == BGARefreshLayout.RefreshStatus.REFRESHING){
                refreshLayout.endRefreshing();
            }
            return;
        }
        presenterProxy.getYouDiaryResult(++page, mode, youDanDuAdapter.getLastItemId(),UIUtils.getDeviceId(context), youDanDuAdapter.getLastItemCreateTime());
    }



    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        //1.数据库中存有一页以上才能触发上拉加载的监听
        if (danDuRecord.getPage() == 0 || danDuRecord.getPage() == 1) {

            refreshLayout.endLoadingMore();
            return false;
        }

        //2.如果数据库中有超过俩页的数据，就从数据库中读取
        DanDuRecord lastDanRecord = DanDuRecordHelper.getPreNewsRecord(Constant.DanDuVideo, danDuRecord.getPage());
        if (lastDanRecord == null) {

            refreshLayout.endLoadingMore();
        }

        //如果不为空
        danDuRecord = lastDanRecord;

        long startTime = System.currentTimeMillis();

        List<Item> dBItems = DanDuRecordHelper.convertToNewsList(danDuRecord.getJson());

        long endTime = System.currentTimeMillis();

        if (endTime - startTime < 1000) {

            UIUtils.postTaskDelay(new Runnable() {
                @Override
                public void run() {
                    Log.i("TAG", "延迟了吗？---:" + (endTime - startTime));
                    mRefreshLayout.endLoadingMore();
                    items.addAll(items.size() - 1, dBItems);
                    youDanDuAdapter.notifyDataSetChanged();
                }
            }, (int) (1000 - (endTime - startTime)));
        }

        return true;
    }
}

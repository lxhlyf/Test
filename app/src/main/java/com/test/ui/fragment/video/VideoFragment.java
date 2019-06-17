package com.test.ui.fragment.video;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.R;
import com.test.data.Constant;
import com.test.listener.OnChannelListener;
import com.test.model.entity.Channel;
import com.test.ui.adapter.ViewPageTabLayoutAdapter;
import com.test.ui.base.BaseFragment;
import com.test.ui.base.BasePresenter;
import com.test.utils.NavigationBarImpl;
import com.test.utils.SharePreUtils;
import com.uikit.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

/**
 * Created by 简言 on 2019/2/4.
 * 努力吧 ！ 少年 ！
 */

public class VideoFragment extends BaseFragment implements OnChannelListener {

    private NavigationBarImpl navigationBarImpl;

    @BindView(R.id.tab_channel)
    ColorTrackTabLayout tabChannel;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.view_group_video)
    LinearLayout viewGroupVideo;
    @BindView(R.id.iv_operation)
    ImageView ivAddChannel;

    private Gson mGson = new Gson();

    //用户选择了的通道的List集合
    private List<Channel> selectChannelList;

    //用户未选择的通道的List集合
    private List<Channel> unSelectChannelList;
    private String[] mChannelCodes;
    private List<BaseFragment> newsListFragments;
    private ViewPageTabLayoutAdapter channelViewPageAdapter;


    @Override
    protected int provideContentViewId() {
        return R.layout.video_fragment;
    }


    @Override
    public BasePresenter createPresenterProxy1() {
        return null;
    }

    @Override
    public void initTitle(View rootView) {
        navigationBarImpl = new NavigationBarImpl();
        navigationBarImpl.initTextTitle(context, R.id.view_group_video, rootView, "单独头条", R.id.ib_right_icon);
    }

    @Override
    protected void initView(View rootView) {


    }

    @Override
    protected void initData() {

        selectChannelList = new ArrayList<>();
        unSelectChannelList = new ArrayList<>();
        newsListFragments = new ArrayList<>();

        initChannelData();

        initChannelFragments();

        channelViewPageAdapter = new ViewPageTabLayoutAdapter<Channel>(getChildFragmentManager(), newsListFragments, selectChannelList);
        vpContent.setAdapter(channelViewPageAdapter);
        vpContent.setOffscreenPageLimit(selectChannelList.size());
        //设置Tab内部左边和右边的内边距
        tabChannel.setTabPaddingLeftAndRight(UIUtils.dip2Px(context, 10), UIUtils.dip2Px(context,10));
        tabChannel.setupWithViewPager(vpContent);

        tabChannel.post(new Runnable() {
            @Override
            public void run() {
                //设置最小宽度，使其可以在滑动一部分距离
                ViewGroup slidingTabStrip = (ViewGroup) tabChannel.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + ivAddChannel.getMeasuredWidth());
            }
        });

        //隐藏指示器
        tabChannel.setSelectedTabIndicatorHeight(0);

    }

    /**
     * 初始化已选频道和未选频道的数据
     */
    private void initChannelData() {
        //已选频道的Json数据，
        String selectedChannelJson = SharePreUtils.getString(Constant.SELECTED_CHANNEL_JSON, "");
        //未选频道的Json数据
        String unSelectChannelJson = SharePreUtils.getString(Constant.UNSELECTED_CHANNEL_JSON, "");

        //如果SP中没有存储相应的数据，就从res文件中获取Channel,默认选取所有的通道。
        if (TextUtils.isEmpty(selectedChannelJson) || TextUtils.isEmpty(unSelectChannelJson)) {

            String[] tabTitles = getResources().getStringArray(R.array.channel);
            String[] channelCodes = getResources().getStringArray(R.array.channel_code);

            for (int i = 0; i < channelCodes.length; i++) {

                String tabTitle = tabTitles[i];
                String channelCode = channelCodes[i];
                selectChannelList.add(new Channel(tabTitle, channelCode));
            }

            //转成Json字符串，
            selectedChannelJson = mGson.toJson(selectChannelList);

            Log.i("从res中获取", ""+selectedChannelJson);
            //将Json字符串 存放到 SP 数据库当中
            SharePreUtils.putString(Constant.SELECTED_CHANNEL_JSON, selectedChannelJson);

        } else {
            //数据库中曾经存储过通道的数据
            Log.i("从Sp数据库中获取", ""+selectedChannelJson);

            //将Json字符串转换成List集合
            //集合不饿能清空
            selectChannelList.clear();
            unSelectChannelList.clear();
            selectChannelList = mGson.fromJson(selectedChannelJson, new TypeToken<List<Channel>>(){}.getType());
            unSelectChannelList = mGson.fromJson(unSelectChannelJson, new TypeToken<List<Channel>>(){}.getType());
        }


    }

    /**
     * 实例化 各 通道的 Fragment
     */
    private void initChannelFragments() {

        mChannelCodes = getResources().getStringArray(R.array.channel_code);
        for (Channel channel : selectChannelList) {

            NewsListFragment newsListFragment = new NewsListFragment();

            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_CODE, channel.channelCode);
            newsListFragment.setArguments(bundle);
            newsListFragments.add(newsListFragment);
        }
    }


    @Override
    protected void initListener() {

    }

    @OnClick(R.id.iv_operation)
    public void channelSelect(View view){

        //选择频道按钮的监听器
        ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(selectChannelList, unSelectChannelList);
        dialogFragment.setOnChannelListener(this);
        dialogFragment.show(getChildFragmentManager(), "CHANNEL");
        //频道取消选择的监听器
        dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                channelViewPageAdapter.notifyDataSetChanged();
                vpContent.setOffscreenPageLimit(selectChannelList.size());
                tabChannel.setCurrentItem(tabChannel.getSelectedTabPosition());
                ViewGroup slidingTabStrip = (ViewGroup) tabChannel.getChildAt(0);
                //注意：因为最开始设置了最小宽度，所以重新测量宽度的时候一定要先将最小宽度设置为0
                slidingTabStrip.setMinimumWidth(0);
                slidingTabStrip.measure(0, 0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + ivAddChannel.getMeasuredWidth());

                //保存选中和未选中的channel
                SharePreUtils.putString(Constant.SELECTED_CHANNEL_JSON, mGson.toJson(selectChannelList));
                SharePreUtils.putString(Constant.UNSELECTED_CHANNEL_JSON, mGson.toJson(unSelectChannelList));
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        listMove(selectChannelList, starPos, endPos);
        listMove(newsListFragments, starPos, endPos);
    }


    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {
        //移动到我的频道
        Channel channel = unSelectChannelList.remove(starPos);
        selectChannelList.add(starPos, channel);

        NewsListFragment newsFragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CHANNEL_CODE, channel.channelCode);
        bundle.putBoolean(Constant.IS_VIDEO_LIST, channel.channelCode.equals(mChannelCodes[1]));
        newsFragment.setArguments(bundle);
        newsListFragments.add(newsFragment);
    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {
        //移动到推荐频道
        unSelectChannelList.add(endPos, selectChannelList.remove(starPos));
        newsListFragments.remove(starPos);
    }

    private void listMove(List datas, int starPos, int endPos) {
        Object o = datas.get(starPos);
        //先删除之前的位置
        datas.remove(starPos);
        //添加到现在的位置
        datas.add(endPos, o);
    }
}

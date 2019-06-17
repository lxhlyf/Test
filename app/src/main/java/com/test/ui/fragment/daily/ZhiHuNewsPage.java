package com.test.ui.fragment.daily;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.R;
import com.test.constract.ZhiHuNewsConstract;
import com.test.model.result.dandu.Item;
import com.test.model.result.dandu.YouDiaryResult;
import com.test.ui.activity.MainActivity;
import com.test.ui.base.BaseFragment;
import com.test.ui.presenter.ZhiHuNewsPresenter;
import com.test.utils.SharePreUtils;
import com.test.utils.TimeUtils;
import com.test.utils.UIUtils;
import com.test.view.LunarDialog;
import com.test.view.VerticalViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 简言 on 2019/2/5.
 * 努力吧 ！ 少年 ！
 */

public class ZhiHuNewsPage extends BaseFragment<ZhiHuNewsPresenter> implements ZhiHuNewsConstract.IZhiHuNewsView {

    @BindView(R.id.fl_content_zhihu)
    public FrameLayout flContent;
    @BindView(R.id.view_pager)
    public VerticalViewPager verticalViewPager;

    private ZhiHuNewsListAdapter viewPageAdapter;

    private List<Item> datas = new ArrayList<>();

    private int page = 0;

    private boolean isLoading;


    @Override
    public ZhiHuNewsPresenter createPresenterProxy1() {
        return new ZhiHuNewsPresenter();
    }

    @Override
    protected View getStateViewRoot() {
        return flContent;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.zhihu_fragment;
    }

    @Override
    protected void initView(View rootView) {


    }

    @Override
    protected void initData() {

        datas = new ArrayList<>();

        viewPageAdapter = new ZhiHuNewsListAdapter(getChildFragmentManager());
        verticalViewPager.setAdapter(viewPageAdapter);

    }

    @Override
    protected void initListener() {

        verticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (viewPageAdapter.getCount() <= position + 2 && !isLoading) {
                    if (isLoading){
                        Toast.makeText(mActivity,"正在努力加载...",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Log.i("ZhiHuNewsPager","page=" + page + ",getLastItemId=" + viewPageAdapter.getLastItemId());
                    loadingData(page, 0, viewPageAdapter.getLastItemId(), viewPageAdapter.getLastItemCreateTime());
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void loadData() {

        String getLunar= SharePreUtils.getString("getLuar",null);
        if (!TimeUtils.getDate("yyyyMMdd").equals(getLunar)){
            loadRecommend();
        }

        loadingData(SharePreUtils.getInt("danDuPage", 0), 0, viewPageAdapter.getLastItemId(),""+viewPageAdapter.getLastItemCreateTime());
    }

    private void loadingData(int page, int mode, String pageId, String createTime) {
        isLoading = true;
        presenterProxy.getZhiHuNewsResult(page, mode, pageId, UIUtils.getDeviceId(context),createTime);
    }

    private void loadRecommend(){
        presenterProxy.getRecommend(UIUtils.getDeviceId(context));
    }


    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onSuccess(YouDiaryResult.Data<List<Item>> listData) {

            isLoading = false;
            viewPageAdapter.setDataList(listData.getDatas());
            page++;
            SharePreUtils.putInt("danDuPage", page);
    }

    // 推荐的回调
    @Override
    public void showNoMore() {
        page = 0;
        Toast.makeText(mActivity, "没有更多数据了", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showOnFailure() {

    }

    @Override
    public void showLunar(String content) {
        Toast.makeText(mActivity, "弹框", Toast.LENGTH_SHORT).show();
        SharePreUtils.putString("getLunar",TimeUtils.getDate("yyyyMMdd"));
        LunarDialog lunarDialog = new LunarDialog(mActivity);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_lunar,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.image_iv);
        Glide.with(this).load(mActivity).into(imageView);
        lunarDialog.setContentView(view);
        lunarDialog.show();
    }

}

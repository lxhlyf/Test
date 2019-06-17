package com.test.ui.fragment.daily;

import android.view.View;

import com.test.R;
import com.test.ui.adapter.ViewPageTabLayoutAdapter;
import com.test.ui.base.BaseFragment;
import com.test.ui.base.BasePresenter;
import com.test.utils.NavigationBarImpl;
import com.test.view.DailyViewPagerTabLayout;
import com.test.view.HorizontalScrollViewPager;
import com.test.view.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by 简言 on 2019/2/4.
 * 努力吧 ！ 少年 ！
 */

public class DailyFragment extends BaseFragment {

    @BindView(R.id.daily_tly)
    public DailyViewPagerTabLayout dTabLayout;
    @BindView(R.id.daily_vp)
    public HorizontalScrollViewPager dViewPager;

    private NavigationBarImpl navigationBarImpl;
    private DefaultNavigationBar navigationBar;

    private ViewPageTabLayoutAdapter viewPageAdapter;

    private List<BaseFragment> dFragments;
    private List<String> dTitles;

    @Override
    protected int provideContentViewId() {
        return R.layout.daily_fragment;
    }

    @Override
    public BasePresenter createPresenterProxy1() {
        return null;
    }

    @Override
    public void initTitle(View rootView) {

        navigationBarImpl = new NavigationBarImpl();
        this.navigationBarImpl.initTextTitle(context, R.id.view_group_daily, rootView,"所谓伊人", R.id.ib_right_icon);
    }

    @Override
    protected void initView(View rootView) {

        //TabLayout + ViewPager + Fragment
        dFragments = new ArrayList<>();
        dTitles = new ArrayList<>();

        dFragments.add(new ZhiHuNewsPage());
        dFragments.add(new YouDiaryPage());
        dTitles.add("知乎日报");
        dTitles.add("寤寐思服");

        viewPageAdapter = new ViewPageTabLayoutAdapter<String>(getChildFragmentManager(), dFragments, dTitles);
        dViewPager.setAdapter(viewPageAdapter);
        dTabLayout.setActivity(mActivity);
        dTabLayout.setupWithViewPager(dViewPager);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {


//        navigationBar.setSearchClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //发起搜索
//                navigationBar.getSearchText();
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dTitles != null || dFragments != null) {
            dTitles = null;
            dFragments = null;
        }
    }
}

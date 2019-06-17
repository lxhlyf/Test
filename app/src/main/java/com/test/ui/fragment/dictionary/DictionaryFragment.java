package com.test.ui.fragment.dictionary;

import android.util.Log;
import android.view.View;


import com.test.R;
import com.test.constract.DictionaryConstract;
import com.test.model.result.DictionaryAssocitiveResult;
import com.test.ui.adapter.ViewPageTabLayoutAdapter;
import com.test.ui.base.BaseFragment;
import com.test.ui.presenter.DictionaryPresenter;
import com.test.utils.NavigationBarImpl;
import com.test.view.DailyViewPagerTabLayout;
import com.test.view.HorizontalScrollViewPager;
import com.test.view.navigationbar.DefaultNavigationBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 简言 on 2019/2/4.
 * 努力吧 ！ 少年 ！
 */

public class DictionaryFragment extends BaseFragment<DictionaryPresenter> implements DictionaryConstract.DictionaryView{

    @BindView(R.id.dictionary_tly)
    DailyViewPagerTabLayout tabLayout;
    @BindView(R.id.dictionary_vp)
    HorizontalScrollViewPager viewPager;

    private NavigationBarImpl navigationBarImpl;
    private DefaultNavigationBar navigationBar;

    private ViewPageTabLayoutAdapter viewPageTabLayoutAdapter;

    private List<BaseFragment> fragments;

    private List<String> titles;

    private AssociativeWFragment associativeWFragment;
    private SinterpretationFragment sinterpretationFragment;
    private TranslateFragment translateFragment;


    @Override
    protected int provideContentViewId() {

        return R.layout.dictionary_fragment;
    }


    @Override
    public DictionaryPresenter createPresenterProxy1() {
        return new DictionaryPresenter();
    }

    @Override
    public void initTitle(View rootView) {
        navigationBarImpl = new NavigationBarImpl();
        navigationBar = navigationBarImpl.initSearchTitle(context, R.id.view_group_dictionary, rootView, R.id.ib_right_icon);
    }

    @Override
    protected void initView(View rootView) {

        fragments = new ArrayList<>();
        fragments.add(associativeWFragment = new AssociativeWFragment());
        fragments.add(sinterpretationFragment = new SinterpretationFragment());
        fragments.add(translateFragment = new TranslateFragment());

        titles = new ArrayList<>();
        titles.add("联想单词");
        titles.add("源头释义");
        titles.add("翻译在线");

        viewPageTabLayoutAdapter = new ViewPageTabLayoutAdapter<String>(getChildFragmentManager(),fragments,titles);
        viewPager.setAdapter(viewPageTabLayoutAdapter);
        tabLayout.setActivity(mActivity);
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    protected void initData() {

    }



    String text ;

    @Override
    protected void initListener() {


        navigationBar.setSearchClick(v -> {


            text = navigationBar.getSearchText();
            presenterProxy.getAssocitiveResult(text);
        });



    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onSucceed(DictionaryAssocitiveResult result) {

        Log.i("DictionaryFragment:", "" + result.getData().getEntries().get(0).getExplain());
        //Toast.makeText(context, result.getData().getEntries().get(0).getExplain() , Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(result);
    }

}

package com.test.ui.fragment.dictionary;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.test.R;
import com.test.model.result.DictionaryAssocitiveResult;
import com.test.ui.adapter.AccositiveRecyclerAdapter;
import com.test.ui.base.BaseFragment;
import com.test.ui.base.BasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * Created by 简言 on 2019/2/9.
 * 努力吧 ！ 少年 ！
 */

public class AssociativeWFragment extends BaseFragment {

    @BindView(R.id.associtive_word_ry)
    public RecyclerView aRecyclerView;

    private AccositiveRecyclerAdapter accositiveRecyclerAdapter;

    @Override
    public BasePresenter createPresenterProxy1() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.associtive_fragment;
    }

    @Override
    protected void initView(View rootView) {


    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100, sticky = true)
    public void getData(DictionaryAssocitiveResult result) {

        //只能在这里绑定数据。
        aRecyclerView.setItemAnimator(new DefaultItemAnimator());
        aRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        accositiveRecyclerAdapter = new AccositiveRecyclerAdapter(context, result);
        aRecyclerView.setAdapter(accositiveRecyclerAdapter);
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

package com.test.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.test.model.entity.Channel;
import com.test.ui.base.BaseFragment;

import java.util.List;

/**
 * Created by 简言 on 2019/2/5.
 * 努力吧 ！ 少年 ！
 */

public class ViewPageTabLayoutAdapter<V> extends FragmentStatePagerAdapter{

    private List<BaseFragment> dFragments;
    private List<V> dTitles;

    public ViewPageTabLayoutAdapter(FragmentManager fm, List<BaseFragment> fragments, List<V> titles) {
        super(fm);
        this.dFragments = fragments;
        this.dTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return dFragments.get(position);
    }

    @Override
    public int getCount() {
        return dFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (dTitles.get(position) instanceof Channel){
            return ((Channel) dTitles.get(position)).getTitle();
        }
        return (String) dTitles.get(position);
    }
}

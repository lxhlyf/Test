package com.test.ui.fragment.daily;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.test.model.result.dandu.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
class ZhiHuNewsListAdapter extends FragmentPagerAdapter {

    private List<Item> dataList = new ArrayList<>();

    public ZhiHuNewsListAdapter(FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return ZhiHuDetailFragment.instance(dataList.get(position));
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    public void setDataList(List<Item> data) {


        dataList.addAll(data);
        notifyDataSetChanged();
    }

    public String getLastItemId() {
        if (dataList.size() == 0) {
            return "0";
        }
        Item item = dataList.get(dataList.size() - 1);
        return item.getId();
    }

    public String getLastItemCreateTime() {
        if (dataList.size() == 0) {
            return "0";
        }
        Item item = dataList.get(dataList.size() - 1);
        return item.getCreate_time();
    }
}

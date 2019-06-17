package com.test.model.event;

import android.widget.RadioButton;

/**
 * Created by 简言 on 2019/2/12.
 * 努力吧 ！ 少年 ！
 */
public class TabRefreshEvent {
    /**
     * 频道
     */
    private String channelCode;
    private RadioButton bottomBarItem;
    private boolean isHomeTab;  //是不是首页的Tab

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public RadioButton getBottomBarItem() {
        return bottomBarItem;
    }

    public void setBottomBarItem(RadioButton bottomBarItem) {
        this.bottomBarItem = bottomBarItem;
    }

    public boolean isHomeTab() {
        return isHomeTab;
    }

    public void setHomeTab(boolean homeTab) {
        isHomeTab = homeTab;
    }
}

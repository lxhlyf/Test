package com.test.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.R;
import com.test.ui.base.BaseActivity;
import com.test.view.navigationbar.DefaultNavigationBar;


/**
 * Created by 简言 on 2019/2/4.
 * 努力吧 ！ 少年 ！
 */

public class NavigationBarImpl {

    /**
     *   设置文字标题的title
     * @param context
     * @param rootView
     */
    public void initTextTitle(final Context context,int viewGroupId, View rootView, String title, int rightIconId){

        DefaultNavigationBar navigationBar = (DefaultNavigationBar) new DefaultNavigationBar
                .Builder(context, (ViewGroup) rootView.findViewById(viewGroupId))
                .bindViewId(R.layout.title_bar2)
                .setTittle(R.id.tv_title, title)
                .setRightIcon(rightIconId, R.drawable.ic_camera_enhance_black_24dp)
                .setLeftIcon(R.id.ib_left_icon, R.drawable.img_menu)
                .setLeftClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseActivity baseActivity = (BaseActivity) context;
                        baseActivity.slidingMenu.toggle();
                    }
                })
                .setRightClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "我是右边", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }

    public DefaultNavigationBar initSearchTitle(final Context context,int viewGroupId, View rootView, int rightIconId){

        return  (DefaultNavigationBar) new DefaultNavigationBar
                .Builder(context, (ViewGroup) rootView.findViewById(viewGroupId))
                .bindViewId(R.layout.title_bar)
                .setRightIcon(rightIconId, R.drawable.ic_tab_netvideo)
                .setLeftIcon(R.id.ib_left_icon, R.drawable.img_menu)
                .setLeftClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseActivity baseActivity = (BaseActivity) context;
                        baseActivity.slidingMenu.toggle();
                    }
                })
                .setRightClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "我是右边", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
    }
}

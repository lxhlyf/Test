package com.test.helper;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import com.test.ui.fragment.leftMenu.LeftMenuFragment;

import java.util.List;

/**
 * Created by 简言 on 2019/2/3.
 * 努力吧 ！ 少年 ！
 * <p>
 * Description : Fragment 管理类
 */
public class FragmentManagerHelper {
    // 管理类FragmentManager
    private FragmentManager mFragmentManager;
    // 容器布局id containerViewId
    private int mContainerViewId;
    private List<Fragment> childFragments;

    private Context context;

    /**
     * 构造函数
     *
     * @param fragmentManager 管理类FragmentManager
     * @param containerViewId 容器布局id containerViewId
     */
    public FragmentManagerHelper(Context context, FragmentManager fragmentManager, @IdRes int containerViewId) {
        this.mFragmentManager = fragmentManager;
        this.mContainerViewId = containerViewId;
        this.context = context;
        //childFragments = new ArrayList<>();
    }

    /**
     * 添加Fragment
     */
    public void add(Fragment fragment) {
        //childFragments.add(fragment);
        // 开启事物
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        // 第一个参数是Fragment的容器id，需要添加的Fragment
        fragmentTransaction.add(mContainerViewId, fragment);
        // 一定要commit
        fragmentTransaction.commit();
    }

    /**
     * 切换显示Fragment
     */
    public void switchFragment(Fragment fragment) {
        // 开启事物
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // 1.先隐藏当前所有的Fragment
        childFragments = mFragmentManager.getFragments();
        for (Fragment childFragment : childFragments) {
            if (!(childFragment instanceof LeftMenuFragment)){
                fragmentTransaction.hide(childFragment);
            }
        }

        // 2.如果容器里面没有我们就添加，否则显示
        if (!childFragments.contains(fragment)) {
            fragmentTransaction.add(mContainerViewId, fragment);
            //android.app.fragment 用下面的语句
            //childFragments.add(fragment);
        } else {
            fragmentTransaction.show(fragment);
        }

        // 替换Fragment
        // fragmentTransaction.replace(R.id.main_tab_fl,mHomeFragment);
        // 一定要commit
        fragmentTransaction.commit();
    }

    public void clearFragment(){
        if (childFragments != null && childFragments.size() > 0){
            childFragments.clear();
            childFragments = null;
        }
    }
}

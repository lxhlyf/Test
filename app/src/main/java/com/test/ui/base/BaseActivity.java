package com.test.ui.base;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.test.R;
import com.test.listener.PermissionListener;
import com.test.proxy.BaseActivityProxyImpl;
import com.test.ui.activity.MainActivity;
import com.test.ui.fragment.leftMenu.LeftMenuFragment;
import com.test.ui.presenter.NewsDetailPresenter;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 简言 on 2019/2/3.
 * 努力吧 ！ 少年 ！
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {

    public T mPresenter;

    private static long mPreTime = 0;

    private BaseActivity mCurrentActivity;
    public static List<BaseActivity> mActivities = new LinkedList<>();

    private PermissionListener mPermissionListener;


    public SlidingMenu slidingMenu;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //设置布局文件
        setContentView(provideContentViewId());

        //设置滑动菜单
        if (enableSlideOpen()) {

            initSlidingMenu();
        }

        //注册IOC 或者 ButterKnife
        ButterKnife.bind(this);

        //初始化的时候将其添加到集合中
        synchronized (mActivities) {
            //应用一打开，就将BaseActivity添加到Activity的集合中
            mActivities.add(this);
        }

        //实例化presenter
        mPresenter = createPresenter();

        if (mPresenter != null){
            mPresenter.attach(this);
        }

        initTitle();
        initView();
        initData();
        initListener();
    }


    private void initSlidingMenu() {


        slidingMenu = new SlidingMenu(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screeWidth = displayMetrics.widthPixels;
        int screeHeight = displayMetrics.heightPixels;

        //slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this, 200));
        slidingMenu.setBehindOffset((int) (screeWidth * 0.2));  //设置主页占据的宽度

        slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN); //设置滑动模式：滑动边缘，全屏滑动，不可以滑动
        slidingMenu.setMode(SlidingMenu.LEFT );    //滑动无效
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);    //menu边缘阴影宽度
        slidingMenu.setFadeDegree(0.35f);   //menu褪色程度
        slidingMenu.setBehindScrollScale(0.5f);    //菜单滚动速度比内容滚动速度。。。
        slidingMenu.setMenu(R.layout.activity_leftmenu); //设置菜单部分布局
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW );   //包括ActionBar


        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        LeftMenuFragment mLeftMenuFragment = new LeftMenuFragment();
        transaction.add(R.id.left_menu_fl, mLeftMenuFragment);
        transaction.commit();
    }

    public boolean enableSlideOpen() {
        return false;
    }

    public abstract T   createPresenter();

    /**
     * 提供布局文件 的 Id
     *
     * @return
     */
    public abstract int provideContentViewId();


    public void initTitle() {
    }

    protected abstract void initView();

    public void initData() {

    }

    public void initListener() {
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    /**
     * 得到当前正在显示得Activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    /**
     * 申请运行时权限
     *
     * @param permissions        需要申请的权限
     * @param permissionListener 权限申请结果的回调接口
     */
    public void requestRuntimePermission(String[] permissions, PermissionListener permissionListener) {
        mPermissionListener = permissionListener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                //如果请求的权限没有被同意，就添加到权限集合中
                permissionList.add(permission);
            }
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            permissionListener.onGranted();
        }
    }

    //用于请求权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    //定义一个存放被拒绝了的权限的集合
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mPermissionListener.onGranted();
                    } else {
                        mPermissionListener.onDenied(deniedPermissions);
                    }
                }
                break;
        }
    }

    //判断是否注册了EventBus
    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    //注册EventBus
    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    //取消注册EventBus
    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }
    }

    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity) {
            //如果是主页面
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
                Toast.makeText(this, "再按一次，退出应用", Toast.LENGTH_SHORT).show();
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        synchronized (mActivities) {
            mActivities.remove(this);
        }

        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

}

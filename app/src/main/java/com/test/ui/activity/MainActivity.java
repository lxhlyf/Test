package com.test.ui.activity;

import android.widget.RadioGroup;

import com.test.R;
import com.test.helper.FragmentManagerHelper;
import com.test.model.event.TabRefreshCompletedEvent;
import com.test.ui.base.BaseSkinActivity;
import com.test.ui.fragment.ScheduleFragment;
import com.test.ui.fragment.daily.DailyFragment;
import com.test.ui.fragment.dictionary.DictionaryFragment;
import com.test.ui.fragment.video.VideoFragment;
import com.test.utils.StatusBarUtil;
import com.test.utils.UIUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;


public class MainActivity extends BaseSkinActivity {

    @BindView(R.id.main_rg)
    public RadioGroup mRadioGroup;

    private DailyFragment mDailyFragment;
    private VideoFragment mVideoFragment;
    private DictionaryFragment mDictionaryFragment;
    private ScheduleFragment mScheduleFragment;

    private FragmentManagerHelper mFragmentManagerHelper;

    int[] colors = {
            R.color.follow_item_tip_night
    };

    @Override
    public int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initTitle() {
        StatusBarUtil.statusBarTintColor(this, UIUtils.getColor(colors[0]));
    }

    @Override
    protected void initView() {

        //添加主页
        mFragmentManagerHelper = new FragmentManagerHelper(this, getSupportFragmentManager(), R.id.main_tab_fl);
        mDailyFragment = new DailyFragment();
        mFragmentManagerHelper.add(mDailyFragment);
        mRadioGroup.check(R.id.home_rb);
    }

    @Override
    public boolean enableSlideOpen() {
        return true;
    }

    @Override
    public void initData() {

    }


    @Override
    public void initListener() {


        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.find_rb:
                        if (mVideoFragment == null) {
                            mVideoFragment = new VideoFragment();
                        }
                        mFragmentManagerHelper.switchFragment(mVideoFragment);
                        break;
                    case R.id.new_rb:
                        if (mDictionaryFragment == null) {
                            mDictionaryFragment = new DictionaryFragment();
                        }
                        mFragmentManagerHelper.switchFragment(mDictionaryFragment);
                        break;
                    case R.id.message_rb:
                        if (mScheduleFragment == null) {
                            mScheduleFragment = new ScheduleFragment();
                        }
                        mFragmentManagerHelper.switchFragment(mScheduleFragment);
                        break;
                    default:
                        mFragmentManagerHelper.switchFragment(mDailyFragment);
                        break;
                }


            }
        });
    }


    /**
     * 向newsListFragment 发送刷新事件。
     */
    public void postTabRefreshEvent(int checkedId){
//        TabRefreshEvent event = new TabRefreshEvent();
//        event.setBottomBarItem(rb);
//        event.setChannelCode();
//        event.setHomeTab();
//        EventBus.getDefault().post(event);
    }

    /**
     *   该事件由 NewListFragment 发送
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100, sticky = true)
    public void onRefreshCompletedEvent(TabRefreshCompletedEvent event){


    }

    @Override
    protected void onStart() {
        super.onStart();
        registerEventBus(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragmentManagerHelper.clearFragment();
        mFragmentManagerHelper = null;
    }
}

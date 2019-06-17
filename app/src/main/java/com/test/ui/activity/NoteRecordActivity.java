package com.test.ui.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.R;
import com.test.db.noteRecord.DiaryDatabaseHelper;
import com.test.db.noteRecord.GetDate;
import com.test.model.entity.DiaryBean;
import com.test.model.event.RefreshViewEvent;
import com.test.model.event.StartUpdateDiaryEvent;
import com.test.ui.adapter.DiaryAdapter;
import com.test.ui.base.BaseSkinActivity;
import com.uikit.refreshlayout.BGARefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 简言 on 2019/2/21  16:58.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.test.ui.activity
 * Description :
 */
public class NoteRecordActivity extends BaseSkinActivity {


    @BindView(R.id.main_iv_circle)
    ImageView mIvCircle;
    @BindView(R.id.main_tv_date)
    TextView mTvDate;
    @BindView(R.id.main_tv_content)
    TextView mTvContent;
    @BindView(R.id.item_ll_control)
    LinearLayout mLlControl;
    @BindView(R.id.item_first)
    LinearLayout mItemFirst;
    @BindView(R.id.main_rv_show_diary)
    RecyclerView mRvShowDiary;
    @BindView(R.id.main_ll_main)
    LinearLayout mLlMain;
    @BindView(R.id.main_fab_enter_edit)
    FloatingActionButton mFabEnterEdit;
    @BindView(R.id.main_rl_main)
    RelativeLayout mRlMain;
    @BindView(R.id.main_ll_date)
    LinearLayout mLlDate;
    @BindView(R.id.main_ll_content)
    LinearLayout mLlContent;

    private List<DiaryBean> mDiaryBeanList;
    private DiaryDatabaseHelper mDatabaseHelper;

    private static final String DB_DIARY_NAME = "Diary.db";

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NoteRecordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int provideContentViewId() {
        return R.layout.activity_note_record;
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerEventBus(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDiaryBeanList();
        initSDData();
    }

    @Override
    protected void initView() {
        //如果存在就打开，如果不存在就创建
        mDatabaseHelper = new DiaryDatabaseHelper(this, DB_DIARY_NAME, null, 1);
        getDiaryBeanList();
        initSDData();
    }


    private void initSDData() {
        mTvDate.setText(GetDate.getDate());
        mRvShowDiary.setLayoutManager(new LinearLayoutManager(this));
        mRvShowDiary.setAdapter(new DiaryAdapter(this, mDiaryBeanList));
    }

    //从数据库中获取，数据然后进行显示
    private void getDiaryBeanList() {
        mDiaryBeanList = new ArrayList<>();
        List<DiaryBean> diaryList = new ArrayList<>();
        SQLiteDatabase sqliteDatabase = mDatabaseHelper.getWritableDatabase();
        Cursor cursor = sqliteDatabase.query("Diary", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String dateSystem = GetDate.getDate().toString();
                if (date.equals(dateSystem)) {
                    mLlMain.removeView(mItemFirst);
                    break;
                }

            } while (cursor.moveToNext());
        }

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String tag = cursor.getString(cursor.getColumnIndex("tag"));
                mDiaryBeanList.add(new DiaryBean(date, title, content, tag));

            } while (cursor.moveToNext());
        }
        cursor.close();

        for (int i = mDiaryBeanList.size() - 1; i >= 0; i--) {
            diaryList.add(mDiaryBeanList.get(i));
        }
        mDiaryBeanList = diaryList;
    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100, sticky = true)
    public void startUpdateDiaryActivity(StartUpdateDiaryEvent event) {
        DiaryBean diaryBean = event.getDiaryBean();
        String title = diaryBean.getTitle();
        String content = diaryBean.getContent();
        String tag = diaryBean.getTag();
        finish();
        UpdateDiaryActivity.startActivity(this, title, content, tag);
    }


    @OnClick(R.id.main_fab_enter_edit)
    public void onViewClicked() {

        finish();
        AddDiaryActivity.startActivity(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterEventBus(this);
    }
}

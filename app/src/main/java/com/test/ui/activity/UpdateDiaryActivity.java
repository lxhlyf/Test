package com.test.ui.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.R;
import com.test.db.noteRecord.DiaryDatabaseHelper;
import com.test.db.noteRecord.GetDate;
import com.test.model.event.RefreshViewEvent;
import com.test.ui.base.BaseSkinActivity;
import com.test.view.LinedEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;

/**
 * Created by 简言 on 2019/2/21  20:46.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.test.ui.activity
 * Description :
 */
public class UpdateDiaryActivity extends BaseSkinActivity {

    @BindView(R.id.update_diary_tv_date)
    TextView mUpdateDiaryTvDate;
    @BindView(R.id.update_diary_et_title)
    EditText mUpdateDiaryEtTitle;
    @BindView(R.id.update_diary_et_content)
    LinedEditText mUpdateDiaryEtContent;
    @BindView(R.id.update_diary_fab_back)
    FloatingActionButton mUpdateDiaryFabBack;
    @BindView(R.id.update_diary_fab_add)
    FloatingActionButton mUpdateDiaryFabAdd;
    @BindView(R.id.update_diary_fab_delete)
    FloatingActionButton mUpdateDiaryFabDelete;
    @BindView(R.id.right_labels)
    FloatingActionsMenu mRightLabels;
    @BindView(R.id.update_diary_tv_tag)
    TextView mTvTag;
    @BindView(R.id.home_iv_draw)
    ImageView mIvDraw;
    @BindView(R.id.home_tv_title_normal)
    TextView mTvTitle;
    @BindView(R.id.home_tv_title_center)
    TextView mTvCenter;
    @BindView(R.id.home_iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.contacts_tab_rl)
    LinearLayout mContactsTabRl;

    private DiaryDatabaseHelper mHelper;

    public static void startActivity(Context context, String title, String content, String tag) {
        Intent intent = new Intent(context, UpdateDiaryActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    @Override
    public int provideContentViewId() {
        return R.layout.activity_update_diary;
    }

    @Override
    protected void initView() {

        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
        Intent intent = getIntent();
        initToolbar();
        initView(intent);
        String url = "http://ww3.sinaimg.cn/large/7a8aed7bgw1eswencfur6j20hq0qodhs.jpg";
    }

    private void initToolbar() {
        mIvDraw.setImageResource(R.drawable.app_back);
        mIvDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvTitle.setText("修改日记");
        mIvMenu.setVisibility(View.GONE);
    }

    private void initView(Intent intent) {
        mUpdateDiaryTvDate.setText("今天，" + GetDate.getDate());
        mUpdateDiaryEtTitle.setText(intent.getStringExtra("title"));
        mUpdateDiaryEtContent.setText(intent.getStringExtra("content"));
        mTvTag.setText(intent.getStringExtra("tag"));
    }

    @OnClick({R.id.home_iv_draw, R.id.update_diary_fab_back, R.id.update_diary_fab_add, R.id.update_diary_fab_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_iv_draw: //结束当前页面，返回上一个界面
                finish();
                NoteRecordActivity.startActivity(UpdateDiaryActivity.this);
                break;
            case R.id.update_diary_fab_back:  //删除按钮，弹出弹框，询问是否确认删除
                showTips();
                break;
            case R.id.update_diary_fab_add:
                SQLiteDatabase dbUpdate = mHelper.getWritableDatabase();
                ContentValues valuesUpdate = new ContentValues();
                String title = mUpdateDiaryEtTitle.getText().toString();
                String content = mUpdateDiaryEtContent.getText().toString();
                valuesUpdate.put("title", title);
                valuesUpdate.put("content", content);
                dbUpdate.update("Diary", valuesUpdate, "title = ?", new String[]{title});
                dbUpdate.update("Diary", valuesUpdate, "content = ?", new String[]{content});
                finish();
                NoteRecordActivity.startActivity(UpdateDiaryActivity.this);
                break;
            case R.id.update_diary_fab_delete:
                finish();
                NoteRecordActivity.startActivity(UpdateDiaryActivity.this);
                break;
        }
    }

    private void showTips() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("确定要删除该日记吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String tag = mTvTag.getText().toString();
                SQLiteDatabase dbDelete = mHelper.getWritableDatabase();
                dbDelete.delete("Diary", "tag = ?", new String[]{tag});
                finish();
                NoteRecordActivity.startActivity(UpdateDiaryActivity.this);

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

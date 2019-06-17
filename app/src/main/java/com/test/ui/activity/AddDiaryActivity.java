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
import com.test.ui.base.BaseSkinActivity;
import com.test.view.LinedEditText;

import butterknife.BindView;
import butterknife.OnClick;
import cc.trity.floatingactionbutton.FloatingActionButton;
import cc.trity.floatingactionbutton.FloatingActionsMenu;

/**
 * Created by 简言 on 2019/2/21  17:20.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.test.ui.activity
 * Description :
 */
public class AddDiaryActivity extends BaseSkinActivity {

    @BindView(R.id.add_diary_et_title)
    EditText mAddDiaryEtTitle;
    @BindView(R.id.add_diary_et_content)
    LinedEditText mAddDiaryEtContent;
    @BindView(R.id.add_diary_fab_back)
    FloatingActionButton mAddDiaryFabBack;
    @BindView(R.id.add_diary_fab_add)
    FloatingActionButton mAddDiaryFabAdd;
    @BindView(R.id.right_labels)
    FloatingActionsMenu mRightLabels;
    @BindView(R.id.home_iv_draw)
    ImageView mIvDraw;
    @BindView(R.id.home_tv_title_normal)
    TextView mTvTitle;
    @BindView(R.id.home_iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.contacts_tab_rl)
    LinearLayout mContactsTabRl;

    private DiaryDatabaseHelper mHelper;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int provideContentViewId() {
        return R.layout.activity_add_note_record;
    }

    @Override
    protected void initView() {
        initToolbar();
        mAddDiaryEtTitle.setText(getIntent().getStringExtra("title"));
        mAddDiaryEtContent.setText(getIntent().getStringExtra("content"));
        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);
    }

    private void initToolbar() {
        mIvDraw.setImageResource(R.drawable.app_back);
        mTvTitle.setText("添加日记");
        mIvMenu.setVisibility(View.GONE);
    }

    @OnClick({R.id.home_iv_draw, R.id.add_diary_et_title, R.id.add_diary_et_content, R.id.add_diary_fab_back, R.id.add_diary_fab_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_iv_draw:
                backToDiaryFragment();
            case R.id.add_diary_et_title:
                break;
            case R.id.add_diary_et_content:
                break;
            case R.id.add_diary_fab_back:
                String date = GetDate.getDate().toString();
                String tag = String.valueOf(System.currentTimeMillis());
                String title = mAddDiaryEtTitle.getText().toString() + "";
                String content = mAddDiaryEtContent.getText().toString() + "";
                if (!title.equals("") || !content.equals("")) {
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("date", date);
                    values.put("title", title);
                    values.put("content", content);
                    values.put("tag", tag);
                    db.insert("Diary", null, values);
                    values.clear();
                }
                finish();
                NoteRecordActivity.startActivity(AddDiaryActivity.this);
                break;
            case R.id.add_diary_fab_add:
                backToDiaryFragment();
                break;
        }
    }

    private void backToDiaryFragment() {
        final String dateBack = GetDate.getDate().toString();
        final String titleBack = mAddDiaryEtTitle.getText().toString();
        final String contentBack = mAddDiaryEtContent.getText().toString();
        if (!titleBack.isEmpty() || !contentBack.isEmpty()) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("是否保存日记内容？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("date", dateBack);
                    values.put("title", titleBack);
                    values.put("content", contentBack);
                    db.insert("Diary", null, values);
                    values.clear();
                    finish();
                    NoteRecordActivity.startActivity(AddDiaryActivity.this);
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.home_iv_draw)
    public void onViewClicked() {
        finish();
    }
}

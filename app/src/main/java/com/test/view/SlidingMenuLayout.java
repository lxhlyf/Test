package com.test.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.test.R;


/**
 * Created by 简言 on 2019/2/6.
 * 努力吧 ！ 少年 ！
 */

public class SlidingMenuLayout extends LinearLayout implements View.OnClickListener {

    private Context context;

    private ViewGroup sRelativeLayout;

    private View sIcon;
    private View sName;
    private View sSign;

    public SlidingMenuLayout(Context context) {
        this(context, null);
    }

    public SlidingMenuLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenuLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        sRelativeLayout = (ViewGroup) getChildAt(0);

        sIcon = sRelativeLayout.getChildAt(0);
        sName = sRelativeLayout.getChildAt(1);
        sSign = sRelativeLayout.getChildAt(2);

        sIcon.setOnClickListener(this);
        sName.setOnClickListener(this);
        sSign.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.left_menu_icon:

                Toast.makeText(context, "图标", Toast.LENGTH_SHORT).show();
                Log.i("sRelativeLayout", "——点击事件 ");
                break;
            case R.id.left_menu_name:

                Toast.makeText(context, "名字", Toast.LENGTH_SHORT).show();
                Log.i("sRelativeLayout", "——点击事件 ");
                break;
            case R.id.left_menu_sign:

                Toast.makeText(context, "签名", Toast.LENGTH_SHORT).show();
                Log.i("sRelativeLayout", "——点击事件 ");
                break;
        }
    }
}

package com.test.ui.fragment.leftMenu;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.R;
import com.test.data.Configs;
import com.test.data.Constant;
import com.test.ui.activity.CameraActivity;
import com.test.ui.activity.MainActivity;
import com.test.ui.activity.NoteRecordActivity;
import com.test.ui.adapter.LeftMenuFragmentAdapter;
import com.test.ui.base.BaseActivity;
import com.test.ui.base.BaseFragment;
import com.test.ui.base.BasePresenter;
import com.test.utils.BitmapHelper;
import com.test.utils.CropUtil;
import com.test.utils.FileUtils;
import com.test.utils.GlideUtils;
import com.test.utils.SharePreUtils;
import com.test.utils.UIUtils;
import com.test.view.tool.SelectHeadTools;
import com.wq.photo.widget.PickConfig;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by 简言 on 2019/2/6.
 * 努力吧 ！ 少年 ！
 */

public class LeftMenuFragment extends BaseFragment {

    @BindView(R.id.left_menu_icon)
    public CircleImageView lIcon;
    @BindView(R.id.left_menu_name)
    public TextView lName;
    @BindView(R.id.left_menu_sign)
    public TextView lSign;
    @BindView(R.id.left_menu_list)
    public ListView lListView;

    private boolean isTake;

    private LeftMenuFragmentAdapter mLeftMenuFragmentAdapter;

    @Override
    public BasePresenter createPresenterProxy1() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_leftmenu;
    }

    @Override
    protected void initView(View rootView) {

        String pathHead = SharePreUtils.getString(Constant.HEAD_ICON_PATH, "");
        if (!TextUtils.isEmpty(pathHead) && pathHead != null) {
            Log.i("pathHead", ""+pathHead);
            byte[] bytes = null;
            try {
                bytes = FileUtils.loadBitmap(pathHead);
                if (bytes != null){
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (bitmap!= null)
                        lIcon.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mLeftMenuFragmentAdapter = new LeftMenuFragmentAdapter(mActivity);
        lListView.setAdapter(mLeftMenuFragmentAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {


        lListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                BaseActivity mainActivity = (MainActivity) context;
                mainActivity.slidingMenu.toggle();

                if (position == 0){
                    Intent intent = new Intent(mActivity, NoteRecordActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @OnClick(R.id.left_menu_icon)
    public void onClick(View view) {

        if (!FileUtils.isSDCardAvailable()) {
            Toast.makeText(mActivity, "没有找到SD卡，请检查SD卡是否存在", Toast.LENGTH_SHORT).show();
            return;
        }

        BaseActivity baseActivity = (BaseActivity) mActivity;
        baseActivity.slidingMenu.toggle();

        isTake = SelectHeadTools.openDialog(mActivity, this);

    }


    private int position;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                Toast.makeText(mActivity, "拍照的回调结果。", Toast.LENGTH_SHORT).show();
                position = 0;
               SelectHeadTools.startPhotoZoom(this, Uri.fromFile(SelectHeadTools.currentImageFile), 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
                Toast.makeText(mActivity, "相册的回调结果。", Toast.LENGTH_SHORT).show();
                if (data == null) {
                    return;
                }
                position = 1;
                CropUtil.cropImageUri(this, data.getData(), Uri.fromFile(SelectHeadTools.currentImageFile), 600, 600, Configs.SystemPicture.PHOTO_REQUEST_CUT);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_CUT:  //接收处理返回的图片结果


                Toast.makeText(mActivity, "进行图片处理的回调结果。", Toast.LENGTH_SHORT).show();
                if (data == null) {
                    return;
                }

                if (position == 0){

                    Bitmap bitmap = data.getExtras().getParcelable("data");
                    lIcon.setImageBitmap(bitmap);
                }else {

                    Bitmap bitmap = CropUtil.decodeUriAsBitmap(mActivity, data.getData());
                    lIcon.setImageBitmap(bitmap);
                }

                SharePreUtils.putString(Constant.HEAD_ICON_PATH, SelectHeadTools.currentImageFile.getPath());
                break;
        }

    }

    @Override
    protected void loadData() {

    }
}

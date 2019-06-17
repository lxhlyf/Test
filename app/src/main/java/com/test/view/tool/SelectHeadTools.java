package com.test.view.tool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.test.data.Configs;
import com.test.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by ZYMAppOne on 2015/12/16.
 */
public class SelectHeadTools {

    private static boolean isTake = false;

    public static File currentImageFile = null;

    /*****
     * 打开选择框
     * @param context Context  Activity上下文对象
     */
    public static boolean openDialog(Activity context, final Fragment fragment){

        new ActionSheetDialog(context)
                .builder()
                .setTitle("选择图片")
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        createCurrentFile();
                        startCameraPicCut(fragment,FileUtils.getImageContentUri(context, currentImageFile));
                        isTake = true;
                    }
                })
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        createCurrentFile();
                        startImageCaptrue(fragment);
                        isTake = true;
                    }
                })
                .show();

        return isTake;
    }

   private static void createCurrentFile(){

       //定义一个保存图片的File变量
       File dir = new File(Environment.getExternalStorageDirectory(), Configs.SystemPicture.SAVE_DIRECTORY);
       if (dir.exists()) {
           dir.mkdirs();
       }

       currentImageFile = new File(dir, Configs.SystemPicture.SAVE_PIC_NAME);
       if (!currentImageFile.exists()) {
           try {
               currentImageFile.createNewFile();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }

    /****
     * 调用系统的拍照功能
     * @param fragment Activity上下文对象
     * @param uri  Uri
     */
    private static void startCameraPicCut(Fragment fragment,Uri uri) {
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra("camerasensortype", 2);// 调用前置摄像头
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", true);// 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        fragment.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO);
    }
    /***
     * 调用系统的图库
     * @param fragment Activity上下文对象
     */
    //ACTION_GET_CONTENT
    //Intent.ACTION_PICK  拿的是图库
    private static void startImageCaptrue(Fragment fragment) {
//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        //intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");


        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        fragment.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_GALLERY);

    }

    /*****
     * 进行截图
     * @param context Activity上下文对象
     * @param uri  Uri
     * @param size  大小
     */
    public static void startPhotoZoom(Fragment context,Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_CUT);
    }

}

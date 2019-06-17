package com.test.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class FileUtils {

    public static final String ROOT_DIR = "Android/data/"
            + UIUtils.getPackageName();
    public static final String DOWNLOAD_DIR = "download";
    public static final String CACHE_DIR = "cache";
    public static final String ICON_DIR = "icon";

    public static final String APP_STORAGE_ROOT = "TouTiao";

    /**
     * 判断SD卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取下载目录
     */
    public static String getDownloadDir() {
        return getDir(DOWNLOAD_DIR);
    }

    /**
     * 获取缓存目录
     */
    public static String getCacheDir() {
        return getDir(CACHE_DIR);
    }

    /**
     * 获取icon目录
     */
    public static String getIconDir() {
        return getDir(ICON_DIR);
    }

    /**
     * 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录
     */
    public static String getDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getAppExternalStoragePath());
        } else {
            sb.append(getCachePath());
        }
        sb.append(name);
        sb.append(File.separator);
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            return null;
        }
    }

    /**
     * 获取SD下的应用目录
     */
    public static String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 获取SD下当前APP的目录
     */
    public static String getAppExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(APP_STORAGE_ROOT);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 获取应用的cache目录
     */
    public static String getCachePath() {
        File f = UIUtils.getContext().getCacheDir();
        if (null == f) {
            return null;
        } else {
            return f.getAbsolutePath() + "/";
        }
    }

    /**
     * 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 产生图片的路径，这里是在缓存目录下
     */
    public static String generateImgePathInStoragePath() {
        return getDir(ICON_DIR) + String.valueOf(System.currentTimeMillis()) + ".jpg";
    }

    /**
     * 发起剪裁图片的请求
     *
     * @param activity    上下文
     * @param srcFile     原文件的File
     * @param output      输出文件的File
     * @param requestCode 请求码
     */
    public static void startPhotoZoom(Activity activity, File srcFile, File output, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(activity, srcFile), "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 480);
        // intent.putExtra("return-data", false);

        //        intent.putExtra(MediaStore.EXTRA_OUTPUT,
        //                Uri.fromFile(new File(FileUtils.picPath)));

        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection

        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 发起剪裁图片的请求
     *
     * @param fragment    上下文
     * @param srcFile     原文件的File
     * @param output      输出文件的File
     * @param requestCode 请求码
     */
    public static void startPhotoZoom(Fragment fragment, File srcFile, File output, int size, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(UIUtils.getContext(), srcFile), "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // intent.putExtra("return-data", false);

        //        intent.putExtra(MediaStore.EXTRA_OUTPUT,
        //                Uri.fromFile(new File(FileUtils.picPath)));

        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        fragment.startActivityForResult(intent, requestCode);
    }

    /**
     * 安卓7.0裁剪根据文件路径获取uri
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 复制bm
     *
     * @param bm
     * @return
     */
    public static String saveBitmap(Bitmap bm) {
        String croppath = "";
        try {
            File f = new File(FileUtils.generateImgePathInStoragePath());
            //得到相机图片存到本地的图片
            croppath = f.getPath();
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return croppath;
    }

    public static String saveBitmapByFile(String path, Bitmap bitmap) {

        String croppath = "";
        try {
            File f = new File(path);
            //得到相机图片存到本地的图片
            croppath = f.getPath();
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return croppath;
    }

    /**
     * 按质量压缩bm
     *
     * @param bm
     * @param quality 压缩率
     * @return
     */
    public static String saveBitmapByQuality(Bitmap bm, int quality) {
        String croppath = "";
        try {
            File f = new File(FileUtils.generateImgePathInStoragePath());
            //得到相机图片存到本地的图片
            croppath = f.getPath();
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return croppath;
    }

    public static void copy(File src, File dst) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0L, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }

    /**
     * 根据图片文件类型获取图片文件的后缀名
     *
     * @param filePath
     * @return
     */
    public static String getImageFileExt(String filePath) {
        HashMap<String, String> mFileTypes = new HashMap<String, String>();
        mFileTypes.put("FFD8FF", ".jpg");
        mFileTypes.put("89504E47", ".png");
        mFileTypes.put("474946", ".gif");
        mFileTypes.put("49492A00", ".tif");
        mFileTypes.put("424D", ".bmp");

        String value = mFileTypes.get(getFileHeader(filePath));
        String ext = TextUtils.isEmpty(value) ? ".jpg" : value;
        Log.i("file ext: ", "" + ext);
        return ext;
    }

    /**
     * 获取文件头信息
     *
     * @param filePath
     * @return
     */
    public static String getFileHeader(String filePath) {
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(filePath);
            byte[] b = new byte[3];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return value;
    }

    /**
     * 将byte字节转换为十六进制字符串
     *
     * @param src
     * @return
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        String header = builder.toString();
        Log.i("file header:", "" + header);
        return header;
    }

    /****
     * 通过目录和文件名来获取Uri
     * @param strFileDir   目录
     * @param strFileName   文件名
     * @return Uri
     * @throws IOException  IO异常
     */
    public static Uri getUriByFileDirAndFileName(String strFileDir, String strFileName) throws IOException {
        Uri uri = null;
        File fileDir = new File(Environment.getExternalStorageDirectory(), strFileDir);  //定义目录
        if (!fileDir.exists()) {   //判断目录是否存在
            fileDir.mkdirs();      //如果不存在则先创建目录
        }
        File file = new File(fileDir, strFileName);   //定义文件
        if (!file.exists()) {  //判断文件是否存在
            file.createNewFile();    //如果不存在则先创建文件
        }
        uri = Uri.fromFile(file);  //获取Uri
        return uri;
    }

    /**
     * 通过Uri返回File文件
     * 注意：通过相机的是类似content://media/external/images/media/97596
     * 通过相册选择的：file:///storage/sdcard0/DCIM/Camera/IMG_20150423_161955.jpg
     * 通过查询获取实际的地址
     *
     * @param uri     Uri
     * @param context 上下文对象  Activity
     * @return File
     */
    public static File getFileByUri(Activity context, Uri uri) {
        String path = null;
        if ("file".equals(uri.getScheme())) {
            path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=").append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}, buff.toString(), null, null);
                int index = 0;
                int dataIdx = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    index = cur.getInt(index);
                    dataIdx = cur.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    path = cur.getString(dataIdx);
                }
                cur.close();
                if (index == 0) {
                } else {
                    Uri u = Uri.parse("content://media/external/images/media/" + index);
                    System.out.println("temp uri is :" + u);
                }
            }
            if (path != null) {
                return new File(path);
            }
        } else if ("content".equals(uri.getScheme())) {
            // 4.2.2以后
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                path = cursor.getString(columnIndex);
            }
            cursor.close();

            return new File(path);
        }
        return null;
    }

    /**
     * 向SD卡中存放图片
     *
     * @param fileName
     * @param
     * @throws IOException
     */
    public static String savePictureToSd(String fileDirectoryName, String fileName, Bitmap bitmap) throws IOException {
        if (isSDCardAvailable()) {

            String filePath = Environment.getExternalStorageDirectory().getCanonicalFile() + "/" + fileDirectoryName + "/" + fileName;
            Log.i("filePath", "filePath" + filePath);
            FileOutputStream out = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            out.close();
            return filePath;
        }
        return "";
    }


    /**
     * 从SD卡中加载图片
     *
     * @param path getFileName   截取的值传进来
     * @return
     */
    public static byte[] loadBitmap(String path) throws IOException {
        String filePath = path;
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        InputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] temp = new byte[1024 * 10];
        int len;
        while ((len = in.read(temp)) != -1) {
            out.write(temp, 0, len);
            out.flush();
        }
        byte[] bytes = out.toByteArray();
        in.close();
        out.close();
        return bytes;
    }
}

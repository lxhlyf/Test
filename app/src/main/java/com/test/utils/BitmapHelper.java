package com.test.utils;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;

/**
 * Created by 简言 on 2019/2/20  16:18.
 * 努力吧 ！ 少年 ！
 * email : yifeng20161123@163.com
 *
 * @package : com.customview.utils
 * Description :
 */
public class BitmapHelper {

//    public byte[] getBytesByBitmap(Bitmap bitmap) {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//        return outputStream.toByteArray();
//    }

    public static byte[] getBytesByBitmap(Bitmap bitmap) {
        ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
        return buffer.array();
    }


}

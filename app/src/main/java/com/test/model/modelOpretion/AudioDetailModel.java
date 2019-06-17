package com.test.model.modelOpretion;

import com.test.constract.AudioDetailConstract;
import com.test.ui.base.BaseModel;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/16.
 * 努力吧 ！ 少年 ！
 */
public class AudioDetailModel extends BaseModel implements AudioDetailConstract.IAudioDetailModel {
    @Override
    public Observable getDetail(String itemId) {
        return getApiService().getDetail("api", "getPost", itemId, 1);
    }
}

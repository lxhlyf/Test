package com.test.model.modelOpretion;

import com.test.constract.YouDiaryConstract;
import com.test.ui.base.BaseModel;
import com.test.utils.TimeUtils;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/14.
 * 努力吧 ！ 少年 ！
 */
public class YouDiaryModel extends BaseModel implements YouDiaryConstract.IYouDiaryModel {
    @Override
    public Observable getYouDiaryResult(int page, int model, String pageId, String deviceId, String createTime) {
        return getApiService().getList("api","getList",page,model,pageId,createTime,"android","1.3.0", TimeUtils.getCurrentSeconds(), deviceId,1);
    }
}

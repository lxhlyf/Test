package com.test.model.modelOpretion;

import com.test.constract.ZhiHuNewsConstract;
import com.test.ui.base.BaseModel;
import com.test.utils.TimeUtils;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
public class ZhiHuNewsModel extends BaseModel implements ZhiHuNewsConstract.IZhiHuNewsModel {
    @Override
    public Observable getZhiHuNewsResult(int page, int model, String pageId, String deviceId, String createTime) {
        return getApiService().getList("api","getList",page,model,pageId,createTime,"android","1.3.0", TimeUtils.getCurrentSeconds(), deviceId,1);
    }

    @Override
    public Observable getRecommend(String deviceId) {
        return getApiService().getRecommend("home","Api","getLunar","android",deviceId,deviceId);
    }


}

package com.test.model.modelOpretion;

import com.test.constract.VideoDetailConstract;
import com.test.model.result.dandu.DetailEntity;
import com.test.model.result.dandu.YouDiaryResult;
import com.test.ui.base.BaseModel;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
public class VideoDetailModel extends BaseModel implements VideoDetailConstract.IVideoDetailModel {
    @Override
    public Observable<YouDiaryResult.Data<DetailEntity>> getDetail(String itemId) {

        return getApiService().getDetail("api", "getPost", itemId, 1);
    }
}

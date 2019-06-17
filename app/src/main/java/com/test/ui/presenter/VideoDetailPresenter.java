package com.test.ui.presenter;

import android.util.Log;

import com.test.constract.VideoDetailConstract;
import com.test.model.modelOpretion.VideoDetailModel;
import com.test.model.result.dandu.DetailEntity;
import com.test.model.result.dandu.YouDiaryResult;
import com.test.ui.base.BasePresenter;
import com.test.utils.UIUtils;

import rx.Subscriber;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
public class VideoDetailPresenter extends BasePresenter<VideoDetailConstract.IVideoDetailView, VideoDetailModel> implements VideoDetailConstract.IVideoDetailPresenter {
    @Override
    public void getDetail(String itemId) {

        addSubscription(getModel().getDetail(itemId), new Subscriber<YouDiaryResult.Data<DetailEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                getView().showOnFailure(e);
            }

            @Override
            public void onNext(YouDiaryResult.Data<DetailEntity> detailEntityData) {
                if (detailEntityData != null){
                    getView().updateListUI(detailEntityData.getDatas());
                }
            }
        });
    }
}

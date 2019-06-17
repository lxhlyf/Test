package com.test.ui.presenter;

import com.test.constract.AudioDetailConstract;
import com.test.model.modelOpretion.AudioDetailModel;
import com.test.model.result.dandu.DetailEntity;
import com.test.model.result.dandu.YouDiaryResult;
import com.test.ui.base.BasePresenter;

import rx.Subscriber;

/**
 * Created by 简言 on 2019/2/16.
 * 努力吧 ！ 少年 ！
 */
public class AudioDetailPresenter extends BasePresenter<AudioDetailConstract.IAudioDetailView, AudioDetailModel> implements AudioDetailConstract.IAudioDetailPresenter {
    @Override
    public void getDetail(String itemId) {

        addSubscription(getModel().getDetail(itemId), new Subscriber<YouDiaryResult.Data<DetailEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                getView().onErroe(e);
            }

            @Override
            public void onNext(YouDiaryResult.Data<DetailEntity> detailEntityData) {

                getView().updateListUI(detailEntityData.getDatas());
            }
        });
    }
}

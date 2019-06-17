package com.test.ui.presenter;

import com.test.constract.YouDiaryConstract;
import com.test.model.modelOpretion.YouDiaryModel;
import com.test.model.result.dandu.Item;
import com.test.model.result.dandu.YouDiaryResult;
import com.test.ui.base.BasePresenter;
import com.test.utils.ListUtils;

import java.util.List;

import rx.Subscriber;

/**
 * Created by 简言 on 2019/2/14.
 * 努力吧 ！ 少年 ！
 */
public class YouDiaryPresenter extends BasePresenter<YouDiaryConstract.IYouDiaryView, YouDiaryModel> implements YouDiaryConstract.IYouDiaryPresenter {
    @Override
    public void getYouDiaryResult(int page, int model, String pageId, String deviceId, String createTime) {

        addSubscription(getModel().getYouDiaryResult(page, model, pageId, deviceId, createTime), new Subscriber<YouDiaryResult.Data<List<Item>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                getView().onError(e);
            }

            @Override
            public void onNext(YouDiaryResult.Data<List<Item>> listData) {

                List<Item> datas = listData.getDatas();
                if (!ListUtils.isEmpty(datas)){
                    getView().onSucceed(datas);
                }
            }
        });
    }
}

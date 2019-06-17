package com.test.ui.presenter;



import com.test.constract.DictionaryConstract;
import com.test.model.modelOpretion.DictionaryModel;
import com.test.model.result.DictionaryAssocitiveResult;
import com.test.ui.base.BasePresenter;

import rx.Subscriber;

/**
 * Created by 简言 on 2019/2/9.
 * 努力吧 ！ 少年 ！
 */

public class DictionaryPresenter extends BasePresenter<DictionaryConstract.DictionaryView, DictionaryModel> implements DictionaryConstract.IDictionaryPresenter {

        @Override
    public void getAssocitiveResult(String letter) {

        addSubscription(getModel().getAssocitiveResult(letter), new Subscriber<DictionaryAssocitiveResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(DictionaryAssocitiveResult associtiveResult) {

                if (associtiveResult != null){
                    getView().onSucceed(associtiveResult);
                }
            }
        });
    }
}

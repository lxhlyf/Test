package com.test.constract;


import com.test.model.result.DictionaryAssocitiveResult;
import com.test.ui.base.BaseView;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/9.
 * 努力吧 ！ 少年 ！
 */

public class DictionaryConstract {

    public interface DictionaryView extends BaseView {

        void onLoading();
        void onError();
        void onSucceed(DictionaryAssocitiveResult result);
    }

    public interface IDictionaryPresenter{

        void getAssocitiveResult(String letter);
    }

    public interface IDictionaryModel{
        Observable getAssocitiveResult(String letter);
    }
}

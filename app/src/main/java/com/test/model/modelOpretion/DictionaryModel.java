package com.test.model.modelOpretion;


import com.test.constract.DictionaryConstract;
import com.test.ui.base.BaseModel;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/9.
 * 努力吧 ！ 少年 ！
 */

public class DictionaryModel extends BaseModel implements DictionaryConstract.IDictionaryModel{

    @Override
    public Observable getAssocitiveResult(String letter) {

        return getApiService().getDictionaryResult(letter,"eng",10,"json");
    }
}

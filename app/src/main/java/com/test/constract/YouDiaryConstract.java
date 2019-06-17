package com.test.constract;

import com.test.model.result.dandu.Item;
import com.test.model.result.dandu.YouDiaryResult;
import com.test.ui.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/14.
 * 努力吧 ！ 少年 ！
 */
public class YouDiaryConstract {

    public interface IYouDiaryView extends BaseView {

        void onLoading();
        void onError(Throwable e);
        void onSucceed( List<Item> datas);
    }

    public interface IYouDiaryPresenter{

        void getYouDiaryResult(int page, int model, String pageId, String deviceId, String createTime);
    }

    public interface IYouDiaryModel{
        Observable getYouDiaryResult(int page, int model, String pageId, String deviceId, String createTime);
    }
}

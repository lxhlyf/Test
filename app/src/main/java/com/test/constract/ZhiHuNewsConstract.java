package com.test.constract;

import com.test.model.result.dandu.Item;
import com.test.model.result.dandu.YouDiaryResult;
import com.test.model.result.zhihu.ZhiHuDetailResult;
import com.test.model.result.zhihu.ZhiHuNewsResult;
import com.test.ui.base.BaseView;

import java.util.List;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
public class ZhiHuNewsConstract {

    public interface IZhiHuNewsView extends BaseView{

        void onError(Throwable e);
        void onSuccess(YouDiaryResult.Data<List<Item>> listData);

        void showNoMore();
        void showOnFailure();
        void showLunar(String content);
    }

    public interface IZhiHuNewsPresenter{

        void getZhiHuNewsResult(int page, int model, String pageId, String deviceId, String createTime);

         void getRecommend(String deviceId);
    }

    public interface IZhiHuNewsModel{

        Observable getZhiHuNewsResult(int page, int model, String pageId, String deviceId, String createTime);

        Observable getRecommend(String deviceId);
    }
}

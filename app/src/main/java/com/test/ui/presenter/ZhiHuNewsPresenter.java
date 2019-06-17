package com.test.ui.presenter;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.constract.ZhiHuNewsConstract;
import com.test.model.modelOpretion.ZhiHuNewsModel;
import com.test.model.result.dandu.Item;
import com.test.model.result.dandu.YouDiaryResult;
import com.test.model.result.zhihu.ZhiHuDetailResult;
import com.test.model.result.zhihu.ZhiHuNewsResult;
import com.test.ui.base.BasePresenter;
import com.test.utils.TimeUtils;
import com.test.utils.UIUtils;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
public class ZhiHuNewsPresenter extends BasePresenter<ZhiHuNewsConstract.IZhiHuNewsView, ZhiHuNewsModel> implements ZhiHuNewsConstract.IZhiHuNewsPresenter{
    @Override
    public void getZhiHuNewsResult(int page, int model, String pageId, String deviceId, String createTime) {

        addSubscription(getModel().getZhiHuNewsResult(page, model, pageId, deviceId, createTime), new Subscriber<YouDiaryResult.Data<List<Item>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                getView().onError(e);
            }

            @Override
            public void onNext(YouDiaryResult.Data<List<Item>> listData) {

                if (listData != null){

                    int size = listData.getDatas().size();
                    if (size > 0) {
                        getView().onSuccess(listData);
                    } else {
                        getView().showNoMore();
                    }
                }
            }
        });
    }

    @Override
    public void getRecommend(String deviceId) {

        String key = TimeUtils.getDate("yyyyMMdd");

        addSubscription(getModel().getRecommend(deviceId), new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("ZhiHuNewsPresenter","onError:");
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                String key = TimeUtils.getDate("yyyyMMdd");
                try {
                    JsonParser jsonParser = new JsonParser();
                    JsonElement jel = jsonParser.parse(s);
                    JsonObject jsonObject = jel.getAsJsonObject();
                    jsonObject = jsonObject.getAsJsonObject("datas");
                    jsonObject = jsonObject.getAsJsonObject(key);
                    getView().showLunar(jsonObject.get("thumbnail").getAsString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}

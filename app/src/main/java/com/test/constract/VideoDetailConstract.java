package com.test.constract;

import com.test.model.result.dandu.DetailEntity;
import com.test.model.result.dandu.YouDiaryResult;
import com.test.ui.base.BaseView;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
public class VideoDetailConstract {


    public interface IVideoDetailView extends BaseView{

        void showLoading();
        void dismissLoading();
        void updateListUI(DetailEntity detailEntity);
        void showOnFailure(Throwable e);
    }

    public interface IVideoDetailModel{
        Observable<YouDiaryResult.Data<DetailEntity>> getDetail(String itemId);
    }

   public interface IVideoDetailPresenter{
       void getDetail(String itemId);
   }
}

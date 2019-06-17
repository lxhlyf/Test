package com.test.constract;

import com.test.model.result.dandu.DetailEntity;
import com.test.ui.base.BaseView;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/16.
 * 努力吧 ！ 少年 ！
 */
public class AudioDetailConstract {

    public interface IAudioDetailView extends BaseView {

        void updateListUI(DetailEntity detailEntity);

        void onErroe(Throwable e);
    }

    public interface IAudioDetailPresenter {

        public void getDetail(String itemId);
    }

    public interface IAudioDetailModel {

         Observable getDetail(String itemId);
    }
}

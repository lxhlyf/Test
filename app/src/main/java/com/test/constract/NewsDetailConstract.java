package com.test.constract;

import com.test.data.Constant;
import com.test.model.response.CommentResponse;
import com.test.model.result.toutiao.NewsDetail;
import com.test.ui.base.BaseView;

import rx.Observable;

/**
 * Created by 简言 on 2019/2/13.
 * 努力吧 ！ 少年 ！
 */
public class NewsDetailConstract {

    public interface INewsDetailView extends BaseView{

        void onGetNewsDetailSuccess(NewsDetail newsDetail);

        void onGetCommentSuccess(CommentResponse response);

        void onError();
    }

    public interface  NewsDetailPresenter{
         void getComment(String groupId, String itemId, int pageNow);
         void getNewsDetail(String url);
    }

    public interface NewsDetailModel{
        Observable getNewsDetail(String url);
        Observable getComment(String groupId, String itemId, String offset, String  count);
    }
}

package com.test.http.httpClass.retrofit;



import com.test.model.response.CommentResponse;
import com.test.model.response.ResultResponse;
import com.test.model.result.DictionaryAssocitiveResult;
import com.test.model.response.NewsResponse;
import com.test.model.response.VideoPathResponse;
import com.test.model.result.zhihu.ZhiHuDetailResult;
import com.test.model.result.zhihu.ZhiHuNewsResult;
import com.test.model.result.dandu.DetailEntity;
import com.test.model.result.dandu.Item;
import com.test.model.result.dandu.YouDiaryResult;
import com.test.model.result.toutiao.NewsDetail;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;


/**
 * Created by 简言 on 2019/2/8.
 * 努力吧 ！ 少年 ！
 */

public interface ApiService {

    @Headers({"url_name:Dictionary"})
    @GET("suggest")
    Observable<DictionaryAssocitiveResult> getDictionaryResult(@Query("q") String letter, @Query("le") String language, @Query("num") int num, @Query("doctype") String docType);


    /******************今日头条*****************************/

    String GET_ARTICLE_LIST = "api/news/feed/v62/?refer=1&count=20&loc_mode=4&device_id=34960436458&iid=13136511752";
    String GET_COMMENT_LIST = "article/v2/tab_comments/";

    /**
     * 获取新闻列表
     *
     * @param category 频道
     * @return  但会一个携带了请求数据的被观察者
     */
    @Headers({"url_name:Tops"})
    @GET(GET_ARTICLE_LIST)
    Observable<NewsResponse> getNewsList(@Query("category") String category, @Query("min_behot_time") long lastTime, @Query("last_refresh_sub_entrance_interval") long currentTime);

    @Headers({
            "Content-Type:application/x-www-form-urlencoded; charset=UTF-8",
            "Cookie:PHPSESSIID=334267171504; _ga=GA1.2.646236375.1499951727; _gid=GA1.2.951962968.1507171739; Hm_lvt_e0a6a4397bcb500e807c5228d70253c8=1507174305;Hm_lpvt_e0a6a4397bcb500e807c5228d70253c8=1507174305; _gat=1",
            "Origin:http://toutiao.iiilab.com",
    })
    @POST("http://service.iiilab.com/video/toutiao")
    Observable<VideoPathResponse> getVideoPath(@Query("link") String link, @Query("r") String r, @Query("s") String s);

    /**
     * 获取评论列表数据
     *
     * @param groupId
     * @param itemId
     * @param offset
     * @param count
     * @return
     */
    @GET(GET_COMMENT_LIST)
    Observable<CommentResponse> getComment(@Query("group_id") String groupId, @Query("item_id") String itemId, @Query("offset") String offset, @Query("count") String count);

    /**
     * 获取新闻详情
     */
    @GET
    Observable<ResultResponse<NewsDetail>> getNewsDetail(@Url String url);


    /**********************单独*****************************/

    /**
     * <p>分类列表</p>
     * <p>http://static.owspace.com/?c=api&a=getList&p=1&model=1&page_id=0&create_time=0&client=android&version=1.3.0&time=1467867330&device_id=866963027059338&show_sdv=1</p>
     *
     * @param c
     * @param a
     * @param page
     * @param model(0:首页，1：文字，2：声音，3：影像，4：单向历)
     * @param pageId
     * @param time
     * @param deviceId
     * @param show_sdv
     * @return
     */
    @Headers({"url_name:DanDu"})
    @GET("/")
    Observable<YouDiaryResult.Data<List<Item>>> getList(@Query("c") String c, @Query("a") String a, @Query("p") int page, @Query("model") int model, @Query("page_id") String pageId, @Query("create_time") String createTime, @Query("client") String client, @Query("version") String version, @Query("time") long time, @Query("device_id") String deviceId, @Query("show_sdv") int show_sdv);

    /**
     * http://static.owspace.com/?c=api&a=getPost&post_id=292296&show_sdv=1
     * <p>详情页</p>
     *
     * @param c
     * @param a
     * @param post_id
     * @param show_sdv
     * @return
     */
    @Headers({"url_name:DanDu"})
    @GET("/")
    Observable<YouDiaryResult.Data<DetailEntity>> getDetail(@Query("c") String c, @Query("a") String a, @Query("post_id") String post_id, @Query("show_sdv") int show_sdv);


    /**
     * http://static.owspace.com/index.php?m=Home&c=Api&a=getLunar&client=android&device_id=866963027059338&version=866963027059338
     * @return
     */
    @GET("index.php")
    Observable<String> getRecommend(@Query("m") String m,@Query("c") String api,@Query("a") String a,@Query("client") String client,@Query("version") String version, @Query("device_id") String deviceId);


    /**
     * 知乎
     */
    @Headers({"url_name:ZhiHu"})
    @GET("/api/3/news/hot")
    Observable<ZhiHuNewsResult> getZhiHuNewsResult();

    @Headers({"url_name:ZhiHu"})
    @GET("/api/2/news/{path}")
    Observable<ZhiHuDetailResult> getZhiHuNewsDetailResult(@Path("path") int newsId );



}

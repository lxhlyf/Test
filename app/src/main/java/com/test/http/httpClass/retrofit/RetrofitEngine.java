package com.test.http.httpClass.retrofit;

import android.os.Environment;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.test.app.MyApp;
import com.test.utils.NetWorkUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by 简言 on 2019/2/8.
 * 努力吧 ！ 少年 ！
 */

public class RetrofitEngine {

    private ApiService apiService;
    private static RetrofitEngine retrofitEngine;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    /**
     * 第一种缓存策略
     * 当有网的时候，判断判断缓存是否有效，如果没有效，就去请求网络，并且设置缓存的有效时间为30秒
     * 当没有网络的时候，就设置为强制缓存，只能从缓存中读取。
     */
    private Interceptor mCacheRequestInterceptor = chain -> {

        Request request = chain.request();
        if (!NetWorkUtils.isNetworkAvailable(MyApp.getContext())) {

            request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }
        return chain.proceed(request);
    };

    private Interceptor mCacheResponse = chain -> {

        Response response = chain.proceed(chain.request());

        return response.newBuilder()
                .removeHeader("Cache-Control")
                .removeHeader("Pragma")
                .addHeader("Cache-Control", "max-age=" + 30).build();
    };

    /**
     * 第二种缓存策略
     * 总是去读取缓存，客户端，不进行任何缓存
     * 可后去响应期效之外365天中的响应
     */
    private Interceptor mCacheInterceptor = chain -> {

        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();

        Request request = chain.request();
        if (!NetWorkUtils.isNetworkAvailable(MyApp.getContext())) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetWorkUtils.isNetworkAvailable(MyApp.getContext())) {
            int maxAge = 0; // read from cache
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };

    private Interceptor mBaseUrlChangeInterceptor = chain -> {

        //获取request
        Request request = chain.request();
        //获取request的创建者builder
        Request.Builder builder = request.newBuilder();
        //从request中获取headers，通过给定的键url_name
        List<String> headerValues = request.headers("url_name");
        if (headerValues != null && headerValues.size() > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader("url_name");

            //匹配获得新的BaseUrl
            String headerValue = headerValues.get(0);
            Log.i("----headerValue----", "------" + headerValue);
            HttpUrl newBaseUrl = null;
            if ("Tops".equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(ApiConstant.TOP_BASE_SERVER_URL);
            } else if ("Dictionary".equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(ApiConstant.DICTIONARY_BASE_SERVER_URL);
            }else if ("DanDu".equals(headerValue)){
                newBaseUrl = HttpUrl.parse(ApiConstant.DAN_DU_BASE_SERVER_URL);
            }else if ("ZhiHu".equals(headerValue)){
                newBaseUrl = HttpUrl.parse(ApiConstant.ZHIHU_BASE_SERVER_URL);
            }

            //从request中获取原有的HttpUrl实例oldHttpUrl
            HttpUrl oldHttpUrl = request.url();
            //重建新的HttpUrl，修改需要修改的url部分
            HttpUrl newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl.scheme())
                    .host(newBaseUrl.host())
                    .port(newBaseUrl.port())
                    .build();

            Log.i("--------全路径-------", "------" + newFullUrl);
            //重建这个request，通过builder.url(newFullUrl).build()；
            //然后返回一个response至此结束修改
            return chain.proceed(builder.url(newFullUrl).build());
        } else {
            return chain.proceed(request);
        }
    };


    /**
     * 请求访问request和response拦截器
     */
    private Interceptor mLogInterceptor = chain -> {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.i("", "----------Request Start----------------");
        Log.e("", "| " + request.toString());
        Log.i("", "| Response:" + content);
        Log.i("", "----------Request End:" + duration + "毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    };

    /**
     * 增加头部信息的拦截器
     */
    private Interceptor mHeaderInterceptor = chain -> {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.108 Safari/537.36 2345Explorer/8.0.0.13547");
        builder.addHeader("Cache-Control", "max-age=0");
        builder.addHeader("Upgrade-Insecure-Requests", "1");
        builder.addHeader("X-Requested-With", "XMLHttpRequest");
        builder.addHeader("Cookie", "uuid=\"w:f2e0e469165542f8a3960f67cb354026\"; __tasessionId=4p6q77g6q1479458262778; csrftoken=7de2dd812d513441f85cf8272f015ce5; tt_webid=36385357187");
        return chain.proceed(builder.build());
    };

    private RetrofitEngine() {

        //cache url
        File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), "cache");
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(mBaseUrlChangeInterceptor)
                .addInterceptor(mHeaderInterceptor)//添加头部信息拦截器
                .addInterceptor(mLogInterceptor)//添加log拦截器
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.TOP_BASE_SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())  //将数据转换成Scalars格式
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .client(okHttpClient)  //设置一些OkHttp的自定义参数
                .build();

        apiService = retrofit.create(ApiService.class);

    }

    public static RetrofitEngine getInstance() {

        if (retrofitEngine == null) {
            synchronized (Object.class) {
                if (retrofitEngine == null) {
                    retrofitEngine = new RetrofitEngine();
                }
            }
        }
        return retrofitEngine;
    }

    public ApiService getApiService() {
        return apiService;
    }

}

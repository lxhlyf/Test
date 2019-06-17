package com.test.ui.activity;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.R;
import com.test.ui.base.BaseActivity;
import com.test.ui.base.BasePresenter;
import com.test.utils.StatusBarUtil;
import com.test.utils.UIUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 简言 on 2019/2/12.
 * 努力吧 ！ 少年 ！
 */
public class WebViewActivity extends BaseActivity {

    public static final String URL = "url";

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_author)
    TextView mTvTitle;
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;
    @BindView(R.id.wv_content)
    WebView mWvContent;

    @Override
    public void initTitle() {

        StatusBarUtil.statusBarTintColor(this, UIUtils.getColor(R.color.color_BDBDBD));
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int provideContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {

        String url = getIntent().getStringExtra(URL);
        mWvContent.loadUrl(url);
        initListener();

    }



    public void initListener() {
        WebSettings settings = mWvContent.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        mWvContent.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mPbLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mPbLoading.setVisibility(View.GONE);
            }
        });

        mWvContent.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                mPbLoading.setProgress(newProgress);
            }
        });

        mWvContent.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {  //手机菜单的返回键
                if (keyCode == KeyEvent.KEYCODE_BACK && mWvContent.canGoBack()) {  //表示按返回键
                    mWvContent.goBack();   //后退
                    return true;    //已处理
                }
            }
            return false;
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

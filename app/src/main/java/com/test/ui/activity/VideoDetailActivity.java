package com.test.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.sunfusheng.glideimageview.progress.GlideApp;
import com.test.R;
import com.test.constract.VideoDetailConstract;
import com.test.model.result.dandu.DetailEntity;
import com.test.model.result.dandu.Item;
import com.test.ui.base.BaseActivity;
import com.test.ui.presenter.VideoDetailPresenter;
import com.test.utils.UIUtils;
import com.test.utils.tools.AnalysisHTML;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * Created by 简言 on 2019/2/13.
 * 努力吧 ！ 少年 ！
 */
public class VideoDetailActivity extends BaseActivity<VideoDetailPresenter> implements VideoDetailConstract.IVideoDetailView {


    @BindView(R.id.favorite)
    ImageView favoriteIv;
    @BindView(R.id.write)
    ImageView writeIv;
    @BindView(R.id.share)
    ImageView shareIv;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.video)
    JzvdStd video;
    @BindView(R.id.news_top_img_under_line)
    View newsTopImgUnderLine;
    @BindView(R.id.news_top_type)
    TextView newsTopType;
    @BindView(R.id.news_top_date)
    TextView newsTopDate;
    @BindView(R.id.news_top_title)
    TextView newsTopTitle;
    @BindView(R.id.news_top_author)
    TextView newsTopAuthor;
    @BindView(R.id.news_top_lead)
    TextView newsTopLead;
    @BindView(R.id.news_top_lead_line)
    View newsTopLeadLine;
    @BindView(R.id.news_top)
    LinearLayout newsTop;
    @BindView(R.id.news_parse_web)
    LinearLayout newsParseWeb;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @Override
    public VideoDetailPresenter createPresenter() {
        return new VideoDetailPresenter();
    }

    @Override
    public int provideContentViewId() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle bundle = getIntent().getExtras();
        Item item = bundle.getParcelable("item");
        if (item != null){
            video.setUp(item.getVideo(), item.getTitle(),JzvdStd.SCREEN_WINDOW_LIST);
            GlideApp.with(this).load(item.getThumbnail()).centerCrop().into(video.thumbImageView);
            newsTopType.setText("视 频");
            newsTopLeadLine.setVisibility(View.VISIBLE);
            newsTopImgUnderLine.setVisibility(View.VISIBLE);
            newsTopDate.setText(item.getUpdate_time());
            newsTopTitle.setText(item.getTitle());
            newsTopAuthor.setText(item.getAuthor());
            newsTopLead.setText(item.getLead());
            mPresenter.getDetail(item.getId());
        }

    }

    private void initWebViewSetting() {
        WebSettings localWebSettings = this.webView.getSettings();
        localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setSupportZoom(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        localWebSettings.setUseWideViewPort(true);
        localWebSettings.setLoadWithOverviewMode(true);
    }

    /**
     *  View 的 回调
     */
    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void updateListUI(DetailEntity detailEntity) {
        if (detailEntity.getParseXML() == 1) {
            newsTopLeadLine.setVisibility(View.VISIBLE);
            newsTopImgUnderLine.setVisibility(View.VISIBLE);
            int i = detailEntity.getLead().trim().length();
            AnalysisHTML analysisHTML = new AnalysisHTML();
            analysisHTML.loadHtml(this, detailEntity.getContent(), analysisHTML.HTML_STRING, newsParseWeb, i);
        } else {
            initWebViewSetting();
            newsParseWeb.setVisibility(View.GONE);
            video.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            newsTop.setVisibility(View.GONE);
            webView.loadUrl(addParams2WezeitUrl(detailEntity.getHtml5(), false));
        }
    }

    public String addParams2WezeitUrl(String url, boolean paramBoolean) {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(url);
        localStringBuffer.append("?client=android");
        localStringBuffer.append("&device_id=" + UIUtils.getDeviceId(this));
        localStringBuffer.append("&version=" + "1.3.0");
        if (paramBoolean)
            localStringBuffer.append("&show_video=0");
        else {
            localStringBuffer.append("&show_video=1");
        }
        return localStringBuffer.toString();
    }

    @Override
    public void showOnFailure(Throwable e) {

    }

    @Override
    public void onBackPressed() {
        if (JzvdStd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        JzvdStd.releaseAllVideos();
        super.onPause();
    }
}

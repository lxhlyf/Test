package com.test.ui.fragment.daily;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunfusheng.glideimageview.progress.GlideApp;
import com.test.R;
import com.test.model.result.dandu.Item;
import com.test.ui.activity.AudioDetailActivity;
import com.test.ui.activity.DanDuDetailActivity;
import com.test.ui.activity.VideoDetailActivity;
import com.test.ui.base.BaseFragment;
import com.test.ui.base.BasePresenter;
import com.test.utils.UIUtils;

import butterknife.BindView;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
public class ZhiHuDetailFragment extends BaseFragment {

    @BindView(R.id.image_iv)
    ImageView imageIv;
    @BindView(R.id.type_container)
    LinearLayout typeContainer;
    @BindView(R.id.comment_tv)
    TextView commentTv;
    @BindView(R.id.like_tv)
    TextView likeTv;
    @BindView(R.id.readcount_tv)
    TextView readcountTv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.author_tv)
    TextView authorTv;
    @BindView(R.id.type_tv)
    TextView typeTv;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.image_type)
    ImageView imageType;
    @BindView(R.id.download_start_white)
    ImageView downloadStartWhite;
    @BindView(R.id.home_advertise_iv)
    ImageView homeAdvertiseIv;
    @BindView(R.id.pager_content)
    RelativeLayout pagerContent;

    private String title;

    public static Fragment instance(Item item) {
        Fragment fragment = new ZhiHuDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", item);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

        Item item = getArguments().getParcelable("item");
        int model = Integer.valueOf(item.getModel());
        if (model == 5) {
            pagerContent.setVisibility(View.GONE);
            homeAdvertiseIv.setVisibility(View.VISIBLE);
            GlideApp.with(this.getContext()).load(item.getThumbnail()).centerCrop().into(homeAdvertiseIv);
        } else {
            pagerContent.setVisibility(View.VISIBLE);
            homeAdvertiseIv.setVisibility(View.GONE);
            title = item.getTitle();
            GlideApp.with(this.getContext()).load(item.getThumbnail()).centerCrop().into(imageIv);
            commentTv.setText(item.getComment());
            likeTv.setText(item.getGood());
            readcountTv.setText(item.getView());
            titleTv.setText(item.getTitle());
            contentTv.setText(item.getExcerpt());
            authorTv.setText(item.getAuthor());
            typeTv.setText(item.getCategory());
            switch (model) {
                case 2:
                    imageType.setVisibility(View.VISIBLE);
                    downloadStartWhite.setVisibility(View.GONE);
                    imageType.setImageResource(R.drawable.library_video_play_symbol);
                    break;
                case 3:
                    imageType.setVisibility(View.VISIBLE);
                    downloadStartWhite.setVisibility(View.VISIBLE);
                    imageType.setImageResource(R.drawable.library_voice_play_symbol);
                    break;
                default:
                    downloadStartWhite.setVisibility(View.GONE);
                    imageType.setVisibility(View.GONE);
            }
        }

        typeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (model) {
                    case 5:
                        UIUtils.showToast("html --5");
                        Uri uri = Uri.parse(item.getHtml5());
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                    case 3:
                        UIUtils.showToast("AudioDetailActivity");
                        intent = new Intent(getActivity(), AudioDetailActivity.class);
                        intent.putExtra("item", item);
                        startActivity(intent);
                        break;
                    case 2:
                        UIUtils.showToast("VideoDetailActivity");
                        intent = new Intent(getActivity(), VideoDetailActivity.class);
                        intent.putExtra("item", item);
                        startActivity(intent);
                        break;
                    case 1:
                        UIUtils.showToast("DetailActivity");
                        intent = new Intent(getActivity(), DanDuDetailActivity.class);
                        intent.putExtra("item", item);
                        startActivity(intent);
                        break;
                    default:
                        UIUtils.showToast("DetailActivity");
                        intent = new Intent(getActivity(), DanDuDetailActivity.class);
                        intent.putExtra("item", item);
                        startActivity(intent);
                }
            }
        });
    }

    @Override
    public BasePresenter createPresenterProxy1() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.item_main_page;
    }

    @Override
    protected void initView(View rootView) {


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadData() {


    }

    @Override
    protected void initListener() {

    }

}

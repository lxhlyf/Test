package com.test.model.response;

import com.google.gson.Gson;
import com.test.model.result.toutiao.NewsData;
import com.test.model.result.toutiao.TipEntity;

import java.util.List;

/**
 * @author 简言
 * @description: TODO
 * @date 2019/2/10
 */

public class NewsResponse {

    public int login_status;
    public int total_number;
    public boolean has_more;
    public String post_content_hint;
    public int show_et_status;
    public int feed_flag;
    public int action_to_last_stick;
    public String message;
    public boolean has_more_to_refresh;
    public TipEntity tips;
    public List<NewsData> data;


        @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

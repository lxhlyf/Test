package com.test.db.domain;

import org.litepal.crud.DataSupport;

/**
 * Created by 简言 on 2019/2/15.
 * 努力吧 ！ 少年 ！
 */
public class ZhiHuDetailRecord extends DataSupport {

    private int page;
    private String json;
    private long time;
    private String zhiHuDetail;

    public ZhiHuDetailRecord(){

    }

    public ZhiHuDetailRecord(String zhiHuDetail, int page, String json, long time) {
        this.page = page;
        this.json = json;
        this.time = time;
        this.zhiHuDetail = zhiHuDetail;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

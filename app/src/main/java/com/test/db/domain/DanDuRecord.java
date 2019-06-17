package com.test.db.domain;

import org.litepal.crud.DataSupport;

/**
 * Created by 简言 on 2019/2/14.
 * 努力吧 ！ 少年 ！
 */
public class DanDuRecord extends DataSupport {

    private int page;
    private String json;
    private long time;
    private String danVideo;

    public DanDuRecord(){

    }

    public DanDuRecord(String danVideo, int page, String json, long time) {
        this.page = page;
        this.json = json;
        this.time = time;
        this.danVideo = danVideo;
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

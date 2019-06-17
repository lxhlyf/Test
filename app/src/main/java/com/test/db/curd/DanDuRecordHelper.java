package com.test.db.curd;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.db.domain.DanDuRecord;
import com.test.model.result.dandu.Item;
import com.test.utils.ListUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 简言 on 2019/2/12.
 * 努力吧 ！ 少年 ！
 *
 * 用于头条数据查找的帮助类
 */
public class DanDuRecordHelper {

    private static Gson mGson = new Gson();

    /**
     * 获取数据库保存的某个频道的最后一条记录
     *
     * @param danVideo
     * @return
     */
    public static DanDuRecord getLastNewsRecord(String danVideo) {
        return DataSupport.where("danVideo=?", danVideo).findLast(DanDuRecord.class);
    }

    /**
     * 获取某个频道上一组新闻记录
     *
     * @param danVideo 频道
     * @param page        页码
     * @return
     */
    public static DanDuRecord getPreNewsRecord(String danVideo, int page) {
        List<DanDuRecord> danDuRecords = selectNewsRecords(danVideo, page - 1);

        if (ListUtils.isEmpty(danDuRecords)) {
            return null;
        }

        return danDuRecords.get(0);
    }


    /**
     * 保存新闻记录
     *
     * @param danVideo
     * @param items
     */
    public static void save(String danVideo, List<Item> items) {
        String json = mGson.toJson(items);
        int page = 1;
        DanDuRecord lastDanDuRecord = getLastNewsRecord(danVideo);
        if (lastDanDuRecord != null) {
            //如果有记录
            page = lastDanDuRecord.getPage() + 1;//页码为最后一条的页码加1
        }
        //保存新的记录
        DanDuRecord danDuRecord = new DanDuRecord(danVideo, page, json, System.currentTimeMillis());
        danDuRecord.saveOrUpdate("danVideo = ? and page = ?", danVideo, String.valueOf(page));
    }


    /**
     * 根据频道码和页码查询新闻记录
     * @param danVideo
     * @param page
     * @return
     */
    private static List<DanDuRecord> selectNewsRecords(String danVideo, int page) {
        return DataSupport
                .where("danVideo = ? and page = ?", danVideo, String.valueOf(page))
                .find(DanDuRecord.class);
    }

    /**
     * 将json转换成新闻集合
     *
     * @param json
     * @return
     */
    public static List<Item> convertToNewsList(String json) {
        return mGson.fromJson(json, new TypeToken<List<Item>>() {
        }.getType());
    }
}

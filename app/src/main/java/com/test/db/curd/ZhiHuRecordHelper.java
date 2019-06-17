package com.test.db.curd;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.db.domain.DanDuRecord;
import com.test.db.domain.ZhiHuRecord;
import com.test.model.result.dandu.Item;
import com.test.model.result.zhihu.ZhiHuNewsResult;
import com.test.utils.ListUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 简言 on 2019/2/12.
 * 努力吧 ！ 少年 ！
 *
 * 用于头条数据查找的帮助类
 */
public class ZhiHuRecordHelper {

    private static Gson mGson = new Gson();

    /**
     * 获取数据库保存的某个频道的最后一条记录
     *
     * @param zhiHu
     * @return
     */
    public static ZhiHuRecord getLastNewsRecord(String zhiHu) {
        return DataSupport.where("zhiHu=?", zhiHu).findLast(ZhiHuRecord.class);
    }

    /**
     * 获取某个频道上一组新闻记录
     *
     * @param zhiHu 频道
     * @param page        页码
     * @return
     */
    public static ZhiHuRecord getPreNewsRecord(String zhiHu, int page) {
        List<ZhiHuRecord> zhiHuRecords = selectNewsRecords(zhiHu, page - 1);

        if (ListUtils.isEmpty(zhiHuRecords)) {
            return null;
        }

        return zhiHuRecords.get(0);
    }


    /**
     * 保存新闻记录
     *
     * @param zhiHu
     * @param records
     */
    public static void save(String zhiHu, List<ZhiHuNewsResult.RecentBean> records) {
        String json = mGson.toJson(records);
        int page = 1;
        ZhiHuRecord lastZhiHuRecord = getLastNewsRecord(zhiHu);
        if (lastZhiHuRecord != null) {
            //如果有记录
            page = lastZhiHuRecord.getPage() + 1;//页码为最后一条的页码加1
        }
        //保存新的记录
        ZhiHuRecord zhiHuRecord = new ZhiHuRecord(zhiHu, page, json, System.currentTimeMillis());
        zhiHuRecord.saveOrUpdate("zhiHu = ? and page = ?", zhiHu, String.valueOf(page));
    }


    /**
     * 根据频道码和页码查询新闻记录
     * @param zhiHu
     * @param page
     * @return
     */
    private static List<ZhiHuRecord> selectNewsRecords(String zhiHu, int page) {
        return DataSupport
                .where("zhiHu = ? and page = ?", zhiHu, String.valueOf(page))
                .find(ZhiHuRecord.class);
    }

    /**
     * 将json转换成新闻集合
     *
     * @param json
     * @return
     */
    public static List<ZhiHuNewsResult.RecentBean> convertToNewsList(String json) {
        return mGson.fromJson(json, new TypeToken<List<ZhiHuNewsResult.RecentBean>>(){}.getType());
    }
}

package com.test.db.curd;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.db.domain.DanDuRecord;
import com.test.db.domain.ZhiHuDetailRecord;
import com.test.model.result.dandu.Item;
import com.test.model.result.zhihu.ZhiHuDetailResult;
import com.test.utils.ListUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 简言 on 2019/2/12.
 * 努力吧 ！ 少年 ！
 *
 * 用于头条数据查找的帮助类
 */
public class ZhiHuDetailRecordHelper {

    private static Gson mGson = new Gson();

    /**
     * 获取数据库保存的某个频道的最后一条记录
     *
     * @param zhiHuDetail
     * @return
     */
    public static ZhiHuDetailRecord getLastNewsRecord(String zhiHuDetail) {
        return DataSupport.where("zhiHuDetail=?", zhiHuDetail).findLast(ZhiHuDetailRecord.class);
    }

    /**
     * 获取某个频道上一组新闻记录
     *
     * @param zhiHuDetail 频道
     * @param page        页码
     * @return
     */
    public static ZhiHuDetailRecord getPreNewsRecord(String zhiHuDetail, int page) {
        List<ZhiHuDetailRecord> zhiHuDetailRecords = selectNewsRecords(zhiHuDetail, page - 1);

        if (zhiHuDetailRecords != null) {
            return null;
        }

        return zhiHuDetailRecords.get(0);
    }


    /**
     * 保存新闻记录
     *
     * @param zhiHuDetail
     * @param items
     */
    public static void save(String zhiHuDetail, List<ZhiHuDetailResult> items) {
        String json = mGson.toJson(items);
        int page = 1;
        ZhiHuDetailRecord lastZhiHuDetailRecord = getLastNewsRecord(zhiHuDetail);
        if (lastZhiHuDetailRecord != null) {
            //如果有记录
            page = lastZhiHuDetailRecord.getPage() + 1;//页码为最后一条的页码加1
        }
        //保存新的记录
        ZhiHuDetailRecord zhiHuDetailRecord = new ZhiHuDetailRecord(zhiHuDetail, page, json, System.currentTimeMillis());
        zhiHuDetailRecord.saveOrUpdate("zhiHuDetail = ? and page = ?", zhiHuDetail, String.valueOf(page));
    }


    /**
     * 根据频道码和页码查询新闻记录
     * @param zhiHu
     * @param page
     * @return
     */
    private static List<ZhiHuDetailRecord> selectNewsRecords(String zhiHu, int page) {
        return DataSupport
                .where("zhiHu = ? and page = ?", zhiHu, String.valueOf(page))
                .find(ZhiHuDetailRecord.class);
    }

    /**
     * 将json转换成新闻集合
     *
     * @param json
     * @return
     */
    public static List<ZhiHuDetailResult> convertToNewsList(String json) {
        return mGson.fromJson(json, new TypeToken<List<ZhiHuDetailResult>>(){}.getType());
    }
}

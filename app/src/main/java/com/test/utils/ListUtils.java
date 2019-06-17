package com.test.utils;

import java.util.List;

/**
 * @author 简言
 * @description: 和List相关的工具方法
 * @date 2019/2/10
 */

public class ListUtils {

    public static boolean isEmpty(List list){
        if (list == null){
            return true;
        }
        return list.size() == 0;
    }
}

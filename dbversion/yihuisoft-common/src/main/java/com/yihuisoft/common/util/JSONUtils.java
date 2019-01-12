package com.yihuisoft.common.util;




import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import java.util.Map;

/**
 * JSON工具类
 * @author tonywu
 * @version v1.0.0
 */
@SuppressWarnings("unchecked")
public final class JSONUtils {

    /**谷歌Gson对象*/
    private static Gson gson;

    /**
     * 构造函数
     */
    private JSONUtils() {
    }

    static {
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
        //gb.setDateFormat(DateFormat.LONG);
        gson = gb.create();
    }

    /**
     * 对象转字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * 从JSon字符串到类
     * @param <T>
     * @param json
     * @param clazz
     * @return
     */
//    public static <T> T fromJson(final com.yihuisoft.bankinterfacebiz.entity.BankData json, Class<com.yihuisoft.bankinterfacebiz.entity.BankMapping> clazz) {
//        return gson.fromJson(json, clazz);
//    }

    /**
     * 从JSon字符串到类
     * @param json
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T fromJson(final String json, Type t) {
        return gson.fromJson(json, t);
    }

    /**
     * 从字符串到Map
     * @param json
     * @return
     */
    public static Map<String, Object> fromJson(final String json) {
        return fromJson(json, Map.class);
    }

}

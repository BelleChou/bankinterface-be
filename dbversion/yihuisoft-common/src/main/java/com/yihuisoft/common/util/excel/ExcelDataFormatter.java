package com.yihuisoft.common.util.excel;

/**
 * Created by Administrator on 2017/6/20.
 */
import java.util.HashMap;
import java.util.Map;

/**
 * Excel数据格式化类
 * @author tonywu
 * @version v1.0.0
 */
public class ExcelDataFormatter {

    /**存在格式map对象*/
    private Map<String,Map<String,String>> formatter=new HashMap<String, Map<String,String>>();

    /**
     * 设值
     * @param key
     * @param map
     */
    public void set(String key,Map<String,String> map){
        formatter.put(key, map);
    }

    /**
     * 放值
     * @param key
     * @return
     */
    public Map<String,String> get(String key){
        return formatter.get(key);
    }

}
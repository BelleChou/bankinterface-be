package com.yihuisoft.commonbiz;

import com.yihuisoft.common.util.DataUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 条件操作类
 * @author tonywu
 * @version v1.0.0
 */
public class Criteria {
    /**字符串缓存*/
    private StringBuffer sb = null;
    /**升序*/
    public static final String SORT_ASC = "ASC";
    /**降序*/
    public static final String SORT_DESC = "DESC";


    /**
     * 构造
     */
    public Criteria(){
        sb = new StringBuffer();
        sb.append(" WHERE 1=1 ");
    }

    /**
     * 不等
     * @param prop
     * @param value
     * @return
     */
    public Criteria notEqual(String prop,Object value){
        if (value instanceof String) {
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" != '").append(value.toString())
                    .append("'");
        }else{
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" != ").append(value.toString());
        }
        return this;
    }

    /**
     * 相等
     * @param prop
     * @param value
     * @return
     */
    public Criteria equal(String prop,Object value){
        if (value instanceof String) {
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" = '").append(value.toString())
                    .append("'");
        }else{
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" = ").append(value.toString());
        }
        return this;
    }
    /**
     * 在之间
     * @param prop
     * @param min
     * @param max
     * @return
     */
    public Criteria between(String prop,Object min,Object max){
        if (min instanceof String) {
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" > '").append(min.toString())
                    .append("' AND ").append(DataUtil.conditionLowToUpper(prop)).append(" < '").append(max.toString()).append("'");
        }else{
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" > ").append(min.toString())
                    .append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" < ").append(max.toString());
        }
        return this;
    }

    /**
     * 之间并相等
     * @param prop
     * @param min
     * @param max
     * @return
     */
    public Criteria betweenOrEqualto(String prop,Object min,Object max){
        if (min instanceof String) {
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" >= '").append(min.toString())
                    .append("' AND ").append(DataUtil.conditionLowToUpper(prop)).append(" <= '").append(max.toString()).append("'");
        }else{
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" >= ").append(min.toString())
                    .append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" <= ").append(max.toString());
        }
        return this;
    }

    /**
     * 小于
     * @param prop
     * @param value
     * @return
     */
    public Criteria lessThan(String prop,Object value){
        if (value instanceof String) {
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" < '").append(value.toString())
                    .append("'");
        }else{
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" < ").append(value.toString());
        }
        return this;
    }

    /**
     * 小于等于
     * @param prop
     * @param value
     * @return
     */
    public Criteria lessThanOrEqualto(String prop,Object value){
        if (value instanceof String) {
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" <= '").append(value.toString())
                    .append("'");
        }else{
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" <= ").append(value.toString());
        }
        return this;
    }

    /**
     * 大于
     * @param prop
     * @param value
     * @return
     */
    public Criteria greaterThan(String prop,Object value){
        if (value instanceof String) {
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" > '").append(value.toString())
                    .append("'");
        }else{
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" > ").append(value.toString());
        }
        return this;
    }

    /**
     * 大于等于
     * @param prop
     * @param value
     * @return
     */
    public Criteria greaterThanOrEqualto(String prop,Object value){
        if (value instanceof String) {
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" >= '").append(value.toString())
                    .append("'");
        }else{
            sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" >= ").append(value.toString());
        }
        return this;
    }

    /**
     * 空
     * @param prop
     * @return
     */
    public Criteria isNull(String prop){
        sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" is null ");
        return this;
    }

    /**
     * 非空
     * @param prop
     * @return
     */
    public Criteria isNotNull(String prop){
        sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" is not null ");
        return this;
    }

    /**
     * like
     * @param prop
     * @param value
     * @return
     */
    public Criteria like(String prop,String value){
        sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" like '").append(value).append("'");
        return this;
    }

    /**
     * not like
     * @param prop
     * @param value
     * @return
     */
    public Criteria notLike(String prop,String value){
        sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" not like '").append(value).append("'");
        return this;
    }

    /**
     * 以开始
     * @param prop
     * @param value
     * @return
     */
    public Criteria startingWith(String prop,String value){
        sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" like '").append(value).append("%'");
        return this;
    }

    /**
     * 以结束
     * @param prop
     * @param value
     * @return
     */
    public Criteria endingWith(String prop,String value){
        sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" like '%").append(value).append("'");
        return this;
    }

    /**
     * 包含
     * @param prop
     * @param value
     * @return
     */
    public Criteria containing(String prop,String value){
        sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" like '%").append(value).append("%'");
        return this;
    }

    /**
     * in
     * @param prop
     * @param valueArr
     * @return
     */
    public Criteria in(String prop,List<Object> valueArr){
        if(valueArr == null)
            return this;

        sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" in (");
        int i = 0;
        for(Object value:valueArr) {
            if (value instanceof String) {
                if(i==0) {
                    sb.append("'").append(value.toString()).append("'");
                }else{
                    sb.append(",'").append(value.toString()).append("'");
                }
            }else{
                if(i==0) {
                    sb.append("").append(value.toString()).append("");
                }else{
                    sb.append(",").append(value.toString()).append("");
                }
            }
            i++;
        }
        sb.append(")");
        return this;
    }

    /**
     * not in
     * @param prop
     * @param valueArr
     * @return
     */
    public Criteria notIn(String prop,List<Object> valueArr){
        if(valueArr == null)
            return this;

        sb.append(" AND ").append(DataUtil.conditionLowToUpper(prop)).append(" not in (");
        int i = 0;
        for(Object value:valueArr) {
            if (value instanceof String) {
                if(i==0) {
                    sb.append("'").append(value.toString()).append("'");
                }else{
                    sb.append(",'").append(value.toString()).append("'");
                }
            }else{
                if(i==0) {
                    sb.append("").append(value.toString()).append("");
                }else{
                    sb.append(",").append(value.toString()).append("");
                }
            }
            i++;
        }
        sb.append(")");
        return this;
    }

    /***
     * 增加自定义条件
     * @param condition
     * @return
     */
    public Criteria addCondition(String condition){
        sb.append(" AND ").append(condition);
        return this;
    }

    /***
     * 增加排序字段
     * @param prop
     * @param sort ASC or DESC
     * @return
     */
    public Criteria addSort(String prop,String sort){
        if(sb.indexOf("ORDER")>0){
            sb.append(",").append(DataUtil.conditionLowToUpper(prop)).append(" ")
                    .append(StringUtils.isEmpty(sort)?SORT_ASC:sort);
        }else{
            sb.append(" ORDER BY ").append(DataUtil.conditionLowToUpper(prop)).append(" ")
                    .append(StringUtils.isEmpty(sort)?SORT_ASC:sort);
        }
        return this;
    }

    /**
     * toString
     * @return
     */
    public String toString(){
        if(sb == null)
            return "";

        return sb.toString();
    }
}

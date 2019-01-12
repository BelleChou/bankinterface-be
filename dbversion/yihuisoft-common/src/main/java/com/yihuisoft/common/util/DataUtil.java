package com.yihuisoft.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据工具类
 * @author tonywu
 * @version v1.0.0
 */
public class DataUtil {

    /**
     * 将Class对象小写变大写 如：memberId => MEMBER_ID
     * @param obj
     * @return
     */
    public static List<String> paramLowToUpper(Class obj) {
        Field[] fields = obj.getDeclaredFields();
        List<String> fieldNames = new ArrayList<String>();
        for (int i = 0; i < fields.length; i++) {
            StringBuffer paramBuf = new StringBuffer();
            String paramName = fields[i].getName();
            String[] splitParamName = paramName.split("(?=[A-Z])");
            for (int j = 0; j < splitParamName.length; j++) {
                if (j == 0) {
                    //param = param + splitParamName[j].toUpperCase();
                    paramBuf.append(splitParamName[j].toUpperCase());
                } else {
                    //param = param + "_" + splitParamName[j].toUpperCase();
                    paramBuf.append("_").append(splitParamName[j].toUpperCase());
                }
            }
            fieldNames.add(paramBuf.toString());
        }
        return fieldNames;
    }

    /**
     * 数名小写变大写
     * @param obj
     * @return
     */
    public static String classNameLowToUpper(Class obj) {
        String classFullNameLower = obj.getName();
        String classNameLower = classFullNameLower.substring(classFullNameLower.lastIndexOf('.') + 1);
        String[] splitClassNameLower = classNameLower.split("(?=[A-Z])");
        StringBuffer tableNameBuf = new StringBuffer();
        for(int j = 0; j < splitClassNameLower.length; j++){
            if(StringUtils.isEmpty(splitClassNameLower[j])){
                continue;
            }else{
                if(StringUtils.isEmpty(tableNameBuf.toString())){
                    //tableName = splitClassNameLower[j].toUpperCase();
                    tableNameBuf.append(splitClassNameLower[j].toUpperCase());
                }else{
                    tableNameBuf.append("_").append(splitClassNameLower[j].toUpperCase());
                    //tableName = tableName + "_" + splitClassNameLower[j].toUpperCase();
                }
            }
        }
//        for (int j = 1; j <= splitClassNameLower.length; j++) {
//            if (j == 1) {
//                tableName = tableName + splitClassNameLower[j-1].toUpperCase();
//            } else {
//                tableName = tableName + "_" + splitClassNameLower[j-1].toUpperCase();
//            }
//        }
        return tableNameBuf.toString();
    }


    /***
     *  条件小写变大写
     * @param param
     * @return
     */
    public static Map<String, Object> conditionLowToUpper(Map<String, Object> param) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String lowKey = entry.getKey();
            StringBuffer upperKeyBuf = new StringBuffer();
            String[] splitParamName = lowKey.split("(?=[A-Z])");
            for (int j = 0; j <= splitParamName.length; j++) {
                if (j == 0) {
                    //upperKey = upperKey + splitParamName[j].toUpperCase();
                    upperKeyBuf.append(splitParamName[j].toUpperCase());
                } else {
                    upperKeyBuf.append("_").append(splitParamName[j].toUpperCase());
                    //upperKey = upperKey + "_" + splitParamName[j].toUpperCase();
                }
            }
            map.put(upperKeyBuf.toString(), entry.getValue());
        }
        return map;
    }

    /***
     * 属性条件小写变大写 如：memberId => MEMBER_ID
     * @param prop
     * @return
     */
    public static String conditionLowToUpper(String prop) {
        StringBuffer upperKeyBuf = new StringBuffer();
        String[] splitParamName = prop.split("(?=[A-Z])");
        for (int j = 1; j <= splitParamName.length; j++) {
            if (j == 1) {
                //upperKey = upperKey + splitParamName[j-1].toUpperCase();
                upperKeyBuf.append(splitParamName[j-1].toUpperCase());
            } else {
                //upperKey = upperKey + "_" + splitParamName[j-1].toUpperCase();
                upperKeyBuf.append("_").append(splitParamName[j-1].toUpperCase());
            }
        }
        return upperKeyBuf.toString();
    }

    /**
     * main函数
     * @param args
     */
    public static void main(String[] args) {
//        List<String> fieldNames = new ArrayList<String>();
//        String[] fields = {"integer", "inteGood", "inteGoodKeed"};
//        for (int i = 0; i < fields.length; i++) {
//            String param = "";
//            String paramName = fields[i];
//            String[] splitParamName = paramName.split("(?=[A-Z])");
//            for (int j = 0; j < splitParamName.length; j++) {
//                if (j == 0) {
//                    param = param + splitParamName[j].toUpperCase();
//                } else {
//                    param = param + "_" + splitParamName[j].toUpperCase();
//                }
//            }
//            fieldNames.add(param);
//        }
//        for (String s : fieldNames) {
//            System.out.println("+++++++++" + s);
//        }

        System.out.println("memberID conditionLowToUpper:" + conditionLowToUpper("memberId"));
        System.out.println("memberID conditionLowToUpper:" + conditionLowToUpper("memberIdCode"));
        System.out.println("member conditionLowToUpper:" + conditionLowToUpper("member"));

        //System.out.println("MemberAddressInDTO classNameLowToUpper:" + classNameLowToUpper(MemberAddressInDTO.class));
        System.out.println("Cipher classNameLowToUpper:" + classNameLowToUpper(Cipher.class));
        //System.out.println("MemberAddressInDTO classNameLowToUpper:" + classNameLowToUpper("member"));


    }
}

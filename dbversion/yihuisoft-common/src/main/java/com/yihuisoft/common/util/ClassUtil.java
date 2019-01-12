package com.yihuisoft.common.util;

/**
 * 类处理工具类
 * @author tonywu
 * @version v1.0.0
 */
public class ClassUtil {

    /***
     * 根据类名得到实例
     * @param className:全类名(如：com.yihuisoft.bankinterfacebiz.job.Test1Job)
     * @return
     */
    public static Object getClass(String className)//className是类名
    {
        Object obj = null; //以String类型的className实例化类
        try {
            obj = Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            //e.printStackTrace();
            System.out.println(e);
        } catch (IllegalAccessException e) {
            //e.printStackTrace();
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println(e);
        }
        return obj;
    }

    /***
     * 根据类名得到bean名(比如：com.yihuisoft.bankinterfacebiz.job.Test1Job返回test1Job)
     * @param cla
     * @return
     */
    public static String getBeanNameByClass(Class cla)//className是类名
    {
        try {
            char[] cs = cla.getSimpleName().toCharArray();
            cs[0] += 32;//首字母大写到小写
            return String.valueOf(cs);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }

    /***
     * 根据类名得到bean名(比如：com.yihuisoft.bankinterfacebiz.job.Test1Job返回test1Job)
     * @param className:类全名
     * @return
     */
    public static String getBeanNameByClass(String className)//className是类名
    {
        try {
            Class cla = Class.forName(className);
            return getBeanNameByClass(cla);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e);
            return null;
        }
    }

    /**
     * main函数
     * @param args
     */
    public static void main(String[] args) {

        try {
            System.out.println(getBeanNameByClass("com.yihuisoft.common.util.CommonConst"));

        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e);
        }

    }
}

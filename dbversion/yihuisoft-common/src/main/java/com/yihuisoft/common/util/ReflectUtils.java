package com.yihuisoft.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反映工具类
 * @author tonywu
 * @version v1.0.0
 */
public class ReflectUtils {

    /**
     * 获取成员变量的修饰符
     *
     * @param clazz
     * @param field
     * @return
     * @throws Exception
     */
    public static <T> int getFieldModifier(Class<T> clazz, String field) throws Exception {
        // getDeclaredFields可以获取所有修饰符的成员变量，包括private,protected等getFields则不可以
        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(field)) {
                return fields[i].getModifiers();
            }
        }
        throw new Exception(clazz + " has no field \"" + field + "\"");
    }

    /**
     * [对象]根据成员变量名称获取其值
     *
     * @param clazzInstance
     * @param field
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> Object getFieldValue(Object clazzInstance, Object field) throws NoSuchFieldException, IllegalAccessException {

        Field[] fields = clazzInstance.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(field)) {
                // 对于私有变量的访问权限，在这里设置，这样即可访问Private修饰的变量
                fields[i].setAccessible(true);
                return fields[i].get(clazzInstance);
            }
        }

        return null;
    }

    /**
     * [类]根据成员变量名称获取其值（默认值）
     *
     * @param clazz
     * @param field
     * @return
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> Object getFieldValue(Class<T> clazz, String field) throws NoSuchFieldException, IllegalAccessException, InstantiationException {

        Field[] fields = clazz.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getName().equals(field)) {
                // 对于私有变量的访问权限，在这里设置，这样即可访问Private修饰的变量
                fields[i].setAccessible(true);
                return fields[i].get(clazz.newInstance());
            }
        }

        return null;
    }

    /**
     * 获取所有的成员变量(通过GET，SET方法获取)
     *
     * @param clazz
     * @return
     */
    public static <T> String[] getFields(Class<T> clazz) {

        Field[] fields = clazz.getDeclaredFields();

        String[] fieldsArray = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            fieldsArray[i] = fields[i].getName();
        }

        return fieldsArray;
    }

    /**
     * 获取所有的成员变量,包括父类
     *
     * @param clazz
     * @param superClass 是否包括父类
     * @return
     * @throws Exception
     */
    public static <T> Field[] getFields(Class<T> clazz, boolean superClass) throws Exception {

        Field[] fields = clazz.getDeclaredFields();
        Field[] superFields = null;
        if (superClass) {
            Class superClazz = clazz.getSuperclass();
            if (superClazz != null) {
                superFields = superClazz.getDeclaredFields();
            }
        }

        Field[] allFields = null;

        if (superFields == null || superFields.length == 0) {
            allFields = fields;
        } else {
            allFields = new Field[fields.length + superFields.length];
//            for (int i = 0; i < fields.length; i++) {
//                allFields[i] = fields[i];
//            }
            System.arraycopy(fields,0,allFields,0,fields.length);
//            for (int i = 0; i < superFields.length; i++) {
//                allFields[fields.length + i] = superFields[i];
//            }
            System.arraycopy(superFields,0,allFields,superFields.length,superFields.length);
        }

        return allFields;
    }

    /**
     * 获取所有的成员变量,包括父类
     *
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> Field[] getClassFieldsAndSuperClassFields(Class<T> clazz) throws Exception {

        Field[] fields = clazz.getDeclaredFields();

        if (clazz.getSuperclass() == null) {
            throw new Exception(clazz.getName() + "没有父类");
        }

        Field[] superFields = clazz.getSuperclass().getDeclaredFields();

        Field[] allFields = new Field[fields.length + superFields.length];

//        for (int i = 0; i < fields.length; i++) {
//            allFields[i] = fields[i];
//        }
//        for (int i = 0; i < superFields.length; i++) {
//            allFields[fields.length + i] = superFields[i];
//        }
        System.arraycopy(fields,0,allFields,0,fields.length);
        System.arraycopy(superFields,0,allFields,superFields.length,superFields.length);
        return allFields;
    }

    /**
     * 指定类，调用指定的无参方法
     *
     * @param clazz
     * @param method
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <T> Object invoke(Class<T> clazz, String method) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object instance = clazz.newInstance();
        Method m = clazz.getMethod(method, new Class[]{});
        return m.invoke(instance, new Object[]{});
    }

    /**
     * 通过对象，访问其方法
     *
     * @param clazzInstance
     * @param method
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <T> Object invoke(Object clazzInstance, String method) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Method m = clazzInstance.getClass().getMethod(method, new Class[]{});
        return m.invoke(clazzInstance, new Object[]{});
    }

    /**
     * 指定类，调用指定的方法
     *
     * @param clazz
     * @param method
     * @param paramClasses
     * @param params
     * @return Object
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static <T> Object invoke(Class<T> clazz, String method, Class<T>[] paramClasses, Object[] params) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object instance = clazz.newInstance();
        Method m = clazz.getMethod(method, paramClasses);
        return m.invoke(instance, params);
    }

    /**
     * 通过类的实例，调用指定的方法
     *
     * @param clazzInstance
     * @param method
     * @param paramClasses
     * @param params
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static <T> Object invoke(Object clazzInstance, String method, Class<T>[] paramClasses, Object[] params) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method m = clazzInstance.getClass().getMethod(method, paramClasses);
        return m.invoke(clazzInstance, params);
    }

//    @SuppressWarnings("unchecked")
//    public static void main(String[] args) throws Exception {
//        // getFields(User.class);
//        User u = new User();
//        invoke(u, "setName", new Class[] { String.class }, new Object[] { "xx发大水法大水法x" });
//        System.out.println(getFieldValue(u, "name"));
//

}

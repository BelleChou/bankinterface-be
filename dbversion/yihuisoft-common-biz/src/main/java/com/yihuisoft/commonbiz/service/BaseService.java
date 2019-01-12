package com.yihuisoft.commonbiz.service;

import com.yihuisoft.common.exception.ApplicationException;
import com.yihuisoft.common.util.DataUtil;
import com.yihuisoft.commonbiz.Criteria;
import com.yihuisoft.commonbiz.mapper.master.BaseDaoMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基本服务类
 * @author tonywu
 * @version v1.0.0
 */
@Component
public class BaseService implements BaseDaoMapper {

    /**sqlsession模板*/
    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    /**表头名*/
    @Value("${table.head.name}")
    private String tableHeadName;
    /**数据库 类型*/
    @Value("${database.type}")
    private String databaseType;

    /**
     * 获取对象列表
     * @param obj
     * @param criteria
     * @return
     * @throws Exception
     */
    public List<Object> selectEntityList(Class obj, Criteria criteria) throws Exception {

        // 将实体字段名变为与数据库相同字段(小写转为大写)
        List paramNameUpperList = DataUtil.paramLowToUpper(obj);

        // 得到类文件数组
        Field[] paramNameLowerArray = obj.getDeclaredFields();

        // 通过class文件得到表的名字
        String tableName = tableHeadName + DataUtil.classNameLowToUpper(obj);

        // select后面的拼接串
        StringBuffer tabFieldBuf = new StringBuffer();

        // where后面的拼接串
        String whereField = criteria==null?"":criteria.toString();

        // 得到select后面的拼接串
        for (int i = 0; i < paramNameUpperList.size(); i++) {
            // 得到属性大写名
            String upperParam = paramNameUpperList.get(i).toString();
            // 得到属性小写名
            String lowerParam = paramNameLowerArray[i].getName();
            // 判断是否为第一次拼串
            if (i == 0) {
                //tabField += "CAST(IFNULL(" + UpperParam + ", '') AS CHAR) " + LowerParam;
                //tabField += UpperParam + " \"" + LowerParam + "\"";
                tabFieldBuf.append(upperParam).append(" \"").append(lowerParam).append("\"");
            } else {
                //tabField += "," + UpperParam + " \"" + LowerParam + "\"";
                tabFieldBuf.append(",").append(upperParam).append(" \"").append(lowerParam).append("\"");
            }
        }

        // 得到sql串
        String sql = "SELECT " + tabFieldBuf.toString() + " FROM " + tableName + whereField;
        /* System.out.println("拼接的selectbyparam的sql为："+sql); */
        List<Map<String, Object>> modelList = sqlSessionTemplate.selectList("selectEntityList", sql);
        if(modelList == null)
            return null;

        List<Object> beanList = new ArrayList();

        // 通过beanutils将list封装成实体集合
        for (Map<String, Object> map : modelList) {
            Object bean = null;
            try {
                bean = obj.newInstance();
                BeanUtils.populate(bean, map);
            } catch (IllegalAccessException e) {
                //throw e;
                System.out.println(e);
            }

            beanList.add(bean);
        }
        return beanList;
    }

    /**
     * 获取对象列表(分页)
     * @param obj
     * @param criteria
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<Object> selectEntityList(Class obj, Criteria criteria,Integer pageNo,Integer pageSize) throws Exception {

        if(pageNo==null || pageNo < 1) {
            throw new ApplicationException("ERRORCODE0010",new String[]{"pageNo"});
        }
        if(pageSize==null || pageSize < 1) {
            throw new ApplicationException("ERRORCODE0010",new String[]{"pageSize"});
        }

        // 将实体字段名变为与数据库相同字段(小写转为大写)
        List paramNameUpperList = DataUtil.paramLowToUpper(obj);

        // 得到类文件数组
        Field[] paramNameLowerArray = obj.getDeclaredFields();

        // 通过class文件得到表的名字
        String tableName = tableHeadName + DataUtil.classNameLowToUpper(obj);

        // select后面的拼接串
        //String tabField = "";
        StringBuffer tabFieldBuf = new StringBuffer();

        // where后面的拼接串
        String whereField = criteria==null?"":criteria.toString();

        // 得到select后面的拼接串
        for (int i = 0; i < paramNameUpperList.size(); i++) {
            // 得到属性大写名
            String upperParam = paramNameUpperList.get(i).toString();
            // 得到属性小写名
            String lowerParam = paramNameLowerArray[i].getName();
            // 判断是否为第一次拼串
            if (i == 0) {
                //tabField += "CAST(IFNULL(" + UpperParam + ", '') AS CHAR) " + LowerParam;
                if("oracle".equals(databaseType)) {
                    tabFieldBuf.append(tableName).append(".")
                            .append(upperParam).append(" \"").append(lowerParam).append("\"");
                }else{
                    tabFieldBuf.append(upperParam).append(" \"").append(lowerParam).append("\"");
                }
            } else {
                if("oracle".equals(databaseType)) {
                    tabFieldBuf.append(",").append(tableName).append(".")
                            .append(upperParam).append(" \"").append(lowerParam).append("\"");
                }else{
                    tabFieldBuf.append(",").append(upperParam).append(" \"").append(lowerParam).append("\"");
                }
            }
        }

        // 得到sql串
        StringBuffer sql = new StringBuffer();
        if("mysql".equals(databaseType)){
            sql.append("SELECT ").append(tabFieldBuf.toString()).append(" FROM ").append(tableName).
                    append(whereField).append(" limit ").append((pageNo -1)*pageSize).append(",").append(pageSize);
        }else if("oracle".equals(databaseType)){
            sql.append("SELECT ").append(tabFieldBuf.toString()).append(" FROM ").
                    append("(SELECT ROWNUM,").append(tableName).append(".* FROM ").append(tableName)
                    .append(whereField).append(" AND ROWNUM <=").append(pageNo*pageSize).append(") ").append(tableName)
                    .append(" WHERE ROWNUM >=").append((pageNo -1)*pageSize + 1);
        }
        //String sql = "SELECT " + tabField + " FROM " + tableName + whereField;
        /* System.out.println("拼接的selectbyparam的sql为："+sql); */
        List<Map<String, Object>> modelList = sqlSessionTemplate.selectList("selectEntityList", sql.toString());
        if(modelList == null)
            return null;

        List<Object> beanList = new ArrayList();

        // 通过beanutils将list封装成实体集合
        for (Map<String, Object> map : modelList) {
            Object bean = null;
            try {
                bean = obj.newInstance();
                BeanUtils.populate(bean, map);
            } catch (IllegalAccessException e) {
                //throw new RuntimeException(e);
                System.out.println(e);
            }

            beanList.add(bean);
        }

        return beanList;
    }

    /**
     * 获取一行对象
     * @param obj
     * @param criteria
     * @return
     * @throws Exception
     */
    public Object selectEntityOne(Class obj, Criteria criteria) throws Exception {

        // 将实体字段名变为与数据库相同字段(小写转为大写)
        List paramNameUpperList = DataUtil.paramLowToUpper(obj);

        // 得到类文件数组
        Field[] paramNameLowerArray = obj.getDeclaredFields();

        // 通过class文件得到表的名字
        String tableName = tableHeadName + DataUtil.classNameLowToUpper(obj);

        // select后面的拼接串
        StringBuffer tabFieldBuf = new StringBuffer();

        // where后面的拼接串
        String whereField = criteria==null?"":criteria.toString();

        // 得到select后面的拼接串
        for (int i = 0; i < paramNameUpperList.size(); i++) {
            // 得到属性大写名
            String upperParam = paramNameUpperList.get(i).toString();
            // 得到属性小写名
            String lowerParam = paramNameLowerArray[i].getName();
            // 判断是否为第一次拼串
            if (i == 0) {
                //tabField += "CAST(IFNULL(" + UpperParam + ", '') AS CHAR) " + LowerParam;
                //tabField += UpperParam + " \"" + LowerParam + "\"";
                tabFieldBuf.append(upperParam).append(" \"").append(lowerParam).append("\"");
            } else {
                //tabField += "," + UpperParam + " \"" + LowerParam + "\"";
                tabFieldBuf.append(",").append(upperParam).append(" \"").append(lowerParam).append("\"");
            }
        }

        // 得到sql串
        String sql = "SELECT " + tabFieldBuf.toString() + " FROM " + tableName + whereField;
        /* System.out.println("拼接的selectbyparam的sql为："+sql); */
        Map<String, Object> result = sqlSessionTemplate.selectOne("selectEntityOne", sql);
        if(result == null)
            return null;

        Object bean = null;
        try {
            bean = obj.newInstance();
            BeanUtils.populate(bean, result);
        } catch (IllegalAccessException e) {
            //throw new RuntimeException(e);
            System.out.println(e);
        }
        return bean;
    }

    /**
     * 获取总记录数
     * @param obj
     * @param criteria
     * @return
     * @throws Exception
     */
    public Integer count(Class obj, Criteria criteria) throws Exception {

        // 通过class文件得到表的名字
        String tableName = tableHeadName + DataUtil.classNameLowToUpper(obj);

        //where后面sql串
        String whereField = criteria==null?"":criteria.toString();

        String sql = "SELECT COUNT(0) FROM " + tableName + whereField;
        /*System.out.println("DELETE sql："+sql);*/
        return sqlSessionTemplate.selectOne("count", sql);
    }


    /**
     * 获取一行对象(根据id值)
     * @param obj
     * @param id
     * @return
     * @throws Exception
     */
    public Object selectEntityById(Class obj, Long id) throws Exception {

        Criteria criteria = new Criteria();
        criteria.equal("id",id);
        return selectEntityOne(obj,criteria);
    }

    /**
     * 插入
     * @param obj
     * @return
     * @throws Exception
     */
    public boolean insertEntity(Object obj)  throws Exception {
        //将属性名小写转化为与数据库字段相同的大写
        List paramNameUpperList = DataUtil.paramLowToUpper(obj.getClass());

        //得到表名
        String tableName = tableHeadName + DataUtil.classNameLowToUpper(obj.getClass());

        //into 后面的sql串
        StringBuffer paramFieldBuf = new StringBuffer();
        paramFieldBuf.append("(");

        //value后面的sql串
        StringBuffer valueFieldBuf = new StringBuffer();
        valueFieldBuf.append(" values (");

        //得到into后面的sql串
        for (int i = 0; i < paramNameUpperList.size(); i++) {
            if (i == 0) {
                //为第一次
                //paramField = paramField + paramNameUpperList.get(i);
                paramFieldBuf.append(paramNameUpperList.get(i));
            } else {
                //paramField = paramField + "," + paramNameUpperList.get(i);
                paramFieldBuf.append(",").append(paramNameUpperList.get(i));
            }
        }
        paramFieldBuf.append(")");
        //paramField = paramField + ")";

        //得到类文件数组
        Field[] fields = obj.getClass().getDeclaredFields();

        //拼接values后面sql串
        for (int i = 0; i < fields.length; i++) {
            Object value;//属性值
            //得到属性名
            String paramName = fields[i].getName();

            //通过属性名得到getXxx方法名
            String paramNameGet = "get" + paramName.substring(0, 1).toUpperCase() + paramName.substring(1);

            //根据java中的反射得到属性值
            try {
                Method m = obj.getClass().getMethod(paramNameGet);
                value = m.invoke(obj);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                System.out.println(e);
                return false;
            }
            //得到的值是否为空
            if (value != null) {
                if (value instanceof String) {
                    //为String类型
                    if (i == 0) {
                        //第一次
                        //valueField = valueField + "'" + value + "'";
                        valueFieldBuf.append("'").append(value).append("'");
                    } else {
                        valueFieldBuf.append(",'").append(value).append("'");
                    }
                } else {
                    //不是String类型
                    if (i == 0) {
                        //valueField = valueField + "'" + value + "'";
                        valueFieldBuf.append(value);
                    } else {
                        //valueField = valueField + "," + value;
                        valueFieldBuf.append(",").append(value);
                    }
                }
            } else {
                //属性值为空
                if (i == 0) {
                    //第一次
                    //valueField = valueField + null;
                    valueFieldBuf.append("null");
                } else {
                    //valueField = valueField + "," + null;
                    valueFieldBuf.append(",").append("null");
                }
            }
        }

        //valueField = valueField + ")";
        valueFieldBuf.append(")");
        //拼接sql串
        String sql = "INSERT INTO " + tableName + paramFieldBuf.toString() + valueFieldBuf.toString();
        /*System.out.println("插入model的sql语句为："+ sql);*/
        int count = sqlSessionTemplate.insert("insertEntity", sql);
        return count == 1 ? true : false;
    }

    /**
     * 选择性更新
     * @param obj
     * @param criteria
     * @return
     * @throws Exception
     */
    public boolean updateSelective(Object obj, Criteria criteria) throws Exception {

        //将属性名转化为大写
        List paramNameUpperList = DataUtil.paramLowToUpper(obj.getClass());
        //得到表名
        String tableName = tableHeadName + DataUtil.classNameLowToUpper(obj.getClass());

        //set后面sql串
        StringBuffer setFieldBuf = new StringBuffer();
        setFieldBuf.append(" SET ");

        //where后面sql串
        String whereField = criteria==null?"":criteria.toString();

        //得到类文件数组
        Field[] fields = obj.getClass().getDeclaredFields();
        boolean isSetAdded = false;//是否增加过set

        //拼接set后面sql串
        for (int i = 0; i < fields.length; i++) {
            Object value;
            String paramName = fields[i].getName();
            String paramNameGet = "get" + paramName.substring(0, 1).toUpperCase() + paramName.substring(1);

            //java反射得到属性值
            try {
                Method m = obj.getClass().getMethod(paramNameGet);
                value = m.invoke(obj);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                System.out.println(e);
                return false;
            }
            //对值为空的字段不处理
            if(value == null)
                continue;

            //如果没有set
            if (!isSetAdded) {
                //第一次
                if (value instanceof String) {
                    //值为String类型
                    setFieldBuf.append(paramNameUpperList.get(i)).append(" = ").
                            append("'").append(value).append("'");
                    isSetAdded = true;
                } else {
                    setFieldBuf.append(paramNameUpperList.get(i)).append(" = ").append(value);
                    isSetAdded = true;
                }
            } else {
                if (value instanceof String) {
                    //值为String类型
                    setFieldBuf.append(", ").append(paramNameUpperList.get(i)).append(" = ").
                            append("'").append(value).append("'");
                } else {
                    setFieldBuf.append(", ").append(paramNameUpperList.get(i)).append(" = ").append(value);
                }
            }
        }

        String sql = "UPDATE " + tableName + setFieldBuf.toString() + whereField;
        /*System.out.println("UPDATE sql："+sql);*/
        int count = sqlSessionTemplate.update("updateEntity", sql);
        return count >= 1 ? true : false;
    }

    /**
     * 选择性更新根据id值
     * @param obj
     * @param id
     * @return
     * @throws Exception
     */
    public boolean updateSelectiveById(Object obj, Long id) throws Exception {
        Criteria criteria = new Criteria();
        criteria.equal("id",id);
        return updateSelective(obj,criteria);
    }

    /**
     * 删除实体
     * @param obj
     * @param criteria
     * @return
     * @throws Exception
     */
    public boolean deleteEntity(Class obj, Criteria criteria) throws Exception {

        // 通过class文件得到表的名字
        String tableName = tableHeadName + DataUtil.classNameLowToUpper(obj);

        //where后面sql串
        String whereField = criteria==null?"":criteria.toString();

        String sql = "DELETE FROM " + tableName + whereField;
        /*System.out.println("DELETE sql："+sql);*/
        int count = sqlSessionTemplate.delete("deleteEntity", sql);
        return count >= 1 ? true : false;
    }

    /**
     * 删除根据id值
     * @param obj
     * @param id
     * @return
     * @throws Exception
     */
    public boolean deleteEntityById(Class obj, Long id) throws Exception {
        Criteria criteria = new Criteria();
        criteria.equal("id",id);
        return deleteEntity(obj,criteria);
    }

    /**
     * 更新实体
     * @param obj
     * @param criteria
     * @return
     * @throws Exception
     */
    public boolean updateEntity(Object obj, Criteria criteria) throws Exception  {
        //将属性名转化为大写
        List paramNameUpperList = DataUtil.paramLowToUpper(obj.getClass());
        //得到表名
        String tableName = tableHeadName + DataUtil.classNameLowToUpper(obj.getClass());

        //set后面sql串
        String setField = " SET ";
        //where后面sql串
        String whereField = criteria==null?"":criteria.toString();

        //得到类文件数组
        Field[] fields = obj.getClass().getDeclaredFields();
        //拼接set后面sql串
        for (int i = 1; i < fields.length; i++) {
            Object value;
            String paramName = fields[i].getName();
            String paramNameGet = "get" + paramName.substring(0, 1).toUpperCase() + paramName.substring(1);

            //java反射得到属性值
            try {
                Method m = obj.getClass().getMethod(paramNameGet);
                value = m.invoke(obj);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                System.out.println(e);
                return false;
            }

            if (i == 1) {
                //第一次
                if (value instanceof String) {
                    //值为String类型
                    setField = setField + paramNameUpperList.get(i) + " = " + "'" + value + "'";
                } else {
                    setField = setField + paramNameUpperList.get(i) + " = " + value;
                }
            } else {
                if (value instanceof String) {
                    //值为String类型
                    setField = setField + ", " + paramNameUpperList.get(i) + " = " + "'" + value + "'";
                } else {
                    setField = setField + ", " + paramNameUpperList.get(i) + " = " + value;
                }
            }
        }

        String sql = "UPDATE " + tableName + setField + whereField;
        /*System.out.println("跟新的sql串为："+sql);*/
        int count = sqlSessionTemplate.update("updateEntity", sql);
        return count >= 1 ? true : false;
    }

}
package com.yihuisoft.commonbiz.mapper.master;

import com.yihuisoft.commonbiz.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 基本Dao Mapper类
 * @author tonywu
 * @version v1.0.0
 */
@Mapper
public interface BaseDaoMapper {

	/**
	 * 获取对象列表
	 * @param obj
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	List selectEntityList(Class obj, Criteria criteria) throws Exception;

	/**
	 * 获取对象列表(分页)
	 * @param obj
	 * @param criteria
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	List selectEntityList(Class obj, Criteria criteria, Integer pageNo, Integer pageSize) throws Exception;

	/**
	 * 获取一行
	 * @param obj
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	Object selectEntityOne(Class obj, Criteria criteria) throws Exception;

	/**
	 * 获取记录数
	 * @param obj
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	Integer count(Class obj, Criteria criteria) throws Exception;

	/**
	 * 根据主键ID值获取一行对象
	 * @param obj
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Object selectEntityById(Class obj, Long id) throws Exception;

	/**
	 * 插入对象
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	boolean insertEntity(Object obj) throws Exception;

	/**
	 * 根据对象值可选更新表
	 * @param obj
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	boolean updateSelective(Object obj, Criteria criteria) throws Exception;

	/**
	 * 根据主键ID值更新记录
	 * @param obj
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean updateSelectiveById(Object obj, Long id) throws Exception;

	/**
	 * 删除
	 * @param obj
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	boolean deleteEntity(Class obj, Criteria criteria) throws Exception;

	/**
	 * 根据id删除
	 * @param obj
	 * @param id
	 * @return
	 * @throws Exception
	 */
	boolean deleteEntityById(Class obj, Long id) throws Exception;

	/**
	 * 更新表(对象没有赋值时统一更新为NULL，此函数慎用)
	 * @param obj
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	boolean updateEntity(Object obj, Criteria criteria) throws Exception;


	//以下函数不建议使用

//	//获取对象列表
//	List selectEntityList(Class obj, Map<String, Object> param) throws Exception;
//    //获取对象列表(分页)
//  List selectEntityList(Class obj, Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception;
//    //获取一行
//	Object selectEntityOne(Class obj, Map<String, Object> param) throws Exception;
//    //获取记录数
//	Integer count(Class obj, Map<String, Object> param) throws Exception;
//
//	//根据对象值可选更新表
//	boolean updateSelective(Object obj, Map<String, Object> param) throws Exception;
//
//	boolean deleteEntity(Class obj, Map<String, Object> param) throws Exception;
//
//	//更新表(对象没有赋值时统一更新为NULL，此函数慎用)
//	boolean updateEntity(Object obj, Map<String, Object> param) throws Exception;

} 
package com.yihuisoft.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 输出报文基类
 * @author tonywu
 * @version v1.0.0
 */
public class OutDTO implements Serializable{
	/**序列id*/
	private static final long serialVersionUID = 1L;
	/**错误码*/
	private String errorCode;
	/**错误消息*/
	private String errorMsg;

	/**记录总数 分页用*/
	private Integer sumCount;

	/**一般为Map对象*/
	private Map<String, Object> data;

	/**token*/
	//private String token;

	/**
	 * 构造函数
	 */
	public OutDTO()
	{
		data =  new HashMap<String, Object>();
	}

	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 设置错误码
	 * @param errorCode
	 * @param errorMsg
	 */
	public void setErrorCode(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg= errorMsg;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	//分页用
	public Integer getSumCount() {
		return sumCount;
	}
	public void setSumCount(Integer sumCount) {	this.sumCount = sumCount;}

	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	/**
	 * 放值
	 * @param k
	 * @param v
	 */
	public void put(String k,Object v){
		this.data.put(k,v);
	}

	/**
	 * 取值
	 * @param k
	 * @return
	 */
	public Object get(String k){
		return this.data.get(k);
	}

//	public String getToken() {	return token;	}
//
//	public void setToken(String token) {
//		this.token = token;
//	}
}

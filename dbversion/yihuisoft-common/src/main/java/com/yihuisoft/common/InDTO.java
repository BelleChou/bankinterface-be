package com.yihuisoft.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 输入报文基类
 * @author tonywu
 * @version v1.0.0
 */
public class InDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -665726420933309515L;

	/**模块名*/
	private String moduleName;
	/**功能名*/
	private String functionName;
	/**方法名*/
	private String methodName;

	/**登录id号*/
	private String loginId;
	/**调用者id号*/
	private String callerId;
	/**IP地址*/
	private String ip;
	/**版本号*/
	private String version;

	//分页用
	/**页号*/
	private Integer pageNo;
	/**页尺寸*/
	private Integer pageSize;

	/**一般为Map对象*/
	private Map<String, Object> data;

	/**安全验证字符串：计划pc时HASH签名，移动端时：为token*/
	private String securityStr;

	/**token*/
	//private String token;

	/**
	 * 构造函数
	 */
	public InDTO()
	{
		data =  new HashMap<String, Object>();
	}

	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getLoginId() {return loginId;}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getCallerId() {return callerId;}
	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getSecurityStr() {return securityStr;}
	public void setSecurityStr(String securityStr) {
		this.securityStr = securityStr;
	}

//	public String getSign() {
//		return sign;
//	}
//	public void setSign(String sign) {
//		this.sign = sign;
//	}
//
//	public String getToken() {
//		return token;
//	}
//	public void setToken(String token) {
//		this.token = token;
//	}
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

}

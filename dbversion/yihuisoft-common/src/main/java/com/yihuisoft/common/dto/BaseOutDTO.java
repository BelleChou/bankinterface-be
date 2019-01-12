package com.yihuisoft.common.dto;

import java.io.Serializable;

/**
 * 输出报文基类
 * @author tonywu
 * @version v1.0.0
 */
public class BaseOutDTO  implements Serializable{
	/**序列id*/
	private static final long serialVersionUID = 1L;
	/**错误码*/
	private String errorCode;
	/**错误Msg*/
	private String errorMsg;

	/**记录总数 分页用*/
	private Integer sumCount;

	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * 设置错误码与消息
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

}

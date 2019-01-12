package com.yihuisoft.common;

/**
 * 移动端返回响应类
 * @author tonywu
 * @version v1.0.0
 */
public class AppResponse {

	/**状态码*/
	private String code;
	/**msg*/
	private String message;
	/**一般为Map对象*/
	private Object data;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}


	/**
	 * 获取成功时对象
	 * @param data
	 * @return
	 */
	public static AppResponse getSuccess(Object data) {
		AppResponse r = new AppResponse();
		//r.code = Rescode.SUCCESS;
		//r.message = Rescode.SUCCESS_MESSAGE;
		r.data = data;
		return r;
	}

//	public static AppResponse getSuccess(Object data , String message) {
//		AppResponse r = new AppResponse();
//		r.code = Rescode.SUCCESS;
//		r.message = message;
//		r.data = data;
//		return r;
//	}

	/**
	 * 获取失败时对象
	 * @param errorCode
	 * @param errorMessage
	 * @return
	 */
	public static AppResponse getError(String errorCode, String errorMessage) {
		AppResponse r = new AppResponse();
		r.code = errorCode;
		r.message = errorMessage;
		return r;
	}
	
}

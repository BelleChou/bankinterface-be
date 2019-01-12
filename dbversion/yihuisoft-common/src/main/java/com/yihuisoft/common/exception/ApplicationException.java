package com.yihuisoft.common.exception;

/**
 * 应用程序异常类
 * @author tonywu
 * @version v1.0.0
 */
public class ApplicationException extends Exception {

    /**序列id号*/
    private static final long serialVersionUID = 1L;
    /**错误码*/
    private String errCode;
    /**错误msg*/
    private String errMsg;
    /**msg参数列表*/
    private String[] errMsgParam;

    /**
     * 构造函数
     * @param errCode
     */
    public ApplicationException(String errCode) {
        this.errCode = errCode;
    }

    /**
     * 构造函数
     * @param errCode
     * @param errMsgParam
     */
    public ApplicationException(String errCode, String[] errMsgParam) {
        this.errCode = errCode;
        this.errMsgParam = (String[])errMsgParam.clone();
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String[] getErrMsgParam() {
        return this.errMsgParam;
    }

    public void setErrMsgParam(String[] errMsgParam) {
        this.errMsgParam = (String[])errMsgParam.clone();
    }

}

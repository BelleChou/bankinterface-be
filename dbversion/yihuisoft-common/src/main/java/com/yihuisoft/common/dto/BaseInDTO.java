package com.yihuisoft.common.dto;

import java.io.Serializable;

/**
 * 输入报文基类
 * @author tonywu
 * @version v1.0.0
 */
public class BaseInDTO implements Serializable {
	/**序列id号*/
	private static final long serialVersionUID = -665726420933309515L;
	/**登录id号*/
	private Long loginId;
	/**IP地址*/
	private String ip;
	/**版本号*/
	private String version;

	//分页用
	/**页号*/
	private Integer pageNo;
	/**页尺寸*/
	private Integer pageSize;

	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
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
}

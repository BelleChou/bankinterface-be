package com.yihuisoft.yihuisoftservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;

import java.io.Serializable;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Controller基类
 * @author tonywu
 * @version v1.0.0
 */
@ComponentScan({"com.yihuisoft.*biz*"})
public class BaseController implements Serializable{

	/**日志*/
	protected static final Logger logger=LoggerFactory.getLogger(BaseController.class);
	/**对象*/
	@Autowired
	private MessageSource messageSource;

	/**
	 * 取得error msg
	 * @param errCode
	 * @param errMsgParam
	 * @return
	 */
	public String getErrorMessage(String errCode,String[] errMsgParam){
		Locale locale = LocaleContextHolder.getLocale();

		return messageSource.getMessage(errCode, errMsgParam, locale);
	}
}

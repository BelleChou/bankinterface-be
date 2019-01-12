package com.yihuisoft.yihuisoftservice;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
/**
 * Servlet初始化类
 * @author tonywu
 * @version v1.0.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * 配置
	 * @param application
	 * @return
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(YihuisoftserviceApplication.class);
	}

}

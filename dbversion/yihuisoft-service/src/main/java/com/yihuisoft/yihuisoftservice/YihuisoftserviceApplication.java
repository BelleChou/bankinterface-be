package com.yihuisoft.yihuisoftservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
/**
 * 服务应用类
 * @author tonywu
 * @version v1.0.0
 */
@SpringBootApplication
@ServletComponentScan
@EnableCaching
public class YihuisoftserviceApplication {

	/**
	 * 主main函数
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(YihuisoftserviceApplication.class, args);
	}

}

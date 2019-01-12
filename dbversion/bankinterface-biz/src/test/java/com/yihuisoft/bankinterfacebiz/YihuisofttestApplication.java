package com.yihuisoft.bankinterfacebiz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * 服务应用类
 *
 * @author tonywu
 * @version v1.0.0
 */
@SpringBootApplication
@ServletComponentScan
@EnableCaching
@ComponentScan({"com.yihuisoft.*biz*"})
public class YihuisofttestApplication {

    /**
     * 主main函数
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(YihuisofttestApplication.class, args);
    }
}

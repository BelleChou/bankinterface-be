package com.yihuisoft.yihuisoftservice;

import com.yihuisoft.common.util.ClassUtil;
//import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * 应用工具类
 * @author tonywu
 * @version v1.0.0
 */
@Component
public class ApplicationUtil implements ApplicationContextAware {
    /**应用上下文*/
    private static ApplicationContext   applicationContext;

    /**
     * 设置上下文
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext){
        //ApplicationUtil.applicationContext = applicationContext;
        setAppContext(applicationContext);
    }

    /**
     * 设置上下文
     * @param applicationContext
     */
    public static void setAppContext(ApplicationContext applicationContext){
        ApplicationUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 获取对象
     * @return  Object 一个以所给名字注册的bean的实例 (service注解方式，自动生成以首字母小写的类名为bean name)
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 获取对象
     * @return  Object 一个以所给名字注册的bean的实例 (className:全类名)
     */
    public static Object getBeanByClass(String className) {
        return applicationContext.getBean(ClassUtil.getBeanNameByClass(className));
    }
}

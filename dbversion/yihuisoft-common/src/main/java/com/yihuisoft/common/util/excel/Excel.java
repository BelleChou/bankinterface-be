package com.yihuisoft.common.util.excel;

/**
 * Created by Administrator on 2017/6/20.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel格式接口类
 * @author tonywu
 * @version v1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE})
public @interface Excel {

    /**
     * 列名
     * @return
     */
    String name() default "";

    /**
     * 宽度
     * @return
     */
    int width() default 20;

    /**
     * 忽略该字段
     * @return
     */
    boolean skip() default false;

    /**
     * 日期格式化
     * @return
     */
    String dateFormat() default "yyyy-MM-dd HH:mm:ss";

    /**
     * 浮点数的精度
     * @return
     */
    int precision() default -1;

    /**
     * 是否金额标志，会自动除以100
     * @return
     */
    boolean isAmount() default false;

    /**
     * 四舍五入
     * @return
     */
    boolean round() default true;

}
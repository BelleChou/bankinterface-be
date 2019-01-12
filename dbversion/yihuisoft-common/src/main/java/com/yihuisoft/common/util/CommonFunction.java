package com.yihuisoft.common.util;

import java.util.Random;

/**
 * 公共函数类
 * @author tonywu
 * @version v1.0.0
 */
public class CommonFunction {

    /**
     * 生成指定范围的随机数
     * min最小值 max最大值
     * @return 字符串
     */
    public static int getRandNumberStr(int min,int max) {
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

}

package com.yihuisoft.common.util;

import java.util.UUID;

/**
 * UuidUtil类
 * @author tonywu
 * @version v1.0.0
 */
public class UuidUtil {

	/**
	 * 获取UUID
	 * @return
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

	/**
	 * main函数
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(get32UUID());
	}
}


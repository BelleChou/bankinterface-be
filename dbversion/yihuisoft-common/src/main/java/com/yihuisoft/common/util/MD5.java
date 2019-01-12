package com.yihuisoft.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5处理类
 * @author tonywu
 * @version v1.0.0
 */
public class MD5 {

	/**
	 * MD5
	 * @param str
	 * @return
	 */
	public static String md5(String str) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		return result;
	}

	/*****************************************
	 * 建议使用下面的方法
	 *****************************************/

	/**
	 * 字节流变成十六进制
	 * @param bytes
	 * @return
	 */
	public static String encodeHex(byte[] bytes) {
		StringBuffer buffer = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if (((int) bytes[i] & 0xff) < 0x10)
				buffer.append("0");
			buffer.append(Long.toString((int) bytes[i] & 0xff, 16));
		}
		return buffer.toString();
	}

	/**
	 * MD5摘要
	 * @param source
	 * @return
	 */
	public static String encodeMd5(String source) {
		try {
			return encodeHex(MessageDigest.getInstance("MD5").digest(source.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}
	/**
	 * main函数
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println( "md5=" +  md5("31119@qq.com" + "123456"));
		System.out.println( "encodeMd5=" + encodeMd5("31119@qq.com" + "123456"));
		//System.out.println(md5("mj1"));
	}
}

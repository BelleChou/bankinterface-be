package com.yihuisoft.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import java.util.Random;

/**
 * 字符串处理工具类
 * @author tonywu
 * @version v1.0.0
 */
public class StringUtil {

	/**逗号常量*/
	public static final String COMMA_STRING_GAP = ",";

	/**获取字符串编码
	 * @param str
	 * @return
	 */
	public static String getEncoding(String str) {      
	       String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {
				  return encode;
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {
				  return encode;
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {
				  return encode;
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {
				  return encode;
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";      
	   } 
	
	/**
	 * 随机产生N位验证码
	 * 
	 * @return 字符串
	 */
	public static String getRandCharString(int n) {
		char[] rand = new char[n];
		String str = "123456789qwertyuipkjhgfdsacvbnmQWERTYUPLKJHGFDSACVBNM";
		for (int i = 0; i < n; i++) {
			int index = new Random().nextInt(str.length());
			rand[i] = str.charAt(index); // 通过下标获取字符
		}
		return String.valueOf(rand);
	}

	/**
	 * 随机产生N位验证码
	 * 
	 * @return 字符串
	 */
	public static String getRandNumberStr(int n) {
		char[] rand = new char[n];
		String str = "1234567809";
		for (int i = 0; i < n; i++) {
			int index = new Random().nextInt(str.length());
			rand[i] = str.charAt(index); // 通过下标获取字符
		}
		return String.valueOf(rand);
	}

	/**
	 * 生成指定范围的随机数
	 * min最小值 max最大值
	 * @return 字符串
	 */
	public static String getRandNumberStr(int min,int max) {
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		return String.valueOf(s);
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s){
		return s!=null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s){
		return s==null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 逗号split后数组长度
	 * @param instr
	 * @return
	 */
	public static int splitCount(String instr){
		if(isEmpty(instr)) {
			return 0;
		}
		return instr.split(COMMA_STRING_GAP).length;
	}

	/**
	 * main函数
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(splitCount(""));
		System.out.println(splitCount("232"));
		System.out.println(splitCount("232,xy"));
	}
	
	
	
	
	
	/**
	 * 
	 * @param jsapiTicket
	 *            签名
	 * @param timestamp
	 *            时间戳
	 * @param noncestr
	 *            随机字符串
	 * @param url
	 *            当前网页的URL
	 * @return
	 */
	public static String wxSignatureJS(String jsapiTicket, String noncestr,
			String timestamp, String url) {
		Map<String, String> ret = sign(jsapiTicket, url, noncestr, timestamp);
		return ret.get("signature");
	}

	/**
	 * 签名
	 * @param jsapiTicket
	 * @param url
	 * @param nonceStr
	 * @param timestamp
	 * @return
	 */
	public static Map<String, String> sign(String jsapiTicket, String url,
			String nonceStr, String timestamp) {
		Map<String, String> ret = new HashMap<String, String>();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		/*
		 * string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str +
		 * "&timestamp=" + timestamp + "&url=" + url;
		 * System.out.println(string1);
		 */

		String[] paramArr = new String[] { "jsapiTicket=" + jsapiTicket,
				"timestamp=" + timestamp, "nonceStr=" + nonceStr, "url=" + url };
		Arrays.sort(paramArr);
		// 将排序后的结果拼接成一个字符串
		string1 = paramArr[0].concat("&" + paramArr[1])
				.concat("&" + paramArr[2]).concat("&" + paramArr[3]);
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
			System.out.println("signature:" + signature);
		} catch (NoSuchAlgorithmException e) {
			//e.printStackTrace();
			System.out.println(e);
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
			System.out.println(e);
		}

		ret.put("url", url);
		ret.put("jsapiTicket", jsapiTicket);
		ret.put("nonceStr", nonceStr);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);
		return ret;
	}

	/**
	 * 字节变成16进制
	 * @param hash
	 * @return
	 */
	public static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}







	
}

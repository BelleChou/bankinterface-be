package com.yihuisoft.common.enumdef;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务枚举类定义
 * @author tonywu
 * @version v1.0.0
 */
public class BizEnum {
	
	/**商户类型枚举*/
	public enum MerchantType{
		/**渠道大商户*/
		ChannelBig("渠道大商户", 10),
		/**品牌大商户*/
		BrandBig("品牌大商户", 20),
		/**代理商*/
		Agent("代理商", 30),
		/**分销商*/
		Distributor("分销商", 40),
		/**门店*/
		Shop("门店", 50),
		;
		/**名称*/
		private String name;
		/**类型*/
		private int type;
		/**map*/
		private static Map<Integer, MerchantType> allMerchantTypes;

		/**
		 * 构造
		 * @param name
		 * @param type
		 */
		private MerchantType(String name, int type){
			this.name = name;
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public int getType() {
			return type;
		}

		public Map<Integer, MerchantType> getAllMerchantTypes() {
        	return allMerchantTypes;
        }

		/**
		 * 根据类型获取对象
		 * @param type
		 * @return
		 */
		public static MerchantType fromType(int type){
			return allMerchantTypes.get(type);
		}

		/**
		 * 是否包含类型
		 * @param type
		 * @return
		 */
		public static boolean supported(int type){
			return allMerchantTypes.containsKey(type);
		}
		static {
			allMerchantTypes = new HashMap<Integer, MerchantType>();
			MerchantType[] types = values();
			for (MerchantType type : types){
				allMerchantTypes.put(type.getType(), type);
			}
		}
	}

	//微信菜单类型

	/**
	 * 微信菜单类型
	 */
	public enum WebChatMenuType{
		/**链接*/
		Link("链接", 1),
		/**回复素材*/
		ReplayResource("回复素材", 2),
		/**附近的门店*/
		NearShop("附近的门店", 3),
		/**跳转图文*/
		JampPic("跳转图文", 4),
		;
		/**名称*/
		private String name;
		/**类型*/
		private int type;
		/**map*/
		private static Map<Integer, WebChatMenuType> allWebChatMenuTypes;

		/**
		 * 构造函数
		 * @param name
		 * @param type
		 */
		private WebChatMenuType(String name, int type){
			this.name = name;
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public int getType() {
			return type;
		}
        public Map<Integer, WebChatMenuType> getAllWebChatMenuTypes() {
        	return allWebChatMenuTypes;
		}

		/**
		 * 根据类型获取对象
		 * @param type
		 * @return
		 */
		public static WebChatMenuType fromType(int type){
			return allWebChatMenuTypes.get(type);
		}

		/**
		 * 是否包含类型
		 * @param type
		 * @return
		 */
		public static boolean supported(int type){
			return allWebChatMenuTypes.containsKey(type);
		}
		static {
			allWebChatMenuTypes = new HashMap<Integer, WebChatMenuType>();
			WebChatMenuType[] types = values();
			for (WebChatMenuType type : types){
				allWebChatMenuTypes.put(type.getType(), type);
			}
		}
	}

}

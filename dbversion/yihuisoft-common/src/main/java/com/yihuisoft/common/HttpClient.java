package com.yihuisoft.common;

import java.io.IOException;
import com.yihuisoft.common.util.JSONUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Http客户端类
 * @author tonywu
 * @version v1.0.0
 */
public class HttpClient {

	/**
	 * 发送POST请求
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Object data) throws Exception{

		String jsonData = JSONUtils.toJson(data);

		CloseableHttpClient httpclient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
		try {
			StringEntity s = new StringEntity(jsonData, "utf-8");
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");//发送json数据需要设置contentType
			post.setEntity(s);
			HttpResponse res = httpclient.execute(post);
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				return EntityUtils.toString(res.getEntity());
			}
		} catch (Exception e) {
			//throw new RuntimeException(e);
			System.out.println(e);
		}
		return null;
	}


	/**
	 * main函数
	 * @param args
	 */
	  public static void main(String[] args) {
		  //String surl =Tools.getEBosServiceUrl("manager.user.userAndRoleByIdInUrl");
		  //String result = HttpClient.getPost(surl,null);
			//System.out.println(result);
	  }


	/**
	 * get函数
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
	    HttpGet httpGet = new HttpGet(url);
		String body = null; 
		try {
			CloseableHttpResponse response1 = httpclient.execute(httpGet);
		    try {
		    	//log.info("response status: " + response1.getStatusLine());

		        HttpEntity entity1 = response1.getEntity();
		    	//String charset = EntityUtils.getContentCharSet(entity1);
				//log.info(charset);
		        body = EntityUtils.toString(entity1);
		        EntityUtils.consume(entity1);
		    } finally {
		        response1.close();
		    }
		} catch (ClientProtocolException e) {
			//e.printStackTrace();
			System.out.println(e);
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println(e);
		}
		//log.info("create httppost:" + url);
		return body;
	}
 


}

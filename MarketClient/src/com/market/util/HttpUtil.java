package com.market.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class HttpUtil {
	//����Base URL����
	public static final String BASE_URL="http://192.168.2.108:8080/Market/";
	//public static final String BASE_URL1="http://169.254.50.166:8080/Market/";
	public static HttpGet getHttpGet(String url)
	{
		//ʵ����HttpGet
		HttpGet request=new HttpGet(url);
		return request;
	}
	public static HttpPost getHttpPost(String url)
	{
		//ʵ����HttpPost
		System.out.println("realize URL to a HttpPost");
		HttpPost request=new HttpPost(url);
		return request;
	}
	//ͨ��HttpGet���HttpResponse����
	public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException,IOException
	{
		//ʵ����HttpResponse
		HttpResponse response=new DefaultHttpClient().execute(request);
		return response;
	}
	// ͨ��HttpPost���HttpResponse����
	public static HttpResponse getHttpResponse(HttpPost request)throws ClientProtocolException,IOException
	{
		//ʵ����HttpResponse
		HttpResponse response=new DefaultHttpClient().execute(request);
		return response;
	}
	//ͨ��url����post���󣬷���������
	public static String queryStringForPost(String url)
	{
		//���HttpPostʵ��
		HttpPost request=HttpUtil.getHttpPost(url);
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		String result=null;
		try
		{
			//���HttpResponseʵ��
			HttpResponse response=HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				result=EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
				return result;
			}
		}catch(ClientProtocolException e)
		{
			e.printStackTrace();
			result = null;
			return result;
		}catch(IOException e)
		{
			e.printStackTrace();
			result = null;
			return result;
		}
		return null;
	}
	
	//ͨ��HttpPost����get���󣬷���������
	public static String queryStringForGet(String url)
	{
		//���HttpPostʵ��
		HttpGet request=HttpUtil.getHttpGet(url);
		String result=null;
		try
		{
			//���HttpResponseʵ��
			HttpResponse response=HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				result=EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
				return result;
			}
		}catch(ClientProtocolException e)
		{
			e.printStackTrace();
			result="�����쳣��";
			return result;
		}catch(IOException e)
		{
			e.printStackTrace();
			result="�����쳣��";
			return result;
		}
		return null;
	}
	
	//ͨ��HttpPost����post���󣬷���������
	public static String queryStringForPost(HttpPost request)
	{
		String result=null;
		try
		{
			//���HttpResponseʵ��
			HttpResponse response=HttpUtil.getHttpResponse(request);
			if(response.getStatusLine().getStatusCode()==200)
			{
				result=EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
				System.out.print(result);
				return result;
			}
		}catch(ClientProtocolException e)
		{
			e.printStackTrace();
			result="�����쳣��";
			return result;
		}catch(IOException e)
		{
			e.printStackTrace();
			result="�����쳣��";
			return result;
		}
		return null;
	}
	
	public static String sendHttpClientPostRequest(String path, Map<String, String> params, String encoding) throws Exception{  
		  String result;
		  List<NameValuePair> param = new ArrayList<NameValuePair>();  
		  if(params!=null && !params.isEmpty()){  
			  for(Map.Entry<String, String> entry : params.entrySet()){  
				  param.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
			  }  
		  }  
		  UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, encoding);  
		  HttpPost post = new HttpPost(path);  
		//  HttpGet get = new HttpGet();  
		  post.setEntity(entity);  
		  DefaultHttpClient client = new DefaultHttpClient();  
		  HttpResponse response = client.execute(post);
		  result = EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
		  if(response.getStatusLine().getStatusCode() == 200){  
		//   response.getEntity().getContent();//��ȡ���������ص�����  
			  return result;
		  }  
		  return null;
		}  

	
	
}

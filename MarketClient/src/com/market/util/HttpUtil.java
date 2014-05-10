package com.market.util;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class HttpUtil {
	//����Base URL����
	public static final String BASE_URL="http://10.0.2.2:8080/Market/";
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
		System.out.println("------");
		HttpPost request=new HttpPost(url);
		System.out.println("&&&&&&&&");
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
			result="�����쳣1111111111111111111111��";
			return result;
		}catch(IOException e)
		{
			e.printStackTrace();
			result="�����쳣22222222222222222222222��";
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
	
	
}

package com.market.tools;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.market.types.GoodsType;
import com.market.util.HttpUtil;

public class CommonMethods {
	private static String goodsListResult;
	private static int goodsListFlag;

	private static String goodsInfoResult;
	private static int goodsInfoFlag;

	private static String goodsUploadResult;
	private static int goodsUploadFlag;
	
	
	public static String queryForGoodsList(int type, int startID)
			throws InterruptedException {
		goodsListFlag = 0;
		String queryString = "type=" + type + "&startID=" + startID;
		final String url = HttpUtil.BASE_URL + "page/GoodsListServlet?"
				+ queryString;
		System.out.println(url);
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Android starts to send need request\n");
				goodsListResult = HttpUtil.queryStringForPost(url);
				if (goodsListResult.equals("")) {
					System.out.println("set listFlag to -1");
					goodsListFlag = -1;
				} else {
					System.out.println("set listFlag to 1");
					goodsListFlag = 1;
				}
			}
		};
		Thread thread_getgood = new Thread(runnable);
		thread_getgood.setPriority(1);
		thread_getgood.start();
		int i = 0;
		while (goodsListFlag == 0 && i++ < 1000)
			Thread.sleep(1);
		if (goodsListFlag == 0)
			return "#";
		return goodsListResult;
	}

	public static String queryForGoodsInfo(String goodsID)
			throws InterruptedException {
		goodsInfoFlag = 0;
		final String url = HttpUtil.BASE_URL
				+ "page/GoodsDisplayServlet?goodsID=" + goodsID;
		System.out.println(url);
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Android starts to send need request\n");
				goodsInfoResult = HttpUtil.queryStringForPost(url);
				if (goodsInfoResult.equals("#")) {
					System.out.println("set goodsInfoistFlag to -1");
					goodsInfoFlag = -1;
				} else {
					System.out.println("set goodsInfoFlag to 1");
					goodsInfoFlag = 1;
				}
			}
		};
		Thread thread_getgood = new Thread(runnable);
		thread_getgood.setPriority(1);
		thread_getgood.start();
		int i = 0;
		while (goodsInfoFlag == 0 && i++ < 1000)
			Thread.sleep(1);
		if (goodsInfoFlag == 0)
			return "";
		else if(goodsInfoFlag == -1)
			return "#";
		return goodsInfoResult;
	}
	
	public static boolean queryForGoodsUpload(final Map<String, String> param) throws InterruptedException{
		goodsUploadFlag = 0;
		final String url = HttpUtil.BASE_URL + "page/GoodsUploadServlet";
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Android starts to send upload request\n");
				try {
					goodsUploadResult = HttpUtil.sendHttpClientPostRequest(url, param, "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (goodsUploadResult.equals("#")) {
					System.out.println("set goodsUploadFlag to -1");
					goodsUploadFlag = -1;
				} else {
					System.out.println("set goodsUploadFlag to 1");
					goodsUploadFlag = 1;
				}
			}
		};
		Thread thread_getgood = new Thread(runnable);
		thread_getgood.setPriority(1);
		thread_getgood.start();
		int i = 0;
		while (goodsUploadFlag == 0 && i++ < 3000)
			Thread.sleep(1);
		if (goodsUploadFlag != 1)
			return false;
		return true;
		
	}
	
	public static boolean queryForGoodsUpload(String goodsName, String goodsOwner, String goodsPrice, String goodsImage, 
			int goodsClass, String goodsIntroduction, int goodsProperty) throws InterruptedException{
goodsUploadFlag = 0;
final String url = HttpUtil.BASE_URL + "page/GoodsUploadServlet?goodsName=" + goodsName 
	+ "&goodsOwner=" + goodsOwner + "&goodsPrice=" + goodsPrice + "&goodsImage=" + goodsImage 
	+ "&goodsClass=" + goodsClass + "&goodsIntroduction=" + goodsIntroduction + "&goodsProperty=" + goodsProperty;
Runnable runnable = new Runnable() {
@Override
public void run() {
	System.out.println("Android starts to send upload request\n");
	goodsUploadResult = HttpUtil.queryStringForPost(url);
	
	if (goodsUploadResult.equals("#")) {
		System.out.println("set goodsUploadFlag to -1");
		goodsUploadFlag = -1;
	} else {
		System.out.println("set goodsUploadFlag to 1");
		goodsUploadFlag = 1;
	}
}
};
Thread thread_getgood = new Thread(runnable);
thread_getgood.setPriority(1);
thread_getgood.start();
int i = 0;
while (goodsUploadFlag == 0 && i++ < 1000)
Thread.sleep(1);
if (goodsUploadFlag != 1)
return false;
return true;

}
	

	public static String BitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}
	
	

	public static Bitmap StringToBitMap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
					encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

}

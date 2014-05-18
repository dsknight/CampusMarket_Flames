package com.market.tools;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.market.util.HttpUtil;

public class CommonMethods {
	private static String goodsListResult;
	private static int goodsListFlag;

	private static String goodsInfoResult;
	private static int goodsInfoFlag;

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
					System.out.println("set listFlag to -1");
					goodsInfoFlag = -1;
				} else {
					System.out.println("set listFlag to 1");
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

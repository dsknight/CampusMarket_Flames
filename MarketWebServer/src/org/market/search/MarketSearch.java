package org.market.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;

import org.market.controlmysql.GoodsInfo;
import org.market.types.GoodsType;

public class MarketSearch {
	public static String search(String needs){
		String result = "" ;
		GoodsInfo info = new GoodsInfo();
		ArrayList<GoodsType> goodsList = info.allGoods();
		ArrayList<GoodsWithDistance> goodsWithDistanceList = new ArrayList<MarketSearch.GoodsWithDistance>();
		int[] distance = new int[goodsList.size()];
		for(int i = 0; i < goodsList.size(); i++){
			//计算所有物品名称与needs的Levenshtein Distance
			distance[i] = LevenshteinDistance.countLevenshteinDistance(needs, goodsList.get(i).getName());
			System.out.println(needs+ " " + goodsList.get(i).getName() +"***before put********distance is:"+distance[i]);
			if(distance[i] < max(needs.length(),goodsList.get(i).getName().length())){
				//System.out.println("int put********");
				//字符串距离小于长度较大者即可放到List中
				goodsWithDistanceList.add(new GoodsWithDistance(goodsList.get(i), distance[i]));
			}
		}
		Collections.sort(goodsWithDistanceList);
		for(int i = 0; i < goodsWithDistanceList.size();i++){
			GoodsType temp = goodsWithDistanceList.get(i).goods;
			result += "*"+temp.getGNO()+" "+temp.getName();
		}
		System.out.println("search result of " + needs + " is " + result);
		return result;
	}
	
	public static String suggest(String needs){
		String result = "" ;
		GoodsInfo info = new GoodsInfo();
		ArrayList<GoodsType> goodsList = info.allGoods();
		int min = 0,secondMin = 0,thirdMin = 0;
		int[] distance = new int[goodsList.size()];
		for(int i = 0; i < goodsList.size(); i++){
			//计算所有物品名称与needs的Levenshtein Distance
			distance[i] = LevenshteinDistance.countLevenshteinDistance(needs, goodsList.get(i).getName());
			//更新最接近的三个名称的位置
			if(distance[i] >= distance[thirdMin])
				continue;
			else if(distance[i] < distance[thirdMin] && distance[i] >= distance[secondMin])
				thirdMin = i;
			else if(distance[i] < distance[secondMin] && distance[i] >= distance[min]){
				thirdMin = secondMin;
				secondMin = i;
			}
			else{
				thirdMin = secondMin;
				secondMin = min;
				min = i;
			}
		}
		result = "*"+goodsList.get(min).getGNO()+" "+goodsList.get(min).getName()+
		         "*"+goodsList.get(secondMin).getGNO()+" "+goodsList.get(secondMin).getName()+
		         "*"+goodsList.get(thirdMin).getGNO()+" "+goodsList.get(thirdMin).getName();
		System.out.println("suggest result of " + needs + " is " + result);
		return result;
	}
	
	private static int max(int a ,int b){
		System.out.println("max is : "+ (a > b ? a:b));
		return a > b ? a:b;
	}
	
	private static class GoodsWithDistance implements Comparable<GoodsWithDistance>{
		private GoodsType goods;
		private int distance;
		
		public GoodsWithDistance(GoodsType goods,int distance){
			this.goods = goods;
			this.distance = distance;
		}

		public int compareTo(GoodsWithDistance o) {
			// TODO Auto-generated method stub
			if(this.distance < o.distance)
				return -1;
			else if(this.distance == o.distance)
				return 0;
			else
				return 1;
		}
	}
}

package org.market.search;


public class LevenshteinDistance {
	
	public static int countLevenshteinDistance(String str1,String str2){
		if(str1 == null)
			return str2.length();
		else if(str2 == null)
			return str1.length();
		int n = str1.length();
		int m = str2.length();
		int[][] cost = new int[m][n];
		int[][] distance = new int[m][n];
		for(int i = 0;i < m;i++)
			for(int j = 0;j < n;j++){
				if(str1.toLowerCase().charAt(j) == str2.toLowerCase().charAt(i))
					cost[i][j] = 0;
				else
					cost[i][j] = 1;
				
				//¹¹½¨distance¾ØÕó
				if(i == 0 && j == 0)
					distance[i][j] = cost[i][j];
				else if(i == 0)
					distance[i][j] = min(distance[i][j-1] + 1,j + cost[i][j]);
				else if(j == 0)
					distance[i][j] = min(distance[i-1][j] + 1,i + cost[i][j]);
				else
					distance[i][j] = minOf3(distance[i][j-1] + 1,distance[i-1][j] + 1,distance[i-1][j-1] + cost[i][j]);
			}
		return distance[m-1][n-1];
				
	}
	
	private static int minOf3(int num1,int num2,int num3){
		int min = num1;
		if(num2 < min)
			min = num2;
		if(num3 < min)
			min = num3;
		return min;
	}
	
	private static int min(int a,int b){
		return a < b ? a : b;
	}
}

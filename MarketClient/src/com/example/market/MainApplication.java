package com.example.market;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;

import com.market.types.ClientType;
import com.market.types.GoodsType;
public class MainApplication extends Application{
	
	private ClientType client;
	private ArrayList<GoodsType> goodsList;
	private String needs;
	private String suggest;
	private String displayInfo;
	

	public ArrayList<GoodsType> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(ArrayList<GoodsType> goodsList) {
		this.goodsList = goodsList;
	}
	public String getSuggest() {
		return suggest;
	}
	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
	public ClientType getClient() {
		return client;
	}
	public void setClient(ClientType client) {
		this.client = client;
	}
	public void setNeeds(String needs) {
		this.needs = needs;
	}
	public String getNeeds() {
		return needs;
	}
	public String getDisplayInfo() {
		return displayInfo;
	}
	public void setDisplayInfo(String displayInfo) {
		this.displayInfo = displayInfo;
	}
	
	
	
	
	
	
}

























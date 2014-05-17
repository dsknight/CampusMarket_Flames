package com.example.market;
import java.util.ArrayList;
import java.util.List;

import com.market.types.ClientType;
import com.market.types.GoodsType;

import android.app.Application;
public class MainApplication extends Application{
	
	private ClientType client;
	private List<GoodsType> goodsList;
	private GoodsType needs;
	private String suggest;
	

	public List<GoodsType> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<GoodsType> goodsList) {
		this.goodsList = goodsList;
	}
	public GoodsType getNeeds() {
		return needs;
	}
	public void setNeeds(GoodsType needs) {
		this.needs = needs;
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
	
	
	
	
	
	
}

























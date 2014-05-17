package com.example.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.market.util.XListView;
import com.market.util.XListView.IXListViewListener;

public class AllGoodsActivity  extends Activity implements IXListViewListener{
    
	private XListView listView;
	private ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String,Object>>();;
	private TextView topbar;
	private Handler mHandler;
	private int start = 0;
	private static int refreshCnt = 0;
	private XAdapter xadapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_all);
		topbar = (TextView)findViewById(R.id.all_goods);
		Intent intent = this.getIntent();
		if(intent != null){
			String msg = intent.getStringExtra("text");
			if(msg != null)
				topbar.setText(msg);
		}
		listView = (XListView)findViewById(R.id.all_goods_XListView);
		listView.setPullLoadEnable(true);
		xadapter = new XAdapter(this);
		listView.setAdapter(xadapter);
		listView.setXListViewListener(this);
		mHandler = new Handler();
		
		getListInfo();
		//showList();
		listView.setOnItemClickListener(new OnItemClickListener() {  	  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
            	Toast.makeText(getApplicationContext(),"choose "+ arg2+" goods",Toast.LENGTH_SHORT).show();
            	Intent intent=new Intent(AllGoodsActivity.this,GoodsInfoActivity.class);
				startActivity(intent);
            }  
        });
		
	}
	
	public void getListInfo(){
		for(int i = 0;i < 12;i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			start++;
			map.put("title", "商品列表--商品"+start);
			map.put("seller", "HelloKitty");
			listItem.add(map);
		}
	}	
	/*
	public void showList(){
		 SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem, R.layout.main_all_list_item,
				new String[]{"catergoryitem_title","catergoryitem_seller"} , 
				new int[]{R.id.catergoryitem_title,R.id.catergoryitem_seller});
		 listView.setAdapter(listItemAdapter);	 
	}*/
	private void geneItems() {
		for(int i = 0;i < 12;i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("title", "商品列表--商品"+i);
			map.put("seller", "HelloKitty");
			listItem.add(map);
		}
	}

	@SuppressLint("SimpleDateFormat")
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = format1.format(new Date());
		listView.setRefreshTime(date);
	}
	
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = ++refreshCnt;
				listItem = new ArrayList<HashMap<String,Object>>();
				geneItems();
				// mAdapter.notifyDataSetChanged();
				xadapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				geneItems();
				xadapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
	}

	public class XAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		public XAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return listItem.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = mInflater.inflate(R.layout.main_all_list_item, null);
			TextView text = (TextView)convertView.findViewById(R.id.catergoryitem_title);
			text.setText(listItem.get(position).get("title").toString());
			TextView text2 = (TextView)convertView.findViewById(R.id.catergoryitem_seller);
			text2.setText(listItem.get(position).get("seller").toString());
			return convertView;
		}
	}
}

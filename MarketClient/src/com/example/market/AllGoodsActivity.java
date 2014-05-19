package com.example.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.market.tools.CommonMethods;
import com.market.types.GoodsType;
import com.market.util.XListView;
import com.market.util.XListView.IXListViewListener;

public class AllGoodsActivity  extends Activity implements IXListViewListener{
    
	private XListView listView;
	private ArrayList<HashMap<String,Object>> listItem = new ArrayList<HashMap<String,Object>>();;
	private TextView topbar;
	private Handler mHandler;
	private int start = 0;
	//private static int refreshCnt = 0;
	private XAdapter xadapter;
	private int type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = 1;//用来表示列表的类型  1:全部  2-7:分类显示
		setContentView(R.layout.main_all);
		topbar = (TextView)findViewById(R.id.all_goods);
		Intent intent = this.getIntent();
		if(intent != null){
			String msg = intent.getStringExtra("text");
			if(msg != null){
				topbar.setText(msg.split(" ")[1]);
				type = Integer.parseInt(msg.split(" ")[0]);
			}
		}
		listView = (XListView)findViewById(R.id.all_goods_XListView);
		listView.setPullLoadEnable(true);
		xadapter = new XAdapter(this);
		listView.setAdapter(xadapter);
		listView.setXListViewListener(this);
		mHandler = new Handler();
		
		try {
			getListInfo();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//showList();
		listView.setOnItemClickListener(new OnItemClickListener() {  	  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
            	Toast.makeText(getApplicationContext(),"choose "+ arg2+" goods",Toast.LENGTH_SHORT).show();
            	String goodsID = listItem.get(arg2-1).get("GNO").toString();
            	String result = null;
				try {
					result = CommonMethods.queryForGoodsInfo(goodsID);
					((MainApplication)getApplicationContext()).setDisplayInfo(result);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result.equals("#")){
					if(type == 1)
						showDialog("该商品可能下架了，请尝试刷新~");
					else
						showDialog("饿，还没有该分类的商品，尽请期待~");
				}
				else if(result.equals("!"))
					showDialog("网络异常，请稍后再试");
				else{	
					Intent intent=new Intent(AllGoodsActivity.this,GoodsInfoActivity.class);
					startActivity(intent);
				}
            }  
        });
		
	}
	
	public void getListInfo() throws InterruptedException, IOException{
		String result = CommonMethods.queryForGoodsList(type, 0+"");
		if(result.equals("!"))
			showDialog("网络异常，请稍后再试");
		else if(result.equals("#"))
			showDialog("出了点小问题...");
		else{
			String[] gStrings = result.split("!\\*G");
			for(int i = 0; i < gStrings.length; i++){
				System.out.println(gStrings[i]);
				GoodsType goods = new GoodsType(gStrings[i].substring(3));
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("title", goods.getName());
				map.put("seller", "提供者: " + goods.getOwner());
				map.put("GNO", goods.getGNO());
				listItem.add(map);
				start = goods.getGNO();
			}
			/*if(gStrings.length < 20){
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("title", "没有更多了...");
				map.put("seller","");
				listItem.add(map);
			}*/
		}
	}	
	/*
	public void showList(){
		 SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem, R.layout.main_all_list_item,
				new String[]{"catergoryitem_title","catergoryitem_seller"} , 
				new int[]{R.id.catergoryitem_title,R.id.catergoryitem_seller});
		 listView.setAdapter(listItemAdapter);	 
	}*/
	private void geneItems() throws InterruptedException, IOException {
		String result = CommonMethods.queryForGoodsList(type, start+"");
		if(result.equals("!"))
			showDialog("网络异常，请稍后再试");
		else if(result.equals("#"))
			showDialog("没有更多了~");
		else{
			if(!result.equals("")){
				String[] gStrings = result.split("!\\*G");
				for(int i = 0; i < gStrings.length; i++){
					System.out.println(gStrings[i]);
					GoodsType goods = new GoodsType(gStrings[i].substring(3));
					HashMap<String,Object> map = new HashMap<String,Object>();
					map.put("title", goods.getName());
					map.put("seller", "提供者: " + goods.getOwner());
					map.put("GNO", goods.getGNO());
					listItem.add(map);
					start = goods.getGNO();
				}
			}	
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
				listItem = new ArrayList<HashMap<String,Object>>();
				start = 0;
				try {
					geneItems();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
				try {
					geneItems();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
	
	private void showDialog(String msg)
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("确定",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {			
			}
		});
		AlertDialog alert=builder.create();
		alert.show();
	}
}

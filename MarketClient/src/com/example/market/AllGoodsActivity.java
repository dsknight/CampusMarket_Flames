package com.example.market;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class AllGoodsActivity  extends Activity{
    
	private ListView listView = null;
	private ArrayList<HashMap<String,Object>> listItem = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_all);
		listView = (ListView)findViewById(R.id.main_all_listView);
		getListInfo();
		showList();
		listView.setOnItemClickListener(new OnItemClickListener() {  	  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
            	Toast.makeText(getApplicationContext(),"choose "+ arg2+" goods",Toast.LENGTH_SHORT).show();  
            }  
        });
	}
	
	public void getListInfo(){
		listItem = new ArrayList<HashMap<String,Object>>();
		for(int i = 0;i < 12;i++){
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("catergoryitem_title", "商品列表--商品"+i);
			map.put("catergoryitem_seller", "HelloKitty");
			listItem.add(map);
		}
	}	
	public void showList(){
		 SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem, R.layout.main_all_listitems,
				new String[]{"catergoryitem_title","catergoryitem_seller"} , 
				new int[]{R.id.catergoryitem_title,R.id.catergoryitem_seller});
		 listView.setAdapter(listItemAdapter);	 
	}

}

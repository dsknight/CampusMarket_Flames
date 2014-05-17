package com.example.market;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyGoodsActivity extends Activity {

	private ListView listView;
	private String[] l_goodsName = new String[1];
	private String[] l_goodsDesp = new String[1];
	
	private Button addGoods;
	private MyAdapter myAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_goods);
		
		
		l_goodsName[0] = "this is my goods name";
		l_goodsDesp[0] = "this is my goods description";
		myAdapter = new MyAdapter(this);
		listView = (ListView)findViewById(R.id.myGoodsList);
		listView.setAdapter(myAdapter);
		
		addGoods = (Button)findViewById(R.id.add_good_button);
		addGoods.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent_signup=new Intent(MyGoodsActivity.this,AddGoodsActivity.class);
				startActivity(intent_signup);
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {  	  
            @Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                    long arg3) {  
            	Toast.makeText(getApplicationContext(),"choose "+ arg2+" goods",Toast.LENGTH_SHORT).show();
            	Intent intent_signup=new Intent(MyGoodsActivity.this,MyGoodsInfoActivity.class);
				startActivity(intent_signup);
            }  
        });
		
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_goods, menu);
		return true;
	}*/

	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return l_goodsName.length;
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
			convertView = mInflater.inflate(R.layout.my_goods_list_item, null);
			TextView text = (TextView)convertView.findViewById(R.id.my_goods_name);
			text.setText(l_goodsName[position]);
			TextView text1 = (TextView)convertView.findViewById(R.id.my_goods_description);
			text1.setText(l_goodsDesp[position]);
			return convertView;
		}
	}
}

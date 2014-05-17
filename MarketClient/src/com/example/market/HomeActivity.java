package com.example.market;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.market.util.HttpUtil;

@SuppressLint("HandlerLeak")
public class HomeActivity  extends Activity  {

	private SearchView sv;
    private ListView lv;
    private View home_suggest1;
    private View home_suggest2;
    private View home_suggest3;
    private String url,msg;
    private String[] mStrings = new String[1];
    private Handler handler;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_home);
		MainApplication appState = (MainApplication)getApplicationContext();
		String[] suggests = appState.getSuggest().split("\\*");
		((TextView)findViewById(R.id.suggest1)).setText(suggests[0]);
		((TextView)findViewById(R.id.suggest2)).setText(suggests[1]);
		((TextView)findViewById(R.id.suggest3)).setText(suggests[2]);
		
		home_suggest1 = (View)findViewById(R.id.home_suggest1);
		home_suggest1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(HomeActivity.this,GoodsInfoActivity.class);
				startActivity(intent);
			}
		});
		home_suggest2 = (View)findViewById(R.id.home_suggest2);
		home_suggest2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(HomeActivity.this,GoodsInfoActivity.class);
				startActivity(intent);
			}
		});
		home_suggest3 = (View)findViewById(R.id.home_suggest3);
		home_suggest3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(HomeActivity.this,GoodsInfoActivity.class);
				startActivity(intent);
			}
		});
		
		mStrings[0] = "Welcome";
		final MyAdapter new_adapter = new MyAdapter(this);
		lv = (ListView)findViewById(R.id.lvForSearch);
		lv.setAdapter(new_adapter);
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mStrings);
        //lv.setAdapter(adapter);
        //lv.setTextFilterEnabled(true);
        
		handler = new Handler(){
	    	public void handleMessage(Message msg){
	    		if(msg.what == 333){
	    			System.out.println("message 333 recieved!");
	    			//ArrayAdapter<String> tempAdapter = new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_list_item_1,mStrings);
	    			//lv.setAdapter(tempAdapter);
	    			//MyAdapter new_adapter = new MyAdapter(this);
	    			//lv.setAdapter(new_adapter);
	    			new_adapter.notifyDataSetChanged();
	    		}
	    	}
	    };
		
        sv = (SearchView)findViewById(R.id.home_search);
        //设置该SearchView默认是否自动缩小为图标
        sv.setIconifiedByDefault(false);
        //为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				
				//Toast.makeText(HomeActivity.this, "您选择的是："+query, Toast.LENGTH_SHORT).show();
					//return true;
				url = HttpUtil.BASE_URL+"page/AndroidSearchServlet?query=" + query;
				System.out.println(url);
				Runnable runnable=new Runnable()
				{
					@Override
					public void run()
					{
						msg=HttpUtil.queryStringForPost(url);
						System.out.println("the result if search is :" + msg);
						if(msg.length() == 0){
							mStrings = new String[1];
							mStrings[0] = "抱歉，没有符合的结果...";
						}
						else{
							mStrings = msg.substring(1).split("\\*");
						}
						Message message = new Message();  
		                message.what = 333;
		                handler.sendMessage(message);
					}
				};
				Thread thread_getgood=new Thread(runnable);
				thread_getgood.setPriority(1);
				thread_getgood.start();
				
				return true;
				
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				 // TODO Auto-generated method stub
		       /* if(TextUtils.isEmpty(newText))
		        {
		            //清楚ListView的过滤
		            lv.clearTextFilter();
		        }
		        else
		        {
		            //使用用户输入的内容对ListView的列表项进行过滤
		            lv.setFilterText(newText);
		        
		        }*/
		        return true;
			}
		});
        //设置该SearchView显示搜索按钮
        sv.setSubmitButtonEnabled(true);

        //设置该SearchView内默认显示的提示文本
        sv.setQueryHint("查找");
           
    }
	
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return mStrings.length;
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
			convertView = mInflater.inflate(R.layout.home_list_item, null);
			TextView text = (TextView)convertView.findViewById(R.id.list_suggest);
			text.setText(mStrings[position]);
			return convertView;
		}
	}
}



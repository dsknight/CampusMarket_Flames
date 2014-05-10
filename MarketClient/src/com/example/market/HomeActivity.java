package com.example.market;
import java.net.URLEncoder;

import com.market.util.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity  extends Activity  {

	private SearchView sv;
    private ListView lv;
    private String url,msg;
    private String[] mStrings = new String[1];
    
    private Handler handler = new Handler(){
    	public void handleMessage(Message msg){
    		if(msg.what == 333){
    			System.out.println("message 333 recieved!");
    			ArrayAdapter<String> tempAdapter = new ArrayAdapter<String>(HomeActivity.this,android.R.layout.simple_list_item_1,mStrings);
    			lv.setAdapter(tempAdapter);
    		}
    	}
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_home);
		MainApplication appState = (MainApplication)getApplicationContext();
		String[] suggests = appState.getSuggest().split("\\*");
		((TextView)findViewById(R.id.suggest1)).setText(suggests[0]);
		((TextView)findViewById(R.id.suggest2)).setText(suggests[1]);
		((TextView)findViewById(R.id.suggest3)).setText(suggests[2]);
		
		mStrings[0] = "Welcome";
		lv=(ListView)findViewById(R.id.lvForSearch);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mStrings);
        lv.setAdapter(adapter);
        //lv.setTextFilterEnabled(true);
        
        sv=(SearchView)findViewById(R.id.searchView);
        //���ø�SearchViewĬ���Ƿ��Զ���СΪͼ��
        sv.setIconifiedByDefault(false);
        //Ϊ��SearchView��������¼�������
        sv.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				
				//Toast.makeText(HomeActivity.this, "��ѡ����ǣ�"+query, Toast.LENGTH_SHORT).show();
					//return true;
				url=HttpUtil.BASE_URL+"page/AndroidSearchServlet?query=" + query;
				System.out.println(url);
				Runnable runnable=new Runnable()
				{
					@Override
					public void run()
					{
						msg=HttpUtil.queryStringForPost(url);
						System.out.println("the result if search is :" + msg);
						if(msg.length() == 0)
							mStrings[0] = "��Ǹ��û�з��ϵĽ��...";
						else
							mStrings = msg.substring(1).split("\\*");
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
		            //���ListView�Ĺ���
		            lv.clearTextFilter();
		        }
		        else
		        {
		            //ʹ���û���������ݶ�ListView���б�����й���
		            lv.setFilterText(newText);
		        
		        }*/
		        return true;
			}
		});
        //���ø�SearchView��ʾ������ť
        sv.setSubmitButtonEnabled(true);

        //���ø�SearchView��Ĭ����ʾ����ʾ�ı�
        sv.setQueryHint("����");
           
    }
	
	
}



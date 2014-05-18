package com.example.market;

import com.market.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MyNeedActivity extends Activity{ 

	private EditText myNeed;
	private Button deliver;
	
	private int if_deliver;
	private String need;
	private String url;
	private String result;
	
	private MainApplication appState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		appState = (MainApplication)getApplicationContext();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_need);
		
		myNeed = (EditText)findViewById(R.id.myneeds);
		deliver = (Button)findViewById(R.id.deliverneeds);
		
		myNeed.setText(appState.getNeeds());
		deliver.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(validate()){
					deliverNeed();
					int i = 0;
					while(if_deliver == 0 && i++ < 1000){
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if(if_deliver == 0){
						System.out.println("if_deliver == 0");
						showDialog("�����쳣�����Ժ�����");
					}
					else if(if_deliver == 1){
						System.out.println("if_deliver == 1");
						showDialog("�����ɹ���");
						if_deliver = 0;
					}
				}
			}
		});
	}
	
	private boolean validate(){
		need = myNeed.getText().toString();
		if(need.equals("")){
			showDialog("������Ϊ��");
			return false;
		}
		else if(need.contains("*")){
			showDialog("��*��Ϊ�Ƿ��ַ�");
			return false;
		}
		return true;
	}
	
	private void deliverNeed(){
		need = myNeed.getText().toString();
		String usern = appState.getClient().getName();
		String queryString="goodsOwner="+usern+"&goodsName="+need+"&goodsProperty="+1;
		url = HttpUtil.BASE_URL+"page/GoodsUploadServlet?"+queryString;
		System.out.println(url);
		
		Runnable runnable = new Runnable(){
			@Override
			public void run(){
				System.out.println("Android starts to send need request\n");
				result = HttpUtil.queryStringForPost(url);
				if(result.equals("#")){
					System.out.println("set if_deliver to 0");
					if_deliver = 0;
				}else{
					System.out.println("set if_deliver to 1");
					if_deliver = 1;
					//TODO
					appState.setNeeds(need);
					appState.setSuggest(result.substring(1));
				}
			}
		};
		Thread thread_getgood = new Thread(runnable);
		thread_getgood.setPriority(1);
		thread_getgood.start();
	}
	
	private void showDialog(String msg)
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("ȷ��",new DialogInterface.OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {			
			}
		});
		AlertDialog alert=builder.create();
		alert.show();
	}

}

package com.example.market;

import com.market.util.HttpUtil;
import com.market.util.UserConfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AlterUsrNeedActivity extends Activity{ 

	private EditText myNeed;
	private Button deliver;
	
	private int if_deliver;
	private String need;
	private String url;
	private String result;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_need);
		
		myNeed = (EditText)findViewById(R.id.myneeds);
		deliver = (Button)findViewById(R.id.deliverneeds);
		
		myNeed.setText(UserConfig.con_needs);
		deliver.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(validate()){
					deliverNeed();
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
		return true;
	}
	
	private void deliverNeed(){
		need = myNeed.getText().toString();
		String usern = UserConfig.con_usr;
		String queryString="username="+usern+"&need="+need;
		url = HttpUtil.BASE_URL+"page/AndroidChangeNeedServlet?"+queryString;
		System.out.println(url);
		
		Runnable runnable = new Runnable(){
			@Override
			public void run(){
				System.out.println("Android starts to send need request\n");
				result = HttpUtil.queryStringForPost(url);
				if(result == null || result.equals("sorry")){
					System.out.println("set if_deliver to 0");
					if_deliver = 0;
				}else if(result.equals("success")){
					System.out.println("set if_deliver to 1");
					if_deliver = 1;
					//TODO
					UserConfig.con_needs = need;
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

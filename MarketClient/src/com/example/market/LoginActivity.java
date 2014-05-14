package com.example.market;

import com.market.util.HttpUtil;
import com.market.util.UserConfig;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
public class LoginActivity extends Activity{
	private EditText user_i;
	private EditText pw_i;
	private Button login;
	private Button signin;
	private String url;
	private int if_login=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("welcome");
		setContentView(R.layout.a_login);
		user_i=(EditText)findViewById(R.id.Editusername);
		pw_i=(EditText)findViewById(R.id.Editpw);
		login=(Button)findViewById(R.id.login);
		signin=(Button)findViewById(R.id.signin);
		login.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println("login---- click login");
				if(validate()){
					login();
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(if_login == 0){
						showDialog("���粻�ȶ������Ժ�����");
					}
					else if(if_login == 2){
						System.out.println("Turn to mainpage");
						Intent intent_login=new Intent(LoginActivity.this,MainPageActivity.class);
						startActivity(intent_login);
						if_login = 0;
					}
					else if(if_login == 1){
						showDialog("�û����ƻ�������������������룡");
						if_login = 0;
					}
				}
			}
		});
		
		signin.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println("login--- click register");
				Intent intent_signup=new Intent(LoginActivity.this,SignupActivity.class);
				startActivity(intent_signup);
			}
		});
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 //Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	//����һ����ʾ��ʾ��Ϣ�ĶԻ���
	private void showDialog(String msg)
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
			
			//@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub				
			}
		});
		AlertDialog alert=builder.create();
		alert.show();
	}
	
	private boolean validate()
	{
		String un=user_i.getText().toString();
		System.out.println(un);
		if(un.equals(""))
		{
			showDialog("�û������Ǳ�����");
			return false;
		}
		String pwd=pw_i.getText().toString();
		System.out.println(pwd);
		if(pwd.equals(""))
		{
			showDialog("�û������Ǳ�����");
			return false;
		}
		return true;
	}
	
	//ͨ���û�����������в�ѯ������Post���󣬻����Ӧ���
	private String query(String un,String pwd)
	{
		String queryString="username="+un+"&password="+pwd;
		url=HttpUtil.BASE_URL+"page/AndroidLoginServlet?"+queryString;
		System.out.println(url);
		Runnable runnable=new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Android starts to send Login request\n");
				String msg=HttpUtil.queryStringForPost(url);
				if(msg == null){
					if_login = 0; 
				}
				else if(msg.equals("notFound")){
					if_login = 1;
				}
				else{
					System.out.println(msg);
					saveUserMsg(msg);
					if_login = 2;
				}
			}
		};
		Thread thread_getgood=new Thread(runnable);
		thread_getgood.setPriority(1);
		thread_getgood.start();
		return null;
		//page/AndroidLoginServlet?
	}
	
	//���û���Ϣ���浽�����ļ�
	private void saveUserMsg(String msg)
	{
		MainApplication appState = ((MainApplication)getApplicationContext());
		System.out.println(msg);
		//�Ƽ���Ϣ
		appState.setSuggest(msg.substring(msg.indexOf("*")+1));
		//�û����
		String temp="";
		String[] msgs=msg.split("\\|");
		temp=msgs[0];
		appState.setuserno(temp);
		temp=msgs[1];
		appState.setusername(temp);
		
		//Save User Information to Local [static] Configure file
		UserConfig.id = Integer.parseInt(msgs[0]);
		UserConfig.con_usr = msgs[1];
		UserConfig.gender = Integer.parseInt(msgs[2]);
		UserConfig.con_stuno = msgs[3];
		UserConfig.con_phone = msgs[4];
		UserConfig.con_email = msgs[5];
		UserConfig.con_date = msgs[6];
		
		System.out.println(appState.getusername() + " may needs " + appState.getSuggest());
	}
	
	
	//��½����
	private void login()
	{
		String un=user_i.getText().toString();
		String pwd=pw_i.getText().toString();
		query(un,pwd);
	}
}

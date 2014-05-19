package com.example.market;


import com.market.types.FormatVerification;
import com.market.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlterUsrInfoActivity  extends Activity{
	private TextView user;
	private TextView stuid;
	private TextView sex;
	private EditText pwd;
	private EditText confirm;
	private EditText email;
	private EditText phone;
	private Button ok;
	private Button cancel;
	private int if_change;
	
	private String url;
	private String result;
	
	private String passw;
	private String mail;
	private String phonenum;
	
	private MainApplication appState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_me_info);
		
		appState = ((MainApplication)getApplicationContext());
		
		user = (TextView)findViewById(R.id.personal_account);
		stuid = (TextView)findViewById(R.id.personal_stuno);
		sex = (TextView)findViewById(R.id.personal_sex);
		pwd = (EditText)findViewById(R.id.personal_pwd);
		confirm = (EditText)findViewById(R.id.personal_confirm);
		email = (EditText)findViewById(R.id.personal_mail);
		phone = (EditText)findViewById(R.id.personal_phone);
		ok = (Button)findViewById(R.id.save);
		cancel = (Button)findViewById(R.id.reset);
		
		user.setText(appState.getClient().getName());
		stuid.setText(appState.getClient().getStuNO());
		if(appState.getClient().getGender() == 1)
			sex.setText("男");
		else if(appState.getClient().getGender() == 0)
			sex.setText("女");
		else
			sex.setText("未知");
		pwd.setText(appState.getClient().getPassword());
		confirm.setText(appState.getClient().getPassword());
		email.setText(appState.getClient().getEmail());
		phone.setText(appState.getClient().getPhone());
		
		ok.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(validate()){
					changeInfo();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(if_change == 0){
						System.out.println("if_change == 0");
						showDialog("网络异常，请稍后再试");
					}
					else if(if_change == 1){
						System.out.println("if_change == 1");
						showDialog("修改成功！");
						if_change = 0;
					}
				}
			}
		});
		
		cancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				user.setText(appState.getClient().getName());
				stuid.setText(appState.getClient().getStuNO());
				if(appState.getClient().getGender() == 1)
					sex.setText("男");
				else if(appState.getClient().getGender() == 0)
					sex.setText("女");
				else
					sex.setText("未知");
				pwd.setText(appState.getClient().getPassword());
				confirm.setText(appState.getClient().getPassword());
				email.setText(appState.getClient().getEmail());
				phone.setText(appState.getClient().getPhone());
			}
		});
	}
	
	private boolean validate()
	{
		//String ip_un = user.getText().toString();
		String ip_pwd = pwd.getText().toString();
		String ip_pwdConfirm = confirm.getText().toString();
		//String ip_stuNO = stuid.getText().toString();
		String ip_mail = email.getText().toString();
		String ip_phone = phone.getText().toString();
		if(ip_pwd.equals("") || ip_pwdConfirm.equals("") || ip_mail.equals("") || ip_phone.equals("")){
			showDialog("请将信息补充完整");
			return false;
		}
		if(!ip_pwd.equals(ip_pwdConfirm)){
			showDialog("两次输入密码不一致");
			return false;
		}
		if(!FormatVerification.verifyMail(ip_mail)){
			showDialog("请输入正确的邮箱地址");
			return false;
		}
		if(!FormatVerification.verifyPhone(ip_phone)){
			showDialog("请输入正确的手机号");
			return false;
		}
		return true;
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
	
	private void changeInfo()
	{
		int sex_user;
		String usern = user.getText().toString();
		passw = pwd.getText().toString();
		String stuno = stuid.getText().toString();
		String sex_t = sex.getText().toString();
		if(sex_t.equals("男"))
			sex_user = 1;
		else
			sex_user = 0;
		mail = email.getText().toString();
		phonenum = phone.getText().toString();

		String queryString="username="+usern+"&password="+passw+
			"&stuno="+stuno+"&sex="+sex_user+"&email="+mail+"&phone="+phonenum;
		url = HttpUtil.BASE_URL+"page/AndroidChangeInfoServlet?"+queryString;
		System.out.println(url);
		
		Runnable runnable = new Runnable(){
			@Override
			public void run(){
				System.out.println("Android starts to send change info request\n");
				result = HttpUtil.queryStringForPost(url);
				if(result == null || result.equals("sorry")){
					System.out.println("set if_change to 0");
					if_change = 0;
				}else if(result.equals("success")){
					System.out.println("set if_change to 1");
					if_change = 1;
					//TODO
					appState.getClient().setPassword(passw);
					appState.getClient().setPassword(mail);
					appState.getClient().setPassword(phonenum);
				}
			}
		};
		Thread thread_getgood = new Thread(runnable);
		thread_getgood.setPriority(1);
		thread_getgood.start();
	}
}
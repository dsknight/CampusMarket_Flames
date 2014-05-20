package com.example.market;


import com.market.util.HttpUtil;
import com.market.types.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SignupActivity extends Activity{ 
	private EditText un;
	private EditText pw;
	private EditText confirm_pw;
	private EditText mail;
	private EditText stu_no;
	private EditText phone;
	private RadioButton male,female;
	//private String sex;
	private String url;
	private String result;
	private Button ok,cancel;
	private int if_sign;
	
	private String usern;
	private String passw;
	private String stuno;
	private int sex_user;
	private String email;
	private String phonenum;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_register);
		System.out.println("register--- onCreate");
		un=(EditText)findViewById(R.id.input_UN);
		System.out.println("register--- usr");
		pw=(EditText)findViewById(R.id.input_PWD);
		System.out.println("register--- pwd");
		confirm_pw=(EditText)findViewById(R.id.Confirm_PWD);
		System.out.println("register--- repeat");
		mail=(EditText)findViewById(R.id.Mail);
		System.out.println("register--- email");
		stu_no=(EditText)findViewById(R.id.StuNo);
		System.out.println("register--- stuno");
		male=(RadioButton)findViewById(R.id.male);
		System.out.println("register--- radioMale");
		female=(RadioButton)findViewById(R.id.female);
		System.out.println("register--- radioFemale");
		phone=(EditText)findViewById(R.id.Phone);
		System.out.println("register--- phone");
		ok=(Button)findViewById(R.id.ok);
		System.out.println("register--- ok");
		cancel=(Button)findViewById(R.id.cancel);
		System.out.println("register--- cancel");
		ok.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(validate()){
					signin();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(if_sign == 0){
						System.out.println("if_sign == 0");
						showDialog("网络异常，请稍后再试");
					}
					if(if_sign == 1){
						System.out.println("if_sign == 1");
						Intent intent_signin=new Intent(SignupActivity.this,LoginActivity.class);
						startActivity(intent_signin);
						if_sign = 0;
					}else if(if_sign == 2){
						System.out.println("if_sign == 2");
						showDialog("用户名已经被使用了");
						if_sign = 0;
					}
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent_cancel=new Intent(SignupActivity.this,LoginActivity.class);
				startActivity(intent_cancel);
			}
		});
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		 //Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	private void query(ClientType user)
	{
		usern = user.getName();
		passw = user.getPassword();
		stuno = user.getStuNO();
		sex_user = user.getGender();
		email = user.getEmail();
		phonenum = user.getPhone();

		String queryString="username="+usern+"&password="+passw+
			"&stuno="+stuno+"&sex="+sex_user+"&email="+email+"&phone="+phonenum;
		url = HttpUtil.BASE_URL+"page/AndroidSignupServlet?"+queryString;
		System.out.println(url);
		
		Runnable runnable = new Runnable(){
			@Override
			public void run(){
				System.out.println("Android starts to send Login request\n");
				result = HttpUtil.queryStringForPost(url);
				//System.out.println("!!!!!!!"+result);
				if(result == null){
					System.out.println("set if_sign to 0");
					if_sign = 0;
				}
				else if(result.equals("sorry")){
					System.out.println("set if_sign to 2");
					if_sign = 2;
				}
				else{
					//TODO save User Information to Local [static] Configure file
					System.out.println("set if_sign to 1");
					if_sign = 1;
				}
			}
		};
		
		Thread thread_getgood = new Thread(runnable);
		thread_getgood.setPriority(1);
		thread_getgood.start();
	}
	
	
	private boolean validate()
	{
		String ip_un = un.getText().toString();
		String ip_pwd = pw.getText().toString();
		String ip_pwdConfirm = confirm_pw.getText().toString();
		String ip_stuNO = stu_no.getText().toString();
		String ip_mail = mail.getText().toString();
		String ip_phone = phone.getText().toString();
		if(ip_un.equals("") || ip_pwd.equals("") || ip_pwdConfirm.equals("") || ip_stuNO.equals("") ||
				ip_mail.equals("") || ip_phone.equals("")){
			showDialog("请将信息补充完整");
			return false;
		}
		if(!ip_pwd.equals(ip_pwdConfirm)){
			showDialog("两次输入密码不一致");
			return false;
		}
		if((!male.isChecked()) && (!female.isChecked())){
			showDialog("请选择您的性别");
			return false;
		}
		if(!FormatVerification.verify123ABC(ip_un)){
			showDialog("用户名只能由6-15个数字和字母组成");
			return false;
		}
		if(!FormatVerification.verifyStuNO(ip_stuNO)){
			showDialog("学号格式不正确");
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

	private void signin()
	{
		String ip_un = un.getText().toString();
		String ip_pwd = pw.getText().toString();
		String ip_stuNO = stu_no.getText().toString();
		String ip_mail = mail.getText().toString();
		String ip_phone = phone.getText().toString();
		int gender = 0;
		if(male.isChecked()){
			gender = 1;
		}
		else if(female.isChecked()){
			gender = 0;
		}
		ClientType newUser = new ClientType(ip_un,ip_pwd,gender,ip_stuNO,ip_phone,ip_mail,null);
		query(newUser);
	}
	
}

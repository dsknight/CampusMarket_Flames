package com.example.market;


import com.market.types.FormatVerification;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_me_info);
		
		user = (TextView)findViewById(R.id.personal_account);
		stuid = (TextView)findViewById(R.id.personal_stuno);
		sex = (TextView)findViewById(R.id.personal_sex);
		pwd = (EditText)findViewById(R.id.personal_pwd);
		confirm = (EditText)findViewById(R.id.personal_confirm);
		email = (EditText)findViewById(R.id.personal_mail);
		phone = (EditText)findViewById(R.id.personal_phone);
		ok = (Button)findViewById(R.id.save);
		cancel = (Button)findViewById(R.id.reset);
		
		user.setText(UserConfig.con_usr);
		stuid.setText(UserConfig.con_stuno);
		if(UserConfig.gender == 1)
			sex.setText("��");
		else if(UserConfig.gender == 0)
			sex.setText("Ů");
		else
			sex.setText("δ֪");
		pwd.setText(UserConfig.con_pwd);
		confirm.setText(UserConfig.con_pwd);
		email.setText(UserConfig.con_email);
		phone.setText(UserConfig.con_phone);
		
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
						showDialog("�����쳣�����Ժ�����");
					}
					else if(if_change == 1){
						System.out.println("if_change == 1");
						showDialog("�޸ĳɹ���");
						if_change = 0;
					}
				}
			}
		});
		
		cancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				user.setText(UserConfig.con_usr);
				stuid.setText(UserConfig.con_stuno);
				if(UserConfig.gender == 1)
					sex.setText("��");
				else if(UserConfig.gender == 0)
					sex.setText("Ů");
				else
					sex.setText("δ֪");
				pwd.setText(UserConfig.con_pwd);
				confirm.setText(UserConfig.con_pwd);
				email.setText(UserConfig.con_email);
				phone.setText(UserConfig.con_phone);
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
			showDialog("�뽫��Ϣ��������");
			return false;
		}
		if(!ip_pwd.equals(ip_pwdConfirm)){
			showDialog("�����������벻һ��");
			return false;
		}
		if(!FormatVerification.verifyMail(ip_mail)){
			showDialog("��������ȷ�������ַ");
			return false;
		}
		if(!FormatVerification.verifyPhone(ip_phone)){
			showDialog("��������ȷ���ֻ���");
			return false;
		}
		return true;
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
	
	private void changeInfo()
	{
		int sex_user;
		String usern = user.getText().toString();
		passw = pwd.getText().toString();
		String stuno = stuid.getText().toString();
		String sex_t = sex.getText().toString();
		if(sex_t.equals("��"))
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
					UserConfig.con_pwd = passw;
					UserConfig.con_email = mail;
					UserConfig.con_phone = phonenum;
				}
			}
		};
		Thread thread_getgood = new Thread(runnable);
		thread_getgood.setPriority(1);
		thread_getgood.start();
	}
}
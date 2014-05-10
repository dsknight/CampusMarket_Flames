package com.example.market;

import com.market.util.HttpUtil;

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
	private String sex;
	private Button ok,cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
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
				/*if(validate())
				{
					if(signin())
					{
						Intent intent_signin=new Intent(SignupActivity.this,signin.class);
						startActivity(intent_signin);
					}
					else
					{
						showDialog("输入错误，请重新输入！");
					}
				}*/
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
	/*@Override
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
	
	private String query(String usern,String passw,String stuno,String sex_user,String email,String phonenum)
	{
		String queryString="username="+usern+"&password="+passw+"&stuno="+stuno+"&sex="+sex_user+"&email="+email+"&phone="+phonenum;
		String url=HttpUtil.BASE_URL+"page/AndroidSignupServlet?"+queryString;
		System.out.println(url);
		return HttpUtil.queryStringForPost(url);
		//page/AndroidLoginServlet?
	}
	
	
	private boolean validate()
	{
		String un_input=un.getText().toString();
		System.out.println(un_input);
		if(un_input.equals(""))
		{
			showDialog("用户名称是必填项");
			return false;
		}
		String pwd=pw.getText().toString();
		if(pwd.equals(""))
		{
			showDialog("用户密码是必填项");
			return false;
		}
		String pwd_confirm=confirm_pw.getText().toString();
		if(pwd_confirm.equals(""))
		{
			showDialog("确认是必填项");
			return false;
		}
		String stuNO=stu_no.getText().toString();
		if(stuNO.equals(""))
		{
			showDialog("学号是必填项");
			return false;
		}
		return true;
	}
	
	private boolean signin()
	{
		String un_input=un.getText().toString();
		String pwd=pw.getText().toString();
		if(male.isChecked())
		{
			sex="1";
		}
		if(female.isChecked())
		{
			sex="0";
		}
		String result=query(un_input,pwd,stu_no.getText().toString(),sex,mail.getText().toString(),phone.getText().toString());
//		System.out.println(un);
//		System.out.println(pwd);
//		System.out.println(result);
		if(result==null||result.equals("notFound"))
		{
			return false;
		}
		else
		{
			if(result.equals("success"))
				return true;
			//saveUserMsg(result);
			else{
				showDialog(result);
			}
			System.out.println("++++++++++++++++"+result+"_______________________");
			return false;
		}
	}*/
	
}

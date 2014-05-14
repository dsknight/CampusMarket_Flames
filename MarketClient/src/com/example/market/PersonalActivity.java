package com.example.market;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class PersonalActivity  extends Activity{
	private View usr_info;
	private View shopping_info;
	private View storage_info;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_me);
		init();
	}
    
	public void init(){
		usr_info = (View)findViewById(R.id.me_usr);
		shopping_info = (View)findViewById(R.id.me_shopping);
		storage_info = (View)findViewById(R.id.me_storage);
		usr_info.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println("personalActivity--- click personal info");
				Intent intent=new Intent(PersonalActivity.this,AlterUsrInfoActivity.class);
				startActivity(intent);
			}
		});
		shopping_info.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(PersonalActivity.this,AlterUsrNeedActivity.class);
				startActivity(intent);
			}
		});
		storage_info.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"ªıº‹–≈œ¢",Toast.LENGTH_SHORT).show();
			}
		});
	}
}

package com.example.market;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsActivity extends Activity{
	@SuppressWarnings("unused")
	private ImageView myImageView;
	@SuppressWarnings("unused")
	private TextView myTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sort);
		myImageView=(ImageView)findViewById(R.id.imageView1);
		myTextView=(TextView)findViewById(R.id.textView_goods);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 //Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

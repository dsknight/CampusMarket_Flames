package com.example.market;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MyGoodsInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_goods_info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_goods_info, menu);
		return true;
	}

}

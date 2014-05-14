package com.example.market;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsActivity extends Activity{
	
	private ImageView goodsImage;
	private TextView goodsName;
	private TextView goodsDescription;
	private TextView providerName;
	private TextView providerPhone;
	private TextView providerEmail;
	private TextView goodsDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_info);
		goodsImage = (ImageView)findViewById(R.id.goods_info_pic);
		goodsName = (TextView)findViewById(R.id.goods_name);
		goodsDescription = (TextView)findViewById(R.id.goods_description);
		providerName = (TextView)findViewById(R.id.goods_provider_name);
		providerPhone = (TextView)findViewById(R.id.goods_provider_phone);
		providerEmail = (TextView)findViewById(R.id.goods_provider_email);
		goodsDate = (TextView)findViewById(R.id.goods_provider_date);
		
		/*Intent intent = this.getIntent();
		if(intent != null){
			Bitmap bitmap = intent.getParcelableExtra("bitmap");
			String gName = intent.getStringExtra("gName");
			String gDescription = intent.getStringExtra("gDescription");
			String pName = intent.getStringExtra("pName");
			String pPhone = intent.getStringExtra("pPhone");
			String pEmail = intent.getStringExtra("pEmail");
			String gDate = intent.getStringExtra("gDate");
			
	        goodsImage.setImageBitmap(bitmap);
	        goodsName.setText(gName);
	        goodsDescription.setText(gDescription);
	        providerName.setText("提供者： "+pName);
	        providerPhone.setText("手机号： "+pPhone);
	        providerEmail.setText("邮箱： "+pEmail);
	        goodsDate.setText("上架时间： "+gDate);
		}*/
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

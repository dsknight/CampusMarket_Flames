package com.example.market;

import java.io.IOException;
import java.util.Currency;

import com.market.tools.CommonMethods;
import com.market.types.ClientType;
import com.market.types.GoodsType;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsInfoActivity extends Activity{
	
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
		goodsImage.setOnTouchListener(onTouch);
		
		goodsName = (TextView)findViewById(R.id.goods_name);
		goodsDescription = (TextView)findViewById(R.id.goods_description);
		providerName = (TextView)findViewById(R.id.goods_provider_name);
		providerPhone = (TextView)findViewById(R.id.goods_provider_phone);
		providerEmail = (TextView)findViewById(R.id.goods_provider_email);
		goodsDate = (TextView)findViewById(R.id.goods_provider_date);
		
		Intent intent = this.getIntent();
		if(intent != null){
			GoodsType currGoods = null;
			ClientType goodsOwner = null;
			String[] str = ((MainApplication)getApplicationContext()).getDisplayInfo().split("!\\*G");
			try {
				currGoods = new GoodsType(str[0].substring(3));
				goodsOwner = new ClientType(str[1].substring(3, str[1].length()-3));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Bitmap bitmap = CommonMethods.StringToBitMap(currGoods.getImage());
			String gName = currGoods.getName();
			String gDescription = currGoods.getIntroduction();
			String pName = currGoods.getOwner();
			String pPhone = goodsOwner.getPhone();
			String pEmail = goodsOwner.getEmail();
			String gDate = currGoods.getDate();
			if(gName != null && gDescription != null && pName != null){
		        goodsImage.setImageBitmap(bitmap);
		        goodsName.setText(gName);
		        goodsDescription.setText(gDescription);
		        providerName.setText("提供者： "+pName);
		        providerPhone.setText("手机号： "+pPhone);
		        providerEmail.setText("邮箱： "+pEmail);
		        goodsDate.setText("上架时间： "+gDate);
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void changeLight(ImageView imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
                        brightness,// 改变亮度
                        0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
        imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
	}
	private OnTouchListener onTouch = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch(event.getAction()){
				case MotionEvent.ACTION_UP:
					changeLight((ImageView)v, 0);
	                	//onclick
					break;
		        case MotionEvent.ACTION_DOWN:
	                changeLight((ImageView)v, -50);
	                break;
		        case MotionEvent.ACTION_MOVE:
	                changeLight((ImageView)v, 0);
	                break;
		        case MotionEvent.ACTION_CANCEL:
	                changeLight((ImageView)v, 0);
	                break;
		        default:
	                break;
			
			}
			return true;
		}
	};
}

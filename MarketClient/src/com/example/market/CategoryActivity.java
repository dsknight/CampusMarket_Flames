package com.example.market;

import com.market.tools.CommonMethods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class CategoryActivity  extends Activity{
	private View digital = null;
	private View skinCare = null;
	private View book = null;
	private View cloth = null;
	private View furniture = null;
	private View eat = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_categories);
		init();
	}
	public void init(){
		digital = (View)findViewById(R.id.catergory_digital);
		skinCare = (View)findViewById(R.id.catergory_skincare);
		book = (View)findViewById(R.id.catergory_book);
		cloth = (View)findViewById(R.id.catergory_cloth);
		furniture = (View)findViewById(R.id.catergory_furniture);
		eat = (View)findViewById(R.id.catergory_eat);
		
		digital.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"数码产品",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "2 数码产品");
				startActivity(intent);
			}
		});
		skinCare.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"美容保健",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "3 美容保健");
				startActivity(intent);
			}
		});
		book.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"图书音像",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "4 图书音像");
				startActivity(intent);
			}
		});
		cloth.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"服装箱包",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "5 服装箱包");
				startActivity(intent);
			}
		});
		furniture.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"家居代步",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "6 家居代步");
				startActivity(intent);
			}
		});
		eat.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"饮食娱乐",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "7 饮食娱乐");
				startActivity(intent);
			}
		});
	}
}
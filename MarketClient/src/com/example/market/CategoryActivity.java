package com.example.market;

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
				Toast.makeText(getApplicationContext(),"�����Ʒ",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "�����Ʒ");
				startActivity(intent);
			}
		});
		skinCare.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"���ݱ���",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "���ݱ���");
				startActivity(intent);
			}
		});
		book.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"ͼ������",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "ͼ������");
				startActivity(intent);
			}
		});
		cloth.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"��װ���",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "��װ���");
				startActivity(intent);
			}
		});
		furniture.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"�ҾӴ���",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "�ҾӴ���");
				startActivity(intent);
			}
		});
		eat.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(),"��ʳ����",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(CategoryActivity.this,AllGoodsActivity.class);
				intent.putExtra("text", "��ʳ����");
				startActivity(intent);
			}
		});
	}
}
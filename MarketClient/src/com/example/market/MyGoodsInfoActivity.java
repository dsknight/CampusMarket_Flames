package com.example.market;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.market.tools.CommonMethods;
import com.market.types.GoodsType;

public class MyGoodsInfoActivity extends Activity {

	private ImageView myPic;
	private TextView gName;
	private EditText gDsp;
	private TextView gType;
	private EditText gPrice;
	private Button bDelete;
	private Button bUpdate;
	
	private String msg;
	private int index = 0;;
	
	private GoodsType myGoods;
	private Bitmap l_pic;
	private String l_name;
	private String l_dsp;
	private int l_type_int;
	private String l_type;
	private String l_price;
	private int gno;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_goods_info);
		
		Intent intent = getIntent();
		if(intent != null){
			msg = intent.getStringExtra("index");
			if(msg != null){
				index = Integer.parseInt(msg);
				myGoods = ((MainApplication)getApplicationContext()).getGoodsList().get(index);
				l_pic = StringToBitMap(myGoods.getImage());
				l_name = myGoods.getName();
				l_dsp = myGoods.getIntroduction();
				l_price = myGoods.getPrice();
				l_type_int = myGoods.getMainClass();
				gno = myGoods.getGNO();
				switch(l_type_int){
				case 2:
					l_type = "�����Ʒ";break;
				case 3:
					l_type = "���ݱ���";break;
				case 4:
					l_type = "ͼ������";break;
				case 5:
					l_type = "��װ���";break;
				case 6:
					l_type = "�ҾӴ���";break;
				case 7:
					l_type = "��ʳ����";break;
				}
				
			}else{
				showDialog("�޷������ȷ�Ĳ�����Ϣ��");
			}
		}
		
		myPic = (ImageView)findViewById(R.id.my_goods_info_imageView);
		gName = (TextView)findViewById(R.id.my_goods_info_name);
		gDsp = (EditText)findViewById(R.id.my_goods_info_description);
		gType = (TextView)findViewById(R.id.my_goods_info_type);
		gPrice = (EditText)findViewById(R.id.my_goods_info_price);
		
		bDelete = (Button)findViewById(R.id.my_goods_info_delete);
		bUpdate = (Button)findViewById(R.id.my_goods_info_update);
		
		myPic.setImageBitmap(l_pic);
        gName.setText(l_name);
        gDsp.setText(l_dsp);
        gType.setText(l_type);
        gPrice.setText(l_price);

        bUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(validate()){
					try {
						if(CommonMethods.queryForAlterGoodsInfo(gno,l_dsp,l_price)){
							((MainApplication)getApplicationContext()).getGoodsList().get(index).setIntroduction(l_dsp);
							((MainApplication)getApplicationContext()).getGoodsList().get(index).setPrice(l_price);
						}
						else{
							showDialog("������Ʒ��Ϣ���������⣬���Ժ�����~");
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				}
			}
		});
        bDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if(CommonMethods.queryForDeletingGoods(gno))
						((MainApplication)getApplicationContext()).getGoodsList().remove(index);
					else{
						showDialog("ɾ����Ʒ���������⣬���Ժ�����~");
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_goods_info, menu);
		return true;
	}
	
	private boolean validate(){
		l_dsp = gDsp.getText().toString();
		l_price = gPrice.getText().toString();
		if(l_dsp.equals("") || l_price.equals("")){
			showDialog("�뽫��Ʒ��Ϣ��������");
			return false;
		}
		return true;
	}
	
	public Bitmap StringToBitMap(String encodedString){
	     try{
	       byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
	       Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
	       return bitmap;
	     }catch(Exception e){
	       e.getMessage();
	       return null;
	     }
	}

	
	private void showDialog(String msg)
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
			
			//@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub				
			}
		});
		AlertDialog alert=builder.create();
		alert.show();
	}

}

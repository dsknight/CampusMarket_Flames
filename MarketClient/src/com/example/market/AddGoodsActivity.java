package com.example.market;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.market.types.FormatVerification;

public class AddGoodsActivity extends Activity {

	private ImageView myPic;
	private String imagePath;
	private Bitmap bitmap;
	private Bitmap sendBmp;
	
	private EditText g_name;
	private EditText g_dsp;
	private EditText g_price;
	private RadioButton t_digit;
	private RadioButton t_makeup;
	private RadioButton t_video;
	private RadioButton t_clothing;
	private RadioButton t_furniture;
	private RadioButton t_entertain;
	
	private Button upload;
	
	private String goodsName;
	private String goodsDsp;
	private String goodsPrice;
	private String goodsImage;
	private int goodsType = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_goods_add);
		
		myPic = (ImageView)findViewById(R.id.add_goods_imageView);
		myPic.setOnTouchListener(onTouch);
		
		g_name = (EditText)findViewById(R.id.add_goods_name);
		g_dsp = (EditText)findViewById(R.id.add_goods_description);
		g_price = (EditText)findViewById(R.id.add_goods_price);
		t_digit = (RadioButton)findViewById(R.id.add_digital);
		t_makeup = (RadioButton)findViewById(R.id.add_makeup);
		t_video = (RadioButton)findViewById(R.id.add_video);
		t_clothing = (RadioButton)findViewById(R.id.add_clothes);
		t_furniture = (RadioButton)findViewById(R.id.add_bicycle);
		t_entertain = (RadioButton)findViewById(R.id.add_entertain);
		
		upload = (Button)findViewById(R.id.add_new_goods_button);
		upload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(validate()){
					//TODO send request
				}
			}
		});
	}

	
	private boolean validate(){
		goodsName = g_name.getText().toString();
		goodsDsp = g_dsp.getText().toString();
		goodsPrice = g_price.getText().toString();
		if(sendBmp != null)
			goodsImage = sendBmp.toString();
		else
			goodsImage = "null";
		if(goodsName.equals("") || goodsDsp.equals("") || goodsPrice.equals("")){
			showDialog("�뽫��Ʒ��Ϣ��������");
			return false;
		}
		if(FormatVerification.verifyStar(goodsName)){
			showDialog("��Ʒ���в��ܰ���'*'");
			return false;
		}
		if(t_digit.isChecked()){
			goodsType = 2;
		}else if(t_makeup.isChecked()){
			goodsType = 3;
		}else if(t_video.isChecked()){
			goodsType = 4;
		}else if(t_clothing.isChecked()){
			goodsType = 5;
		}else if(t_furniture.isChecked()){
			goodsType = 6;
		}else if(t_entertain.isChecked()){
			goodsType = 7;
		}
		if(goodsType == 0){
			showDialog("��ѡ����Ʒ�ķ���");
			return false;
		}
		return true;
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_goods, menu);
		return true;
	}
	
	
	public void changeLight(ImageView imageView, int brightness) {
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
                        brightness,// �ı�����
                        0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
        imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
	}
	
	private OnTouchListener onTouch = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch(event.getAction()){
				case MotionEvent.ACTION_UP:
					Intent intent = new Intent();  
					/* ����Pictures����Type�趨Ϊimage */  
					intent.setType("image/*");  
					/* ʹ��Intent.ACTION_GET_CONTENT���Action */  
					intent.setAction(Intent.ACTION_GET_CONTENT);   
					/* ȡ����Ƭ�󷵻ر����� */  
					startActivityForResult(intent, 1);
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
	
	//��ȡѡ�����Ƭ
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//System.out.println("requestCode==="+requestCode);
		if(1 == requestCode && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();  
            Log.i("uri", uri.toString());
            if(null != uri && !"".equals(uri)){
            ContentResolver cr = this.getContentResolver();
                try {
    	            String[] filePathColumn = {MediaStore.Images.Media.DATA};
    				Cursor cursor = getContentResolver().query(uri,filePathColumn, null, null, null);
    	            //Cursor cursor =  managedQuery(uri, filePathColumn, null, null,null);
    				cursor.moveToFirst();//���α��Ƶ���һλ
    				int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    				
    				imagePath = cursor.getString(columnIndex);//��ȡͼƬ·��
    				showDialog(imagePath);
    				//Bitmap bitmap = BitmapFactory.decodeStream(inStream);
    				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
    				sendBmp = comp(bitmap);  				
    				myPic.setImageBitmap(sendBmp);
    				
    				cursor.close();//�ر��α�
					bitmap.recycle();
					System.gc();
    				//inStream.close();
    			} catch (Exception e) {
    				Toast.makeText(getApplicationContext(), "�ڴ����", Toast.LENGTH_LONG).show();
    				e.printStackTrace();
    			}
            }
		}else {
			Toast.makeText(getApplicationContext(), "δѡ��ͼƬ", Toast.LENGTH_LONG).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private Bitmap compressImage(Bitmap image) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
      //����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;  
      //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
        while ( baos.toByteArray().length / 1024>100) {           
            baos.reset();
          //����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }  
      //��ѹ���������baos��ŵ�ByteArrayInputStream��  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
      //��ByteArrayInputStream��������ͼƬ  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;  
    }

	private Bitmap comp(Bitmap image) {  
	      
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
	    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	  //�ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���    
	    if( baos.toByteArray().length / 1024>1024) {
	    //����baos�����baos  
	    	baos.reset();
	    //����ѹ��50%����ѹ��������ݴ�ŵ�baos��  
	        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
	    }  
	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	  //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
	    newOpts.inJustDecodeBounds = true;  
	    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    newOpts.inJustDecodeBounds = false;  
	    int w = newOpts.outWidth;  
	    int h = newOpts.outHeight;  
	  //���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
	  //�������ø߶�Ϊ800f  
	    float hh = 800f;
	  //�������ÿ��Ϊ480f 
	    float ww = 480f; 
	  //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
	  //be=1��ʾ������  
	    int be = 1;
	  //�����ȴ�Ļ����ݿ�ȹ̶���С����  
	    if (w > h && w > ww) {
	        be = (int) (newOpts.outWidth / ww);  
	  //����߶ȸߵĻ����ݿ�ȹ̶���С����  
	    } else if (w < h && h > hh) {
	        be = (int) (newOpts.outHeight / hh);  
	    }  
	    if (be <= 0)  
	        be = 1;  
	  //�������ű���  
	    newOpts.inSampleSize = be;
	  //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
	    isBm = new ByteArrayInputStream(baos.toByteArray());  
	    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	  //ѹ���ñ�����С���ٽ�������ѹ��  
	    return compressImage(bitmap);
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

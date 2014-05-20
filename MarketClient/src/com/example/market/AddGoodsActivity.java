package com.example.market;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

import com.market.tools.CommonMethods;
import com.market.types.FormatVerification;
import com.market.types.GoodsType;

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
				if(validate()){
					String goodsOwner = ((MainApplication)getApplicationContext()).getClient().getName();
					Map<String, String> param = new HashMap<String, String>();
					param.put("goodsName", goodsName);
					param.put("goodsOwner", goodsOwner);
					param.put("goodsPrice", goodsPrice);
					param.put("goodsImage", goodsImage);
					param.put("goodsClass", goodsType+"");
					param.put("goodsIntroduction", goodsDsp);
					param.put("goodsProperty", "0");
					try {
						String tmp = CommonMethods.queryForGoodsUpload(param);
						if(!tmp.equals("#")){
							//showDialog("上传成功！");
							Toast.makeText(getApplicationContext(),"上传成功！",Toast.LENGTH_SHORT).show();
							GoodsType newGoods = new GoodsType();
							newGoods.setName(goodsName);
							newGoods.setOwner(goodsOwner);
							newGoods.setPrice(goodsPrice);
							newGoods.setImage(goodsImage);
							newGoods.setIntroduction(goodsDsp);
							java.text.SimpleDateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							newGoods.setDate(format1.format(new Date()));
							newGoods.setGNO(Integer.parseInt(tmp.split(" ")[0].substring(1)));
							newGoods.setMainClass(goodsType);
							((MainApplication)getApplicationContext()).getGoodsList().add(newGoods);
							Intent intent = new Intent(AddGoodsActivity.this, MyGoodsActivity.class);
							startActivity(intent);
						}
						else
							showDialog("有点小问题...请稍后再试~");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
				}
			}
		});
	}

	
	private boolean validate(){
		goodsName = g_name.getText().toString();
		goodsDsp = g_dsp.getText().toString();
		goodsPrice = g_price.getText().toString();
		if(sendBmp != null)
			goodsImage = CommonMethods.BitMapToString(sendBmp);
		else
			goodsImage = "null";
		if(goodsName.equals("") || goodsDsp.equals("") || goodsPrice.equals("")){
			showDialog("请将商品信息补充完整");
			return false;
		}
		if(FormatVerification.verifyStar(goodsName)){
			showDialog("商品名中不能包含'*'");
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
			showDialog("请选择商品的分类");
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
					Intent intent = new Intent();  
					/* 开启Pictures画面Type设定为image */  
					intent.setType("image/*");  
					/* 使用Intent.ACTION_GET_CONTENT这个Action */  
					intent.setAction(Intent.ACTION_GET_CONTENT);   
					/* 取得相片后返回本画面 */  
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
	
	//获取选择的照片
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
    				cursor.moveToFirst();//将游标移到第一位
    				int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
    				
    				imagePath = cursor.getString(columnIndex);//获取图片路径
    				showDialog(imagePath);
    				//Bitmap bitmap = BitmapFactory.decodeStream(inStream);
    				bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
    				sendBmp = comp(bitmap);  				
    				myPic.setImageBitmap(sendBmp);
    				
    				cursor.close();//关闭游标
					bitmap.recycle();
					System.gc();
    				//inStream.close();
    			} catch (Exception e) {
    				Toast.makeText(getApplicationContext(), "内存溢出", Toast.LENGTH_LONG).show();
    				e.printStackTrace();
    			}
            }
		}else {
			Toast.makeText(getApplicationContext(), "未选中图片", Toast.LENGTH_LONG).show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private Bitmap compressImage(Bitmap image) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
      //质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;  
      //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        while ( baos.toByteArray().length / 1024>100) {           
            baos.reset();
          //这里压缩options%，把压缩后的数据存放到baos中  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }  
      //把压缩后的数据baos存放到ByteArrayInputStream中  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
      //把ByteArrayInputStream数据生成图片  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;  
    }

	private Bitmap comp(Bitmap image) {  
	      
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
	    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	  //判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
	    if( baos.toByteArray().length / 1024>1024) {
	    //重置baos即清空baos  
	    	baos.reset();
	    //这里压缩50%，把压缩后的数据存放到baos中  
	        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);
	    }  
	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	  //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
	    newOpts.inJustDecodeBounds = true;  
	    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    newOpts.inJustDecodeBounds = false;  
	    int w = newOpts.outWidth;  
	    int h = newOpts.outHeight;  
	  //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
	  //这里设置高度为800f  
	    float hh = 600f;
	  //这里设置宽度为480f 
	    float ww = 360f; 
	  //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
	  //be=1表示不缩放  
	    int be = 1;
	  //如果宽度大的话根据宽度固定大小缩放  
	    if (w > h && w > ww) {
	        be = (int) (newOpts.outWidth / ww);  
	  //如果高度高的话根据宽度固定大小缩放  
	    } else if (w < h && h > hh) {
	        be = (int) (newOpts.outHeight / hh);  
	    }  
	    if (be <= 0)  
	        be = 1;  
	  //设置缩放比例  
	    newOpts.inSampleSize = be;
	  //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
	    isBm = new ByteArrayInputStream(baos.toByteArray());  
	    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	  //压缩好比例大小后再进行质量压缩  
	    return compressImage(bitmap);
	}  
	
	
	private void showDialog(String msg)
	{
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("确定",new DialogInterface.OnClickListener() {
			
			//@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub				
			}
		});
		AlertDialog alert=builder.create();
		alert.show();
	}
}

package com.example.market;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;
import android.view.*;
import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.*;
@SuppressWarnings("deprecation")
public class MainPageActivity extends TabActivity  {
	private TabHost tabHost;
	private RadioButton main_home,main_category,main_car,main_me;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_bottom);
		initTab();
		init();
	}
	
	public void initTab(){
		tabHost = getTabHost();
		tabHost.addTab(tabHost.newTabSpec("home").setIndicator("home")
				.setContent(new Intent(this, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
		tabHost.addTab(tabHost.newTabSpec("car").setIndicator("car")
				.setContent(new Intent(this, AllGoodsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
		tabHost.addTab(tabHost.newTabSpec("category").setIndicator("category")
				.setContent(new Intent(this, CategoryActivity.class)));
		tabHost.addTab(tabHost.newTabSpec("personal").setIndicator("personal")
				.setContent(new Intent(this, PersonalActivity.class)));
	}
	
	public void init(){
		main_home = (RadioButton)findViewById(R.id.tray_home);
		main_car = (RadioButton)findViewById(R.id.tray_ease);
		main_category = (RadioButton)findViewById(R.id.tray_category);
		main_me = (RadioButton)findViewById(R.id.tray_me);
		main_home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tabHost.setCurrentTabByTag("home");
			}
		});
		main_car.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tabHost.setCurrentTabByTag("car");
			}
		});
		main_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tabHost.setCurrentTabByTag("category");
			}
		});
		main_me.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tabHost.setCurrentTabByTag("personal");
			}
		});
	}
	
	public boolean dispatchKeyEvent(KeyEvent event){
		if(event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK){
			new AlertDialog.Builder(this).setCancelable(false)
			.setTitle("��ȷ��Ҫ�˳���?").
			setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){
					finish();
					}})
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int which){}}).show();
		}
		return super.dispatchKeyEvent(event);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*private List<String> strs_home=new ArrayList<String>();
	private List<String> strs1=new ArrayList<String>();
	private List<String> strs2_1=new ArrayList<String>();
	private List<String> strs2_2=new ArrayList<String>();
	private List<String> strs2_3=new ArrayList<String>();
	private List<String> strs2_4=new ArrayList<String>();
	private List<String> strs2_5=new ArrayList<String>();
	private List<String> strs2_6=new ArrayList<String>();
	private boolean isfinished;
	private Button logout;
	private ListView lv2;
	private String url;
	private int list1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TabHost th=getTabHost();
		isfinished=false;
		list1=0;
		LayoutInflater.from(this).inflate(R.layout.activity_main,th.getTabContentView(),true);
		th.addTab(th.newTabSpec("home").setIndicator("��ҳ").setContent(R.id.LinearLayout_home));
		th.addTab(th.newTabSpec("sort").setIndicator("����").setContent(R.id.LinearLayout_sort));
		th.addTab(th.newTabSpec("detail").setIndicator("��������").setContent(R.id.LinearLayout_detail));
		
		logout=(Button)findViewById(R.id.button1);
		logout.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent_login=new Intent(MainPageActivity.this,LoginActivity.class);
				startActivity(intent_login);
			}
		});
		
		strs_home.add("С����ڿ�");
		strs_home.add("�ڶ�����Ŀ");
		ListView lv_home=(ListView)findViewById(id.listView_home);
		lv_home.setAdapter(new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, strs_home));
		
		lv_home.setOnItemClickListener(new OnItemClickListener()
		{
			 @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	                    long arg3) {
	                // TODO Auto-generated method stub
	                if(strs_home.get(arg2).equals("�ڶ�����Ŀ"))
	                {
	                    Intent intent_home = new Intent(MainPageActivity.this,GoodsActivity.class);
	                    startActivity(intent_home);
	                }
	            }

		}
		);
		lv2=(ListView)findViewById(id.listView2);
		strs1.add("�����Ʒ");
		strs1.add("���ݱ���");
		strs1.add("ͼ������");
		strs1.add("��װ���");
		strs1.add("�Ҿߴ���");
		strs1.add("��ʳ����");
		strs1.add("��ʳ����");
		strs1.add("��ʳ����");
		strs1.add("��ʳ����");
		strs1.add("��ʳ����");
		strs1.add("��ʳ����");
		
		strs2_1.add("�ֻ������");
		strs2_1.add("����");
		strs2_1.add("����");
		strs2_1.add("����");
		
		strs2_2.add("����");
		strs2_2.add("��ˮ");
		strs2_2.add("����");
		strs2_2.add("����");
		
		strs2_3.add("��");
		strs2_3.add("��Ƭ");
		strs2_3.add("����");

		strs2_4.add("Ьñ");
		strs2_4.add("�·�");
		strs2_4.add("��");
		strs2_4.add("����");
		
		strs2_5.add("����");
		strs2_5.add("��������");
		strs2_5.add("�ҾӺĲ�");
		strs2_5.add("����");
		
		strs2_6.add("��Ʊ");
		strs2_6.add("�Żݿ�");
		strs2_6.add("��ʳ");
		strs2_6.add("����");
		
		
		
		ListView lv1=(ListView)findViewById(id.listView1);
		lv1.setAdapter(new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, strs1));
		
		lv2.setAdapter(new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, strs2_1));
		
		lv1.setOnItemClickListener(new OnItemClickListener()
		{
			 @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	                    long arg3) {
				 	list1=arg2;
	                // TODO Auto-generated method stub
	                if(strs1.get(arg2).equals("�����Ʒ"))
	                {
	            		lv2.setAdapter(new ArrayAdapter<String>(MainPageActivity.this,
	            		android.R.layout.simple_list_item_1, strs2_1));
	                }
	                if(strs1.get(arg2).equals("���ݱ���"))
	                {
	            		lv2.setAdapter(new ArrayAdapter<String>(MainPageActivity.this,
	            		android.R.layout.simple_list_item_1, strs2_2));
	                }
	                if(strs1.get(arg2).equals("ͼ������"))
	                {
	            		lv2.setAdapter(new ArrayAdapter<String>(MainPageActivity.this,
	            		android.R.layout.simple_list_item_1, strs2_3));
	                }
	                if(strs1.get(arg2).equals("��װ���"))
	                {
	            		lv2.setAdapter(new ArrayAdapter<String>(MainPageActivity.this,
	            		android.R.layout.simple_list_item_1, strs2_4));
	                }
	                if(strs1.get(arg2).equals("�Ҿߴ���"))
	                {
	            		lv2.setAdapter(new ArrayAdapter<String>(MainPageActivity.this,
	            		android.R.layout.simple_list_item_1, strs2_5));
	                }
	                if(strs1.get(arg2).equals("��ʳ����"))
	                {
	            		lv2.setAdapter(new ArrayAdapter<String>(MainPageActivity.this,
	            		android.R.layout.simple_list_item_1, strs2_6));
	                }
	            }

		}
		);
		
		
		
		
		ListView lv2=(ListView)findViewById(id.listView2);
		lv2.setAdapter(new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, strs2_1));
		lv2.setOnItemClickListener(new OnItemClickListener()
		{
			 @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	                    long arg3) {
	                // TODO Auto-generated method stub
				 String temp1=""+list1;
				 String temp2=""+arg2;
				 query(temp1,temp2);
//				 saveGoodsMsg(str);
//				 System.out.println(str);
				 Intent to_goodlist = new Intent(MainPageActivity.this,GoodsListActivity.class);
                 startActivity(to_goodlist);
//				 str =  EntityUtils.toString(str.getEntity(),"UTF-8");
//				 System.out.println(str);
	            }

		}
		);
		
	}
//	public View createTabContent(String tag)
//	{
////		ListView lv=new ListView(this);
////		List<String> list=new ArrayList<String>();
//		//list.add(tag);
////		if(tag.equals("home"))
////		{
//			ListView lv=new ListView(this);
//			List<String> list=new ArrayList<String>();
//			list.add("С����ڿ�");
//			list.add("С������");
//			ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,list);
//			lv.setAdapter(adapter);
//			return lv;
////		}
////		ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,list);
//////		lv.setAdapter(adapter);
////		return lv;
//	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 //Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onBackPressed()
	{
		if(isfinished)
		{
			finish();
		}else
		{
			Toast.makeText(this, "�ٵ��һ���˳�����", Toast.LENGTH_SHORT).show();
			new Thread()
			{
				public void run()
				{
					isfinished=true;
					try
					{
						Thread.sleep(2000);
						isfinished=false;
					}catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				};
			}.start();
		}
	}
	private String query(String un,String pwd)
	{
		String queryString="list1="+un+"&list2="+pwd;
		url=HttpUtil.BASE_URL+"page/AndroidSearchGoodsServlet?"+queryString;
		System.out.println(url);
		Runnable runnable=new Runnable()
		{
			@Override
			public void run()
			{
				//TODO:http request
				System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
				String msg=HttpUtil.queryStringForPost(url);
				if(!(msg==null||msg.equals("notFound")))
				saveGoodsMsg(msg);
			}
		};
		Thread thread_getgood=new Thread(runnable);
		thread_getgood.setPriority(1);
		thread_getgood.start();
		return "";
		//page/AndroidLoginServlet?
	}
	private void saveGoodsMsg(String msg)
	{
		System.out.println("$$$$$$$$$$$$$$$$$$$"+msg);
		String[] msgs1=msg.split("\\$");
		MainApplication appState = ((MainApplication)getApplicationContext());
		
		System.out.println("++++++++++_______________"+msgs1[0]);
		String[][] msgs2=new String[msgs1.length][8];
		for(int i=0;i<msgs1.length;i++)
		{
			msgs2[i]=msgs1[i].split("\\|");
			System.out.println(msgs2[i][0]);
			System.out.println(msgs2[i][1]);
			System.out.println(msgs2[i][2]);
			System.out.println(msgs2[i][3]);
			System.out.println(msgs2[i][4]);
			System.out.println(msgs2[i][5]);
			System.out.println(msgs2[i][6]);
			System.out.println(msgs2[i][7]);
			
			appState.addgoodno(msgs2[i][0]);
			
			appState.addgoodname(msgs2[i][1]);
			
			appState.addownerno(msgs2[i][2]);
			
			appState.addprice(msgs2[i][3]);
			
			appState.addinfo(msgs2[i][6]);
			
			appState.adddate(msgs2[i][7]);
		}
	}*/

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

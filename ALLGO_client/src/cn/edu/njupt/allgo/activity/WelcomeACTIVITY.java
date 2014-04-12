package cn.edu.njupt.allgo.activity;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.adapter.WelcomeViewPagerAdapter;
import cn.edu.njupt.allgo.fragment.LoginDialogFRAGMENT;
import cn.edu.njupt.allgo.logic.LoginLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logicImpl.LoginLogicImpl;
import cn.edu.njupt.allgo.vo.EventVo;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WelcomeACTIVITY extends BaseActivity implements OnClickListener,OnPageChangeListener , RefreshInterFace{

	//记录当前选中位置
    private int currentIndex;
	//底部小点的图片
    private ImageView[] points;
	//引导图片资源
    private static final int[] pics = {R.drawable.guide1,R.drawable.guide2};
	private ArrayList<View> views;
	private ViewPager viewPager;
	private WelcomeViewPagerAdapter vpAdapter;
	private Button button_welcome_login;
	private Button button_welcome_register;
	private ProgressDialog progressDialog;
	private LoginLogic loginLogic ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		Log.i("Activity"," onCreate() ==> WelcomeACTIVITY");
		loginLogic = new LoginLogicImpl(this , this);
		initView();
	}

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		Log.i("Activity"," onResume() ==> WelcomeACTIVITY");
		Intent intent = getIntent();
		if(intent.getStringExtra("login") != null){
		showLogin(intent.getStringExtra("login"));
		}
	}

	private void showLogin(String result){
		LoginDialogFRAGMENT newFragment = LoginDialogFRAGMENT.newInstance("login");
        newFragment.show(getSupportFragmentManager(), "dialog");
        newFragment.setName(result);
	}
	/**
	 * 初始化组件
	 */
	private void initView() {
		//实例化ArrayList对象
				views = new ArrayList<View>();
				
				//实例化ViewPager
				viewPager = (ViewPager) findViewById(R.id.viewpager_welcome);
				
				//实例化ViewPager适配器
				vpAdapter = new WelcomeViewPagerAdapter(views);
				
				button_welcome_login = (Button)findViewById(R.id.button_welcome_login);
				button_welcome_register = (Button)findViewById(R.id.button_welcome_register);
				
				button_welcome_login.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						 
						showLoginDialog();
					}
				});
				
				button_welcome_register.setOnClickListener(new View.OnClickListener() {
									
									@Override
									public void onClick(View v) {
										 
										showRegisterDialog();
									}
								});
				initData();	
					}
	
	/**
	 * 初始化数据
	 */
	private void initData(){
		//定义一个布局并设置参数
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                														  LinearLayout.LayoutParams.MATCH_PARENT);

        //初始化引导图片列表
			ImageView iv0 = new ImageView(this);
			iv0.setScaleType(ScaleType.FIT_CENTER);
			iv0.setLayoutParams(mParams);
            iv0.setImageResource(pics[0]);
            views.add(iv0);
            ImageView iv1 = new ImageView(this);
            iv1.setScaleType(ScaleType.FIT_CENTER);
            iv1.setLayoutParams(mParams);
            iv1.setImageResource(pics[1]); 
            views.add(iv1);
        
        //设置数据
        viewPager.setAdapter(vpAdapter);
        //设置监听
        viewPager.setOnPageChangeListener(this);
        
        //初始化底部小点
        initPoint();
	}
	
	/**
	 * 初始化底部小点
	 */
	private void initPoint(){
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayout_welcome_point);       
		
        points = new ImageView[pics.length];

        //循环取得小点图片
        for (int i = 0; i < pics.length; i++) {
        	//得到一个LinearLayout下面的每一个子元素
        	points[i] = (ImageView) linearLayout.getChildAt(i);
        	//默认都设为灰色
        	points[i].setEnabled(true);
        	//给每个小点设置监听
        	points[i].setOnClickListener(this);
        	//设置位置tag，方便取出与当前位置对应
        	points[i].setTag(i);
        }
        
        //设置当面默认的位置
        currentIndex = 0;
        //设置为白色，即选中状态
        points[currentIndex].setEnabled(false);
	}
	
	/**
	 * 当滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}
	
	/**
	 * 当当前页面被滑动时调用
	 */

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}
	
	/**
	 * 当新的页面被选中时调用
	 */

	@Override
	public void onPageSelected(int position) {
		//设置底部小点选中状态
        setCurDot(position);
	}

	/**
	 * 通过点击事件来切换当前的页面
	 */
	@Override
	public void onClick(View v) {
		 int position = (Integer)v.getTag();
         setCurView(position);
         setCurDot(position);		
	}

	/**
	 * 设置当前页面的位置
	 */
	private void setCurView(int position){
         if (position < 0 || position >= pics.length) {
             return;
         }
         viewPager.setCurrentItem(position);
     }

     /**
     * 设置当前的小点的位置
     */
    private void setCurDot(int positon){
         if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
             return;
         }
         points[positon].setEnabled(false);
         points[currentIndex].setEnabled(true);

         currentIndex = positon;
     }
    
    private void showLoginDialog(){
    	LoginDialogFRAGMENT newFragment = LoginDialogFRAGMENT.newInstance("login");
        newFragment.show(getSupportFragmentManager(), "dialog");
    	
    }
    
	private void showRegisterDialog(){
	    	Intent intent = new Intent(this , RegisterACTIVITY.class);
	    	startActivity(intent);
	    }
    
	public void doLoginClick(String name , String password) {
        // 处理登录事件
        Log.i("WelcomeACTIVITY", "doLogin Click! ==>" + name + password);
        showProgressDialog("正在登录");
        loginLogic.login(name , password) ;  
	}

	
	/**
	 * 显示提示框
	 */
	private void showProgressDialog(String title) {
		if ((!isFinishing()) && (this.progressDialog == null)) {
			this.progressDialog = new ProgressDialog(this);
		}
			this.progressDialog.setTitle(title);
			this.progressDialog.setMessage("请稍等...");
			progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                   "取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    /*取消登录 */
                	loginLogic.cancelLogin();
                }
            });
			this.progressDialog.show();
	}
	
	/**
	 * 关闭提示框
	 */
	protected void closeProgressDialog() {
		if (this.progressDialog != null)
			this.progressDialog.dismiss();
	}

	@Override
	protected void onDestroy() {
		closeProgressDialog();
		super.onDestroy();
	}

	
	@Override
	public void refresh(Object result, int kind) {
		switch(kind){
			case 1 :
				Boolean arg0 = (Boolean)result ;
				if(arg0){
		        	Intent intent = new Intent(WelcomeACTIVITY.this,HomeACTIVITY.class);
		        	startActivity(intent);
		        	finish();
				}
		        break;
		    case -1 :
		    	String arg1 = (String) result ;
		    	Toast.makeText(this,arg1, Toast.LENGTH_SHORT).show();
		    	closeProgressDialog();
		    	break;
		    case 2 :		
		    	showLoginDialog();
		    	break;
		    }
		}
	
	//此方法可保证从resume()状态传入intent
	@Override
	protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// must store the new intent unless getIntent() will
                          // return the old one
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 01, Menu.FIRST, "设置服务器地址").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		 case 01:
			 loginLogic.setURL();
			 break;
		 }
		return true;
	}

	
}

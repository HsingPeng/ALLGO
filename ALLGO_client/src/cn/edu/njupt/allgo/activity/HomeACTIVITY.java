package cn.edu.njupt.allgo.activity;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.fragment.AllEventFRAGMENT;
import cn.edu.njupt.allgo.fragment.BaseFRAGMENT;
import cn.edu.njupt.allgo.fragment.ContactFRAGMENT;
import cn.edu.njupt.allgo.fragment.MyHomePageFRAGMENT;
import cn.edu.njupt.allgo.fragment.UnreadFRAGMENT;
import cn.edu.njupt.allgo.service.PullService;
import cn.edu.njupt.allgo.vo.EventVo;
import android.app.ActionBar;
import android.app.NotificationManager;
import android.support.v4.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat; 
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HomeACTIVITY extends FragmentActivity{
	
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private ActionBar mActionBar;
	private HomeReceiver homeReceiver = new HomeReceiver();;
	
	private BaseFRAGMENT currentFragment = null ;
	private AllEventFRAGMENT alleventFragment ;
	
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mHomeTitles;
	private int position = -1;
	private ContactFRAGMENT contactFragment;
	private long mExitTime;
	private String TAG = "HomeActivity";
	private MenuItem actionItem_unread;

	//广播接收机
	public class HomeReceiver extends BroadcastReceiver
	{
	       @Override  
	       public void onReceive(Context context, Intent intent)
	       { 
	    	
		    	int action = intent.getIntExtra("action", -1);
		    	Log.i(TAG, "cn.edu.njupt.allgo.HomeACTIVITY" + action);
		    	switch(action){
		    	case 0:			//关闭activiy信号
		    		finish();
		    		break;
		    	case 1:			//登录过期,转到LogOffACTIVITY
		    		Toast.makeText(HomeACTIVITY.this, "登录过期", Toast.LENGTH_SHORT).show();
    				Intent intent1 = new Intent(HomeACTIVITY.this,LogOffACTIVITY.class);
    				intent1.putExtra("action", 1);
    				startActivity(intent1);
		    		break;
		    	case 2:
		    		Log.i(TAG,"unread==>case 2");
		    		actionItem_unread.setIcon(R.drawable.ic_action_email_full);
		    		break;
		    	}
	       }  
	   }  
	
	@Override
	protected void onDestroy() {
		unregisterReceiver(homeReceiver);
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		homeReceiver = new HomeReceiver();
		IntentFilter intentFilter = new IntentFilter("cn.edu.njupt.allgo.HomeACTIVITY");  
		registerReceiver(homeReceiver, intentFilter);
		
		Intent intent = new Intent(this,PullService.class);
		intent.putExtra("action", 1);
		startService(intent);
		
		setContentView(R.layout.activity_home);
		mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
		findViewById(savedInstanceState);
		initView(); 
		if(savedInstanceState == null) {
			selectItem(1);
		}
        Log.i(TAG,"==> HomeCTIVITY初始化完毕");
	}
	
	private void initView() {
		// TODO initView
		
		//actionbar和viewpager的初始化
		mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        //drawer 的初始化
        creatdrawer();
	}

	private void findViewById(Bundle savedInstanceState) {

		// Check that the activity is using the layout version with  
        // the fragment_container FrameLayout  
        if (findViewById(R.id.fragment_container) != null) {
  
            // However, if we're being restored from a previous state,  
            // then we don't need to do anything and should return or else  
            // we could end up with overlapping fragments.  
            if (savedInstanceState != null) {
                return;
            }
        }
	}
	
	private boolean isActivity(int position){
		//TODO intent需要修改的地方
		boolean flag = true ;
		if(position != 4 && position != 2 && position != 0){
			flag = false;
		}
		return flag;
	}
	
	//此方法用于改变主页显示的内容，以及actionbar的tab
    private void pagerchange(int position) {
    	//公共操作
    	if(!isActivity(position)) {
    	this.position = position ;  //改变FLAG
		mActionBar.removeAllTabs();//清空 ActionBar的所有TAB
    	}
	    
	    /**
	     * 我的主页0;所有活动1;我的消息2； 联系人3；设置4；退出5
	     */
	    switch(position){
	    case 0:  //载入introFragment;lifehistoryFragment
	    	// Create an instance of ExampleFragment  
	    	/*if(myhomepageFragment == null) {
	    		myhomepageFragment = new MyHomePageFRAGMENT();
	    	}
	    	switchContent(myhomepageFragment);*/
	    	Intent intent=new Intent();
            intent.setClass(HomeACTIVITY.this,IntroACTIVITY.class);  
            startActivity(intent);
	    	break;
	    case 1:   //载入commoneventFragment;friendseventFragment
	    	if(alleventFragment == null) {
	    		alleventFragment = new AllEventFRAGMENT();
	    	}
	    	switchContent(alleventFragment);
	    	break;
	    case 2:	   //载入unreadACTIVITY
	    	openUnread();
	    	break;
	    case 3:   //载入contactlistFragment;chatlistFragment
	    	if(contactFragment == null) {
	    		contactFragment = new ContactFRAGMENT();
	    	}
	    	switchContent(contactFragment);
	    	break;
	    case 4:   //载入settingACTIVITY
	    	Intent intent1=new Intent();
            intent1.setClass(HomeACTIVITY.this,SettingACTIVITY.class);  
            startActivity(intent1);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	    	break;
	    case 5:   //退出
	    	this.finish();
	    	break;
	    default:Log.e(TAG,"点击了未知位置==>" + position);
	    }
	}
	

	//用于切换父级Fragment
	public void switchContent(BaseFRAGMENT to) {
        if(currentFragment != null) {
    		currentFragment.onPause();
    		currentFragment.onStop();
        	if (currentFragment != to) {
	        	 FragmentTransaction transaction = getSupportFragmentManager().beginTransaction() ;
	             if (!to.isAdded()) {    // 先判断是否被add过
	            	 to.setBarFlag(true);
	                 transaction.hide(currentFragment).add(R.id.fragment_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
	             } else {
	            	 to.initActionBar();
	            	 to.onStart();
	            	 to.onResume();
	                 transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
	             }
	        }
        }else {
        	to.setBarFlag(true);
        	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction() ;
        	transaction.add(R.id.fragment_container, to).commit();
        }
        currentFragment = to;
        }

	
	public PullToRefreshAttacher getPullToRefreshAttacher() {
	        return mPullToRefreshAttacher;
	    }
	
	/*@Override
	protected void onSaveInstanceState (Bundle outState) {
		Log.i("debug","==onSaveInstanceState==>");
		OnSaveData onSaveDate = new OnSaveData(currentFragment) ;
		outState.putSerializable("onSaveDate", onSaveDate);
		outState.putInt("position", this.position);
	}
	
	@Override
	protected void onRestoreInstanceState (Bundle savedInstanceState) {
		Log.i("debug","==onRestoreInstanceState==>");
		OnSaveData onSaveDate = (OnSaveData) savedInstanceState.getSerializable("onSaveDate") ;
		this.currentFragment = onSaveDate.getFragment() ;
		this.currentFragment.setBarFlag(true);
		int arg0 = savedInstanceState.getInt("position");
		selectItem(arg0);
	}*/
	
	/*************************************生成抽屉方法开始***************************************/
	/**
	 * drawer 的初始化方法
	 */
	private void creatdrawer() {
		// TODO 生成抽屉方法开始
        mTitle = mDrawerTitle = getTitle();
        mHomeTitles = getResources().getStringArray(R.array.home_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,mHomeTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // enable ActionBar app icon to behave as action to toggle nav drawer
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
            	mActionBar.setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu() 此时关闭抽屉
            }

            public void onDrawerOpened(View drawerView) {
                mActionBar.setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()  此时打开抽屉
                View v=mDrawerList.getChildAt(position);
                v.setBackgroundColor(Color.GRAY);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    
	}


	/* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            if(!isActivity(position)){
	            	for(int i=0;i<parent.getCount();i++){
		            View v=parent.getChildAt(i);
		            if (position == i) {
		                v.setBackgroundColor(Color.GRAY);
		            } else {
		                v.setBackgroundColor(Color.TRANSPARENT);
		            }
		        }
            }
            }
        }
        	
    //点击抽屉选项的操作
    private void selectItem(int position) {
    	if(!(this.position == position)) {
        	pagerchange(position);  //调用方法改变viewpage的内容
    	} ;
        // update selected item and title, then close the drawer
        if(isActivity(position)) {
        	mDrawerList.setItemChecked(this.position, true);
        	setTitle(mHomeTitles[this.position]);
        	mDrawerLayout.closeDrawer(mDrawerList);
        }else {
        	mDrawerList.setItemChecked(position, true);
        	setTitle(mHomeTitles[position]);
        	mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

	/* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        return super.onPrepareOptionsMenu(menu);
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	    	actionItem_unread = menu.add(Menu.NONE, Menu.NONE, 6,"消息") ;
	        actionItem_unread.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	        actionItem_unread.setIcon(R.drawable.ic_action_email);
            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if(item.getTitle().equals("消息")){
        	openUnread();
        	return true;
        }
        
            return super.onOptionsItemSelected(item);
        }
    
    //打开消息界面
    private void openUnread() {
		Intent intent = new Intent(this,UnreadACTIVITY.class);
		startActivity(intent);
		if(actionItem_unread != null){
			actionItem_unread.setIcon(R.drawable.ic_action_email);
		}
		NotificationManager mNotificationManager = 
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.cancel(1001);
	}

	@Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        mActionBar.setTitle(mTitle);
    }
    
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    
    /*************************************生成抽屉方法结束***************************************/
	
    //按两次退出程序
    @Override 
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                       
                        Toast.makeText(this, "再按一次退出都来", Toast.LENGTH_SHORT).show();
                        mExitTime = System.currentTimeMillis();

                } else {
                        finish();
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

	//此方法可保证从resume()状态传入intent
	@Override
	protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// must store the new intent unless getIntent() will
                          // return the old one
    } 
	
	
	
	@Override
	protected void onPause() {
		// TODO 自动生成的方法存根
		super.onPause();
		Log.i(TAG, "onPause==>HomeActivity");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		int action = intent.getIntExtra("action", -1);
		Log.i(TAG,"onResume==>HomeAvtivity==>"+action);
		switch(action){
		case 1:
			openUnread();
		break;
		case 2:
			if(alleventFragment != null){
				EventVo event = (EventVo) intent.getSerializableExtra("deletevent");
				alleventFragment.deleteEvent(event);
			}
			break;
		}
		intent = new Intent();
		setIntent(intent);
		
	}

	//复写finish方法
	@Override
	public void finish() {
		super.finish();
		Intent intent = new Intent(this,PullService.class);
		intent.putExtra("action", 2);
		startService(intent);
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean syncConnPref = sharedPref.getBoolean(SettingACTIVITY.PrefsFragment.KEY_PREF_BACK_PULL, false);
		SharedPreferences sharedPref1 = getSharedPreferences("userdata",Context.MODE_PRIVATE);
		if(sharedPref1.getInt("uid", -1) == -1){
			syncConnPref = false;		//没有登录
		}
		if(syncConnPref){			//判断是否开启后台推送
			Log.i("Service", "开启后台推送");
			Intent intent1 = new Intent(this,PullService.class);
			intent1.putExtra("action", 3);
			startService(intent1);
		}else{
			Intent intent2 = new Intent(this,PullService.class);
			stopService(intent2);
		}
	}

    
}

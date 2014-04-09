package cn.edu.njupt.allgo.fragment;

import java.util.ArrayList;

import cn.edu.njupt.allgo.adapter.MyViewAdapter;
import cn.edu.njupt.allgo.widget.CustomViewPager;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup; 


public abstract class BaseFRAGMENT extends Fragment {

	private String FragmentFlag = null ;
	private boolean LogFlag = false ;
	private boolean initBarFlag = false; //需要生成Actionbar操作的标志
	
	public void setFragmentFlag (String arg) {
		this.FragmentFlag = arg ;
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		if(LogFlag == true) {
		Log.i("fragment1","onAttach()==>" + FragmentFlag + "=Activity=>" + activity);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(LogFlag == true) {
		Log.i("fragment1","onCreate()==>" + FragmentFlag);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(LogFlag == true) {
		Log.i("fragment1","onCreateView()==>" + FragmentFlag);
		}
		return null;
	}

	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(LogFlag == true) {
		Log.i("fragment1","onActivityCreated()==>" + FragmentFlag);
		}
		if(initBarFlag) {
			initActionBar();
			initBarFlag = false ;
		}
	}
	
	public void setBarFlag(boolean arg) {
		this.initBarFlag = arg ;
	}
	
	/**
	 * 生成ActionBar的方法，需要重写
	 */
	public abstract  void initActionBar() ;

	@Override
	public void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		if(LogFlag == true) {
		Log.i("fragment1","onStart()==>" + FragmentFlag);
		}
    	 
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(LogFlag == true) {
		Log.i("fragment1","onPause()==>" + FragmentFlag);
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(LogFlag == true) {
		Log.i("fragment1","onResume()==>" + FragmentFlag);
		}
	}
	
	public void onStop(){
		super.onStop();
		if(LogFlag == true) {
		Log.i("fragment1","onStop()==>" + FragmentFlag);
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		if(LogFlag == true) {
		Log.i("fragment1","onDestroy()==>" + FragmentFlag);
		}
		saveData();
	}

	/**
	 * 程序结束时存储数据
	 */
	public  void saveData() {
	}
	
	@Override
	public void onDestroyView() {
		// TODO 自动生成的方法存根
		super.onDestroyView();
		if(LogFlag == true) {
		Log.i("fragment1","onDestroyView()==>" + FragmentFlag);
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if(LogFlag == true) {
		Log.i("fragment1","onDetach()==>" + FragmentFlag);
		}
	}
	
	//初始化ViewPager的方法
    public void initViewPager(MyViewAdapter adapter,ArrayList<Fragment> list01 ,CustomViewPager mViewPager){
    	FragmentManager manager = getChildFragmentManager();
    	adapter = new MyViewAdapter(manager,list01);
		mViewPager.setAdapter(adapter);
		adapter.notifyDataSetChanged();
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {  
            
            @Override  
            public void onPageSelected(int arg0) {
                if(getActivity().getActionBar().getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS) {  
                	getActivity().getActionBar().setSelectedNavigationItem(arg0);
                }
            }
              
            @Override  
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                  
            }  
              
            @Override  
            public void onPageScrollStateChanged(int arg0) {
                  
            }  
        }); 
    }
	
}

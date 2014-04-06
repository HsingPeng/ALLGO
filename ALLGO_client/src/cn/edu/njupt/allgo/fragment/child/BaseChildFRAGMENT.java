package cn.edu.njupt.allgo.fragment.child;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup; 


public abstract class BaseChildFRAGMENT extends Fragment {

	private String FragmentFlag = null ;
	private boolean LogFlag = false ;
	
	public void setFragmentFlag (String arg) {
		this.FragmentFlag = arg ;
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		if(LogFlag == true) {
		Log.i("fragment.child","onAttach()==>" + FragmentFlag + "=Activity=>" + activity);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(LogFlag == true) {
		Log.i("fragment.child","onCreate()==>" + FragmentFlag);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(LogFlag == true) {
		Log.i("fragment.child","onCreateView()==>" + FragmentFlag);
		}
		return null;
	}

	@Override
	public void onActivityCreated (Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(LogFlag == true) {
		Log.i("fragment.child","onActivityCreated()==>" + FragmentFlag);
		}
	}


	@Override
	public void onStart() {
		// TODO 自动生成的方法存根
		super.onStart();
		if(LogFlag == true) {
		Log.i("fragment.child","onStart()==>" + FragmentFlag);
		}
    	 
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(LogFlag == true) {
		Log.i("fragment.child","onPause()==>" + FragmentFlag);
		}
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(LogFlag == true) {
		Log.i("fragment.child","onResume()==>" + FragmentFlag);
		}
	}
	
	public void onStop(){
		super.onStop();
		if(LogFlag == true) {
		Log.i("fragment.child","onStop()==>" + FragmentFlag);
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		if(LogFlag == true) {
		Log.i("fragment.child","onDestroy()==>" + FragmentFlag);
		}
		
		saveData();
		
	}

	/**
	 * 程序结束时存储数据
	 */
	public abstract void saveData() ;

	@Override
	public void onDestroyView() {
		// TODO 自动生成的方法存根
		super.onDestroyView();
		if(LogFlag == true) {
		Log.i("fragment.child","onDestroyView()==>" + FragmentFlag);
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		if(LogFlag == true) {
		Log.i("fragment.child","onDetach()==>" + FragmentFlag);
		}
	}
	
}

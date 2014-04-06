package cn.edu.njupt.allgo.fragment;

import java.util.ArrayList;

import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import cn.edu.njupt.allgo.HomeACTIVITY;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.adapter.UnreadCardsAdapter;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logic.UnreadLogic;
import cn.edu.njupt.allgo.logicImpl.UnreadLogicImpl;
import cn.edu.njupt.allgo.vo.UnreadVo;
import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;  
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class UnreadFRAGMENT extends BaseFRAGMENT implements PullToRefreshAttacher.OnRefreshListener , RefreshInterFace{

	private ListView listView;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private UnreadCardsAdapter UnreadcardsAdapter;
	private ArrayList<UnreadVo> unreadDate = new ArrayList<UnreadVo>() ;
	private SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
	private UnreadLogic unreadLogic ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		 
		super.onCreate(savedInstanceState);
		setFragmentFlag("UnreadFRAGMENT");
		setHasOptionsMenu(true);
		unreadLogic = new UnreadLogicImpl(getActivity() , UnreadFRAGMENT.this);
		unreadLogic.initUnread();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_unread, null);
		return view;
	}

	@Override 
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		setView();
	}
	
	
	private void setView() {
		listView = (ListView)getView().findViewById(R.id.listView_unread);
		mPullToRefreshAttacher = ((HomeACTIVITY)getActivity())
                .getPullToRefreshAttacher();
        mPullToRefreshAttacher.addRefreshableView(listView, this);
        
        UnreadcardsAdapter = new UnreadCardsAdapter(getActivity(),unreadDate);
		swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(UnreadcardsAdapter);
		swingBottomInAnimationAdapter.setInitialDelayMillis(300);
		swingBottomInAnimationAdapter.setAbsListView(listView);
		listView.setAdapter(swingBottomInAnimationAdapter);
        
		listView.setOnItemClickListener(new OnItemClickListener() {   
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO 点击方法
				
				Toast.makeText( getActivity(),
						"点击了==》"+UnreadcardsAdapter.getItem(arg2).getAction(), 
						Toast.LENGTH_SHORT).show();

			}
        });
	}

	@Override
	public void initActionBar() {
		// TODO 初始化actionbar
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);  
	}

	@Override
	public void onRefreshStarted(View view) {
		// TODO 下拉刷新的动作
		unreadLogic.getUnread();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object result, int kind) {
		switch(kind){
		case 1:
			if(result != null){
				unreadDate.clear();
				unreadDate.addAll(0,(ArrayList<UnreadVo>)result);
				UnreadcardsAdapter.notifyDataSetChanged();
				}
			break;
		case 2:
			mPullToRefreshAttacher.setRefreshComplete();
            unreadDate.addAll(0,(ArrayList<UnreadVo>)result); 
            UnreadcardsAdapter.notifyDataSetChanged();
			break;
		case 3:			//从数据库更新内容
			unreadLogic.saveUnread(unreadDate);
			unreadLogic.initUnread();
		case -1:
			//Toast.makeText(getActivity(),(String)result,Toast.LENGTH_SHORT).show();
			if(mPullToRefreshAttacher.isRefreshing()){
				mPullToRefreshAttacher.setRefreshComplete();
			}
			break;
		}
	}

	@Override
	public void saveData() {
		unreadLogic.saveUnread(unreadDate);
	}
	
}

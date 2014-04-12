package cn.edu.njupt.allgo.fragment.child;

import java.io.Serializable;
import java.util.ArrayList;

import com.haarman.listviewanimations.swinginadapters.prepared.SwingBottomInAnimationAdapter;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.activity.EventPageACTIVITY;
import cn.edu.njupt.allgo.activity.HomeACTIVITY;
import cn.edu.njupt.allgo.adapter.EventCardsAdapter;
import cn.edu.njupt.allgo.logic.MyEventLogic;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logicImpl.FriendsEventLogicImpl;
import cn.edu.njupt.allgo.logicImpl.MyEventLogicImpl;
import cn.edu.njupt.allgo.util.ArrayListUtil;
import cn.edu.njupt.allgo.vo.EventVo;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;


public class MyEventFRAGMENT extends BaseChildFRAGMENT implements PullToRefreshAttacher.OnRefreshListener ,
				RefreshInterFace{


	private View moreView;
	private ListView listView;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private ArrayList<EventVo> eventsData =new ArrayList<EventVo>() ;  //要方在listview的内容
	private boolean listflag = false ;  //保证上滑刷新的线程同时只开启一个
	private boolean listfootflag = false ; ////保证页脚只有一个
	private EventCardsAdapter eventcardsAdapter;
	private SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
	private MyEventLogic myEventLogic ;
	private int page = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		myEventLogic = new MyEventLogicImpl(getActivity() , MyEventFRAGMENT.this);
		myEventLogic.initEvent();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState) ;
		setFragmentFlag("MyEventFRAGMENT");
		View view = inflater.inflate(R.layout.fragment_event, null);
		return view;
	}

	@Override 
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		   setView();
	}


	private void setView() {

		 
		listView = (ListView)getView().findViewById(R.id.listView_event);
	     mPullToRefreshAttacher = ((HomeACTIVITY)getActivity())
                 .getPullToRefreshAttacher();

         mPullToRefreshAttacher.addRefreshableView(listView, this);

		eventcardsAdapter = new EventCardsAdapter(getActivity(),eventsData);
		swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(eventcardsAdapter);
		swingBottomInAnimationAdapter.setInitialDelayMillis(300);
		swingBottomInAnimationAdapter.setAbsListView(listView);
		
		moreView =  getLayoutInflater(getArguments()).inflate(R.layout.list_eventlistview_load, null);
		listView.addFooterView(moreView); //添加底部view，一定要在setAdapter之前添加，否则会报错。
		listView.setAdapter(swingBottomInAnimationAdapter);
		listView.removeFooterView(moreView);
		//点击事件
		listView.setOnItemClickListener(new OnItemClickListener() {   
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO 点击方法
				/*Toast.makeText( getActivity(),
						"点击了==》"+eventcardsAdapter.getItem(arg2).uname, 
						Toast.LENGTH_SHORT).show();*/
				
				  Intent intent = new Intent(getActivity(),EventPageACTIVITY.class);
				   Bundle bundle = new Bundle();
				   bundle.putSerializable("EventData", eventcardsAdapter.getItem(arg2));
				   intent.putExtras(bundle);
				   startActivity(intent);
				
			}
        });
		listView.setOnScrollListener(new OnScrollListener() {
			int lastItem ;
			int totalItemCount ;
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				lastItem = firstVisibleItem + visibleItemCount ;  
				this.totalItemCount = totalItemCount ;
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) { 
				//下拉到空闲时，且最后一个item的数等于数据的总数时，进行更新
				if(lastItem == totalItemCount  && scrollState == OnScrollListener.SCROLL_STATE_IDLE){ 
					//Log.i("Debug", "拉到最底部");
					if(!(listfootflag)) {
					listView.addFooterView(moreView);
					}
					if((!(listflag))&&listfootflag) {
						//Log.i("Debug", "=listflag=>" + listflag + "=listfootflag=>" + listfootflag);
						listflag = true ;
					moreView.setVisibility(View.VISIBLE);
					myEventLogic.getEvent(page, 15);
					}else {
					listfootflag = true ;
				}
				}
			}});
	}
	
	@Override
	public void onRefreshStarted(View view) {

		if (!(listflag)) {
			listflag = true ;
			myEventLogic.getEvent(1, 15);
	}else {
		mPullToRefreshAttacher.setRefreshComplete();
		Toast.makeText( getActivity(), "正在刷新",Toast.LENGTH_SHORT ).show();	
		}
	
	}

	@Override
	public void saveData() {
		myEventLogic.saveEvent(eventsData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object result, int kind) {
		switch(kind){
		case 1:		//初始化
		if(result != null){
			eventsData.addAll(0,(ArrayList<EventVo>)result);
			ArrayListUtil.removeDuplicate(eventsData);
			ArrayListUtil.sortEventVo(eventsData);
			eventcardsAdapter.notifyDataSetChanged();
			}
		break;
		case 2:		//下拉刷新
			eventsData.addAll(0,(ArrayList<EventVo>)result);
			ArrayListUtil.removeDuplicate(eventsData);
			ArrayListUtil.sortEventVo(eventsData);
            //int count = result.size();
			if(page == 1){page++;}
            eventcardsAdapter.notifyDataSetChanged();
            mPullToRefreshAttacher.setRefreshComplete();
            //listView.setSelection(count) ;
            if(listfootflag){
            	listView.removeFooterView(moreView); //移除底部视图
            	listfootflag = false ;
            }
            listflag =false ;
			break;
		case 3:		//底部刷新
			eventsData.addAll((ArrayList<EventVo>)result); 
			ArrayListUtil.removeDuplicate(eventsData);
			ArrayListUtil.sortEventVo(eventsData);
        	eventcardsAdapter.notifyDataSetChanged();
		    moreView.setVisibility(View.GONE); 
		    page++;
		    listView.removeFooterView(moreView); //移除底部视图
		   	listfootflag = false ;
		   	listflag = false ;
			break;
		case -1 :
			Toast.makeText(getActivity(), (String)result, Toast.LENGTH_SHORT).show();
			if(mPullToRefreshAttacher.isRefreshing()){
				mPullToRefreshAttacher.setRefreshComplete();
			}
			if(listfootflag){
            	listView.removeFooterView(moreView); //移除底部视图
            	listfootflag = false ;
            }
            listflag =false ;
			break;
		case 44:
			eventsData.remove((EventVo)result);
			eventcardsAdapter.notifyDataSetChanged();
			break;
		}
	}
	
	

}

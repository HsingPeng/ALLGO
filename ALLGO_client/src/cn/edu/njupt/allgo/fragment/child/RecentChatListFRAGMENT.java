package cn.edu.njupt.allgo.fragment.child;

import cn.edu.njupt.allgo.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup; 

public class RecentChatListFRAGMENT extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chatlist, null);
		return view;
	}


}

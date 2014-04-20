package cn.edu.njupt.allgo.fragment.child;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.activity.AddFriendACTIVITY;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;  
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;  
import android.view.ViewGroup; 


public class FriendListFRAGMENT extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contactlist, null);
		return view;
	}

		@Override
	    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	           // TODO 添加actionbar菜单

	           MenuItem actionItem1 = menu.add("添加联系人");
	           actionItem1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
	           actionItem1.setIcon(R.drawable.ic_action_add_person);
		   }
    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	    		if(item.getTitle().equals("添加联系人")) {
	    			Intent intent = new Intent(getActivity(),AddFriendACTIVITY.class);
	    			startActivity(intent);
	    			return true ;
	    		}
	            return super.onOptionsItemSelected(item);
	    }
	
}

package cn.edu.njupt.allgo.activity;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.fragment.IntroFRAGMENT;
import cn.edu.njupt.allgo.util.DateUtil;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.UserDataVo;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class IntroACTIVITY extends BaseActivity {

	

	private IntroFRAGMENT intro;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		intro = new IntroFRAGMENT();
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
			intro).commit();
		
	}

	public void dialog_click (int which){
		
		intro.dialog_click(which);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuItem actionItem1 = menu.add("刷新");
        actionItem1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        actionItem1.setIcon(R.drawable.ic_action_refresh);
		
		return super.onCreateOptionsMenu(menu);
	}



	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	finish();
            	return true ;
        }
        if(item.getTitle().equals("刷新")) {
        	intro.get();
			return true ;
		}
        return super.onOptionsItemSelected(item);
    }
	
	//此方法可保证从resume()状态传入intent
	@Override
	protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// must store the new intent unless getIntent() will
                          // return the old one
    } 
	

	@Override
	protected void onResume() {
		super.onResume();
		Intent intent = getIntent();
		int action = intent.getIntExtra("action", -1);
		switch(action){
		case 1:			//修改资料
			UserDataVo user = (UserDataVo)intent.getSerializableExtra("user");
			intro.refresh(user);
		break;
		case 2:			//修改头像
			intro.updateAvatar();
			break;
		}
		
		intent = new Intent();
		setIntent(intent);
		
	}
}

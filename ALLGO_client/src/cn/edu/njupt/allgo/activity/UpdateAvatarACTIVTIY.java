package cn.edu.njupt.allgo.activity;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logic.UpdateUserDetailLogic;
import cn.edu.njupt.allgo.logicImpl.UpdateUserDetailLogicImpl;
import cn.edu.njupt.allgo.util.AvatarUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class UpdateAvatarACTIVTIY extends BaseActivity implements RefreshInterFace {

	private ImageButton imageButton_update_avatar;
	private Button Button_update_avatar_cancel;
	private Button Button_update_avatar_submit;
	private AvatarUtil avatarUtil;
	private UpdateUserDetailLogic logic ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		logic = new UpdateUserDetailLogicImpl(this,this);
		setContentView(R.layout.activtiy_updateavatar);
		initView();
	}

	private void initView(){
		imageButton_update_avatar = (ImageButton)findViewById(R.id.imageButton_update_avatar);
		Button_update_avatar_cancel = (Button)findViewById(R.id.Button_update_avatar_cancel);
		Button_update_avatar_submit = (Button)findViewById(R.id.button_update_avatar_submit);
		
		avatarUtil = new AvatarUtil(imageButton_update_avatar,this);
		
		Button_update_avatar_cancel.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						finish();
					}
				});
		
		Button_update_avatar_submit.setOnClickListener(new View.OnClickListener() {
			
					@Override
					public void onClick(View v) {
						if(avatarUtil.getAvatar() != null){
						logic.updateAvatar(avatarUtil.getAvatar());
						}else{
							Toast.makeText(UpdateAvatarACTIVTIY.this, "请选择图片", Toast.LENGTH_SHORT).show();
						}
					}
				});
		
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		avatarUtil.onActivityResult(arg0, arg1, arg2);
		super.onActivityResult(arg0, arg1, arg2);
	}

	@Override
	protected void onDestroy() {
		avatarUtil.destroy();
		super.onDestroy();
	}

	@Override
	public void refresh(Object result, int kind) {
		switch(kind){
		case 1:
			Toast.makeText(this, "提交成功", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this,IntroACTIVITY.class);
			intent.putExtra("action", 2);
			startActivity(intent);
			finish();
			break;
		case -1:
			Toast.makeText(this, (String)result, Toast.LENGTH_SHORT).show();
			break;
		}
		
	}
	
	
}

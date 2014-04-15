package cn.edu.njupt.allgo.activity;


import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.fragment.LoginDialogFRAGMENT;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logic.RegisterLogic;
import cn.edu.njupt.allgo.logicImpl.RegisterLogicImpl;
import cn.edu.njupt.allgo.util.AvatarUtil;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterACTIVITY extends BaseActivity implements RefreshInterFace{
	

	private EditText editText_register_uname;
	private RadioGroup radioGroup_register_sex;
	private EditText EditText_register_email;
	private EditText EditText_register_password;
	private EditText EditText_register_passconfirm;
	private Button Button_register_cancel;
	private Button button_register_submit;
	private String usex = "男";
	private String uname;
	private String uemail;
	private String upassword;
	private String upassconfirm;
	private ProgressDialog progressDialog;
	private RegisterLogic registerLogic ;
	private ImageButton imageButton_register_avatar;
	private AvatarUtil avatarUtil;

	


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		avatarUtil.onActivityResult(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
        if(getActionBar() != null) {
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }
		setFinishOnTouchOutside(false);
		registerLogic = new RegisterLogicImpl(this,this);
		initView();

        }
	
    private void initView() {
    	editText_register_uname = (EditText)findViewById(R.id.editText_register_uname);
		radioGroup_register_sex = (RadioGroup)this.findViewById(R.id.radioGroup_register_sex);
		EditText_register_email = (EditText)findViewById(R.id.EditText_register_email);
		EditText_register_password = (EditText)findViewById(R.id.EditText_register_password);
		EditText_register_passconfirm = (EditText)findViewById(R.id.EditText_register_passconfirm);
		Button_register_cancel = (Button)findViewById(R.id.Button_register_cancel);
		button_register_submit = (Button)findViewById(R.id.button_register_submit);
		imageButton_register_avatar = (ImageButton)findViewById(R.id.imageButton_register_avatar);
		
		avatarUtil = new AvatarUtil(imageButton_register_avatar,this);
		
		radioGroup_register_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				 
				int radioButtonId = group.getCheckedRadioButtonId();
	                //根据ID获取RadioButton的实例
	                 RadioButton rb = (RadioButton)RegisterACTIVITY.this.findViewById(radioButtonId);
	                 //更新文本内容，以符合选中项
	                 usex = rb.getText().toString() ;

			}});
		
		Button_register_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		button_register_submit.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(checkInput() == true ) {
							/*Toast.makeText(RegisterACTIVITY.this,
									uname + usex + uemail + upassword + upassconfirm, Toast.LENGTH_SHORT).show();*/
							showProgressDialog("正在注册");
							registerLogic.register(uname, usex.equals("男")?1:0, uemail, upassword,avatarUtil.getFile());
						}

					}
				});
		
	}


	private boolean checkInput() {
		boolean flag = false ;
		uname = editText_register_uname.getText().toString();
		uemail = EditText_register_email.getText().toString();
		upassword = EditText_register_password.getText().toString();
		upassconfirm = EditText_register_passconfirm.getText().toString();
		if(uname.equals("")){
			Toast.makeText(RegisterACTIVITY.this, "用户名没写", Toast.LENGTH_SHORT).show();
		}else if(uemail.equals("")){
			Toast.makeText(RegisterACTIVITY.this, "邮箱没写", Toast.LENGTH_SHORT).show();
		}else if(upassword.length() < 6){
			Toast.makeText(RegisterACTIVITY.this, "密码需要大于等于6位", Toast.LENGTH_SHORT).show();
		}else if(upassconfirm.equals("")){
			Toast.makeText(RegisterACTIVITY.this, "密码确认没写", Toast.LENGTH_SHORT).show();
		}else if(!uname.matches("[\u4e00-\u9fa5\\w]+")){
			Toast.makeText(RegisterACTIVITY.this, "用户名含有特殊字符", Toast.LENGTH_SHORT).show();
		}else 
		if(!uemail.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")){
			Toast.makeText(RegisterACTIVITY.this, "邮箱格式错误", Toast.LENGTH_SHORT).show();
		}else if(!upassword.equals(upassconfirm)){
			Toast.makeText(RegisterACTIVITY.this, "两次输入密码不同", Toast.LENGTH_SHORT).show();
		}else{
			flag = true ;
		}
		return flag;
	}
    
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
               /* Intent upIntent = new Intent(this, WelcomeACTIVITY.class);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.from(this)
                            //如果这里有很多原始的Activity,它们应该被添加在这里
                            .addNextIntent(upIntent)
                            .startActivities();
                    finish();
                } else {
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;*/
            	finish();
        }
        return super.onOptionsItemSelected(item);
    }
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		closeProgressDialog();
		avatarUtil.deleteFile();
	}
	
	private void showProgressDialog(String title) {
		if ((!isFinishing()) && (this.progressDialog == null)) {
			this.progressDialog = new ProgressDialog(this);
		}
			this.progressDialog.setTitle(title);
			this.progressDialog.setMessage("请稍等...");
			this.progressDialog.show();
	}
    
	protected void closeProgressDialog() {
		if (this.progressDialog != null)
			this.progressDialog.dismiss();
	}

	@Override
	public void refresh(Object result, int kind) {
		switch(kind){
		case 1 :			//注册后直接跳转到登录画面，已实现
			Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show() ;
			Intent intent = new Intent(this,WelcomeACTIVITY.class);
			intent.putExtra("login", (String)result);
			startActivity(intent);
			closeProgressDialog();
			finish();
			break;
		case -1 :
			String arg1 = (String) result ;
	    	Toast.makeText(this,arg1, Toast.LENGTH_SHORT).show();
	    	closeProgressDialog();
	    	break;
		}
	}
}

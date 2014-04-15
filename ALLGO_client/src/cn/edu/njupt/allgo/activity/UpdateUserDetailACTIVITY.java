package cn.edu.njupt.allgo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.fragment.PlaceSpinnerDialogFRAGMENT;
import cn.edu.njupt.allgo.fragment.PlaceSpinnerDialog_userdetail_FRAGMENT;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.logic.UpdateUserDetailLogic;
import cn.edu.njupt.allgo.logicImpl.UpdateUserDetailLogicImpl;
import cn.edu.njupt.allgo.logicImpl.UserDataLogicImpl;
import cn.edu.njupt.allgo.util.DateUtil;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.UserDataVo;
import cn.edu.njupt.allgo.widget.MyDateSpinnerA;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateUserDetailACTIVITY extends BaseActivity implements RefreshInterFace{

	private EditText userdetail_usatement;
	private RadioGroup radioGroup_userdetail_sex;
	private TextView textview_userdetail_address;
	private Button Button_userdetail_cancel;
	private Button button_userdetail_submit;
	private Button button_userdetail_address;
	private String usex = "男";
	private EditText editText_year;
	private EditText editText_month;
	private EditText editText_day;
	private UserDataVo user;
	private RadioButton radio_man;
	private RadioButton radio_woman;
	private UpdateUserDetailLogic logic ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updateuserdetail);
		logic = new UpdateUserDetailLogicImpl(this,this);
		Intent intent = getIntent();
		user = (UserDataVo)intent.getSerializableExtra("user");
		initView();
		initDate();
	}

	private void initView() {
		userdetail_usatement = (EditText)findViewById(R.id.userdetail_usatement);
		radioGroup_userdetail_sex = (RadioGroup)this.findViewById(R.id.radioGroup_userdetail_sex);
		radio_man=(RadioButton)findViewById(R.id.radio_man);
		radio_woman=(RadioButton)findViewById(R.id.radio_woman);
		editText_year = (EditText)findViewById(R.id.editText_year);
		editText_month = (EditText)findViewById(R.id.editText_month);
		editText_day = (EditText)findViewById(R.id.editText_day);
		userdetail_usatement = (EditText)findViewById(R.id.userdetail_usatement);
		userdetail_usatement = (EditText)findViewById(R.id.userdetail_usatement);
		textview_userdetail_address = (TextView)findViewById(R.id.textview_userdetail_address);
		button_userdetail_address = (Button)findViewById(R.id.button_userdetail_address);
		Button_userdetail_cancel = (Button)findViewById(R.id.Button_userdetail_cancel);
		button_userdetail_submit = (Button)findViewById(R.id.button_userdetail_submit);
		
		radioGroup_userdetail_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				 
				int radioButtonId = group.getCheckedRadioButtonId();
	                //根据ID获取RadioButton的实例
	                 RadioButton rb = (RadioButton)UpdateUserDetailACTIVITY.this.findViewById(radioButtonId);
	                 //更新文本内容，以符合选中项
	                 usex = rb.getText().toString() ;
			}});
		

		button_userdetail_address.setOnClickListener(new View.OnClickListener() {
			

			
			@Override
			public void onClick(View v) {
				// TODO 显示PlaceSpinner
				PlaceSpinnerDialog_userdetail_FRAGMENT newFragment = PlaceSpinnerDialog_userdetail_FRAGMENT.newInstance("PostionSpinner_userdetail");
		        newFragment.show(getSupportFragmentManager(), "dialog");
			}
		
		});
		
		Button_userdetail_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		button_userdetail_submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				submit();
			}
		});
		
		
	}
	
	private void initDate(){
		userdetail_usatement.setText(user.getUsatement());
		if(1 == user.getUsex()){
			radio_man.setChecked(true);
		}else{
			usex = "女";
			radio_woman.setChecked(true);
		}

		String birthday = user.getUbirthday();
		
		if(birthday != null&& !birthday.equals("")){
			editText_year.setText(DateUtil.showDate(birthday, "yyyy"));
			editText_month.setText(DateUtil.showDate(birthday, "MM"));
			editText_day.setText(DateUtil.showDate(birthday, "dd"));
		}
		textview_userdetail_address.setText(user.getUaddress());
	}

	private void submit(){
		user.setUsatement(userdetail_usatement.getText().toString());
		user.setUaddress(textview_userdetail_address.getText().toString());
		String birthday = editText_year.getText().toString()+" "+editText_month.getText().toString()+" "+editText_day.getText().toString();
		user.setUbirthday(DateUtil.saveDate(birthday, "yyyy MM dd", "EEE MMM dd hh:mm:ss Z yyyy"));
		user.setUsex(usex.equals("男")?1:0);
		logic.submitDetail(user);
	}

		public void changePosition(String position){
			textview_userdetail_address.setText(position);
		}

		@Override
		public void refresh(Object result, int kind) {
			switch(kind){
			case 1:
				user = (UserDataVo)result;
				Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(this,IntroACTIVITY.class);
				intent.putExtra("action", 1);
				Bundle bundle = new Bundle();
				   bundle.putSerializable("user", user);
				   intent.putExtras(bundle);
				startActivity(intent);
				finish();
				break;
			case -1:
				Toast.makeText(this, (String)result, Toast.LENGTH_SHORT).show();
				break;
			}
			
		}
	
	
}

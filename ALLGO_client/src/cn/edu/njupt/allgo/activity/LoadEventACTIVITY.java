package cn.edu.njupt.allgo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.logic.RefreshInterFace;
import cn.edu.njupt.allgo.util.MD5;
import cn.edu.njupt.allgo.util.NetUtil;
import cn.edu.njupt.allgo.util.NetUtil.NetCallBack;
import cn.edu.njupt.allgo.vo.EventVo;
import cn.edu.njupt.allgo.vo.UserDataVo;

public class LoadEventACTIVITY extends BaseActivity implements RefreshInterFace{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_processdialog);
		Intent intent = this.getIntent();
		int eid = intent.getIntExtra("eid", -1);
		if(eid != -1){
			loadEvent(eid);
		}
	}

	private void loadEvent(int eid) {
		NetUtil netUtil = new NetUtil("event/base", (RefreshInterFace)this, this, new NetCallBack(){
			@Override
			public void getResult(JSONObject jsonObject) {
				try {
					 if(jsonObject.getString("response").equals("event_base")){
						EventVo vo= JSON.parseObject(jsonObject.getString("event_base") , EventVo.class);
						LoadEventACTIVITY.this.refresh(vo, 1);
         	        }else{
         	        	LoadEventACTIVITY.this.refresh("联网出错", -1);
         	        }
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		});
		netUtil.add("eid", eid+"");
		netUtil.get();
	}

	@Override
	public void refresh(Object result, int kind) {
		switch(kind){
		case 1 :
			openEvent((EventVo)result);
			finish();
			break;
		case -1 :
			Toast.makeText(this, (String)result, Toast.LENGTH_SHORT).show();
			finish();
			break;
		}
		
	}

	private void openEvent(EventVo event) {
		Intent intent = new Intent(this,EventPageACTIVITY.class);
		   Bundle bundle = new Bundle();
		   bundle.putSerializable("EventData", event);
		   intent.putExtras(bundle);
		   startActivity(intent);
	}
	
}

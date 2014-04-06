package cn.edu.njupt.allgo.fragment;


import java.lang.reflect.Field;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.WelcomeACTIVITY;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginDialogFRAGMENT extends DialogFragment {

	private String uname = null;
	private EditText login_username;
	private EditText login_password;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.fragment_dialog_login, null) ;
	    login_username = (EditText)view.findViewById(R.id.login_username);
	    login_password = (EditText)view.findViewById(R.id.login_password);
	    if(uname != null){
	    	login_username.setText(uname);
	    }
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view)
	    // Add action buttons
	           .setPositiveButton("登录", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	                   // sign in the user ...
	            	   try { 
	            			  Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing"); 
	            			  field.setAccessible(true); 
	            			  field.set(dialog, false); 
	            			  } catch (Exception e) {
	            			  e.printStackTrace(); 
	            			  } 
	            	   if(login_username.getText().toString().equals("")){
	            		  Toast.makeText(getActivity(), "用户名没写", Toast.LENGTH_SHORT).show();
	            	   }else if(login_password.getText().toString().equals("")) {
	            			   	Toast.makeText(getActivity(), "密码没写", Toast.LENGTH_SHORT).show();
	            	   }else if(!login_username.getText().toString().matches("[\u4e00-\u9fa5\\w]+")){
	            		   		Toast.makeText(getActivity(), "用户名含有特殊字符", Toast.LENGTH_SHORT).show();
	            	   }else if(login_password.getText().toString().length() < 6){
	            		   		Toast.makeText(getActivity(), "密码少于6位", Toast.LENGTH_SHORT).show();
	            	   }else{
	            		   try {
		            		   Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
		            		   field.setAccessible(true);
		            		   field.set(dialog, true);
		            		   } catch (Exception e) {
		            		   e.printStackTrace();
		            		   }
	            	   ((WelcomeACTIVITY)getActivity())
	            	   .doLoginClick(login_username.getText().toString() , login_password.getText().toString());  
	            	   }
	               }
	           })
	           .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   LoginDialogFRAGMENT.this.getDialog().cancel();
	                   try {
	            		   Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
	            		   field.setAccessible(true);
	            		   field.set(dialog, true);
	            		   } catch (Exception e) {
	            		   e.printStackTrace();
	            		   }
	               }
	           }); 
	    return builder.create();
	
	}

	public static LoginDialogFRAGMENT newInstance(String title) {
		LoginDialogFRAGMENT frag = new LoginDialogFRAGMENT();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


	public void setName(String result) {
		uname = result;
	}
}

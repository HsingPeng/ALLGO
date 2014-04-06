package cn.edu.njupt.allgo.fragment;


import java.lang.reflect.Field;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.WelcomeACTIVITY;
import cn.edu.njupt.allgo.logic.EventPageLogic;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCommentDialogFRAGMENT extends DialogFragment {

	private int uid ;
	private int eid ;
	private String uname ;
	private int replyuid ;
	private String replyuname ;
	private EventPageLogic eventPageLogic;
	private EditText  edit;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.fragment_dialog_addcomment, null) ;
	    edit = (EditText)view.findViewById(R.id.dialog_addcomment);


	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setView(view)
	    	   .setTitle("回复:" + replyuname)
	    // Add action buttons
	           .setPositiveButton("发送", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   eventPageLogic.sendComment(edit.getText().toString(), uid, eid, uname, replyuid, replyuname);
	               }
	           })
	           .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	               }
	           });      
	    return builder.create();
	
	}

	public static AddCommentDialogFRAGMENT newInstance(String title) {
		AddCommentDialogFRAGMENT frag = new AddCommentDialogFRAGMENT();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

	
	
	public void setValue(int uid,int eid,String uname , int replyuid ,String replyuname, EventPageLogic eventPageLogic) {
		this.uid = uid ;
		this.eid = eid ;
		this.uname = uname ;
		this.replyuid = replyuid ;
		this.replyuname = replyuname ;
		this.eventPageLogic = eventPageLogic;
	}
}

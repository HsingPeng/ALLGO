package cn.edu.njupt.allgo.fragment;

import cn.edu.njupt.allgo.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import cn.edu.njupt.allgo.EventPageACTIVITY;



public class UpdateEventDialogFRAGMENT extends DialogFragment {


    public static UpdateEventDialogFRAGMENT newInstance(int title) {
    	UpdateEventDialogFRAGMENT frag = new UpdateEventDialogFRAGMENT();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        
        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setItems(R.array.update_event_array, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		               ((EventPageACTIVITY)getActivity()).dialog_click(which);
		           }
                })
                .create();
    }
	
}

package cn.edu.njupt.allgo.fragment;

import cn.edu.njupt.allgo.R;
import cn.edu.njupt.allgo.widget.PlaceSpinner;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import cn.edu.njupt.allgo.AddEventACTIVITY;

public class PlaceSpinnerDialogFRAGMENT extends DialogFragment {

	private Spinner province_spinner;
	private Spinner city_spinner;
	private Spinner county_spinner;

	private PlaceSpinner placeSpinner;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    View view = inflater.inflate(R.layout.fragment_placespinner, null) ;
	   
	    province_spinner = (Spinner) view.findViewById(R.id.dialog_province_spinner);
		city_spinner = (Spinner) view.findViewById(R.id.dialog_city_spinner);
		county_spinner = (Spinner) view.findViewById(R.id.dialog_county_spinner);
	    
		loadSpinner();
		
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    builder.setTitle("选择活动所在地区")
			   .setView(view)
			   // Add action buttons
	           .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {
	            	   //Toast.makeText(getActivity(), placeSpinner.getPosition(), Toast.LENGTH_SHORT).show();
	            	   ((AddEventACTIVITY)getActivity()).changePosition(placeSpinner.getPosition());
	               }
	           })
	           .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   
	               }
	           });      
	    return builder.create();
	}

	private void loadSpinner() {
		placeSpinner = new PlaceSpinner(getActivity() , province_spinner , city_spinner , county_spinner);
		placeSpinner.loadSpinner();
	}
	
	public static PlaceSpinnerDialogFRAGMENT newInstance(String title) {
		PlaceSpinnerDialogFRAGMENT frag = new PlaceSpinnerDialogFRAGMENT();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


}

package com.randomsilo.mystash.dialog;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.randomsilo.mystash.BrowseActivity;
import com.randomsilo.mystash.R;
import com.randomsilo.mystash.db.pojo.Thing;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.ParentSpinnerAdapter;

public class ChangeThingParentDialog {

	public static void SetParentSpinnerItemByThingid(Spinner spnr, Long id)
	{
		ParentSpinnerAdapter adapter = (ParentSpinnerAdapter) spnr.getAdapter();
	    for (int position = 0; position < adapter.getCount(); position++)
	    {
	    	Thing p = (Thing)adapter.getItem(position);
	        if(p.getId() == id)
	        {
	            spnr.setSelection(position);
	            return;
	        }
	    }
	}
	
	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_change_parent);
		dialog.setTitle(R.string.dialog_change_parent_title);
		
		final TextView ThingId = (TextView) dialog.findViewById(R.id.ThingId);
		final Spinner ParentSpinner = (Spinner) dialog.findViewById(R.id.ParentSpinner);
		
		ThingModel m = MyStashSession.getInstance().getActiveThing();

		// leave if thing not selected
		if( m == null) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_a_thing), Toast.LENGTH_LONG).show();
			dialog.dismiss();
			return;
		}
		
		
		// resource list
		List<Thing> possibleParents = MyStashSession.getInstance().getThingService().getPossibleParents(m);
		
		
		// setup spinner
		ParentSpinner.setAdapter( new ParentSpinnerAdapter(activity, possibleParents));
		ParentSpinner.setSelection( 0);
		SetParentSpinnerItemByThingid(ParentSpinner, m.getThingBelongsTo().getParentId());
		
		
		// dialog controls
		ThingId.setText( m.getThingId()+"");


		// buttons
		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Get Edited Values
				Thing p = (Thing)ParentSpinner.getSelectedItem();
				
				ThingModel m = MyStashSession.getInstance().getActiveThing();
				m.getThingBelongsTo().setParentId( p.getId());
				
				// Save ThingProperty
				MyStashSession.getInstance().getThingService().save(m);
				
				// Refresh Activity
				((BrowseActivity)activity).RefreshList();
				
				// Tell User
				Toast.makeText(activity, activity.getResources().getString(R.string.dialog_save_btn_msg), Toast.LENGTH_LONG).show();
				
				// Go Back
				dialog.dismiss();
			}
		});
		
		
		Button backBtn = (Button) dialog.findViewById(R.id.BackBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.show();
		
		return;
	}
}

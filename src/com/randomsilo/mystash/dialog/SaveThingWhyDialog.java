package com.randomsilo.mystash.dialog;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.ThingMetadataActivity;
import com.randomsilo.mystash.db.pojo.ThingWhy;
import com.randomsilo.mystash.db.pojo.Why;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.model.ThingWhyModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.WhySpinnerAdapter;

public class SaveThingWhyDialog {

	public static void SetWhySpinnerItemByWhyid(Spinner spnr, Long id)
	{
		WhySpinnerAdapter adapter = (WhySpinnerAdapter) spnr.getAdapter();
	    for (int position = 0; position < adapter.getCount(); position++)
	    {
	    	Why r = (Why)adapter.getItem(position);
	        if(r.getId() == id)
	        {
	            spnr.setSelection(position);
	            return;
	        }
	    }
	}
	
	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_save_thing_why);
		dialog.setTitle(R.string.dialog_save_thing_why_title);
		
		final TextView Id = (TextView) dialog.findViewById(R.id.Id);
		final TextView ThingId = (TextView) dialog.findViewById(R.id.ThingId);
		final TextView WhyId = (TextView) dialog.findViewById(R.id.WhyId);
		final Spinner WhySpinner = (Spinner) dialog.findViewById(R.id.WhySpinner);
		final EditText WhyDetails = (EditText) dialog.findViewById(R.id.WhyDetails);
		
		ThingWhyModel c = MyStashSession.getInstance().getActiveWhyModel();
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		
		// resource list
		List<Why> objectList = MyStashSession.getInstance().getThingService().getWhyTags();
		if( objectList.size() < 1) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_to_define_resources), Toast.LENGTH_LONG).show();
			return;
		}
		
		// setup spinner
		WhySpinner.setAdapter( new WhySpinnerAdapter(activity, objectList));
		WhySpinner.setSelection( 0);
		Why r = objectList.get( 0);
		
		// leave if thing not selected
		if( m == null) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_a_thing), Toast.LENGTH_LONG).show();
			dialog.dismiss();
		}
		
		// set default model
		if( c == null) {
			c = new ThingWhyModel();
			c.setId(0L);
			c.setThingId(m.getThingId());
			c.setWhyId(r.getId());
			c.setWhy(r);
			c.setWhyDetails("");
			
		} else {
			SetWhySpinnerItemByWhyid(WhySpinner, c.getWhyId());
			r = (Why)WhySpinner.getSelectedItem();
		}
		
		// dialog controls
		Id.setText(c.getId()+"");
		ThingId.setText(c.getThingId()+"");
		WhyId.setText(c.getWhyId()+"");
		WhyDetails.setText(c.getWhyDetails());

		// buttons
		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Get Edited Values
				Why o = (Why)WhySpinner.getSelectedItem();
				
				ThingWhy to = new ThingWhy(); 
				to.setId(Long.parseLong(Id.getText()+""));
				to.setThingId(Long.parseLong(ThingId.getText()+""));
				to.setWhyId(o.getId());
				to.setWhyDetails(WhyDetails.getText()+"");
				
				// Save ThingWhy
				MyStashSession.getInstance().getThingService().getThingWhyDao().save(to);
				
				// Refresh Activity
				((ThingMetadataActivity)activity).RefreshWhyList();
				
				// Tell User
				Toast.makeText(activity, activity.getResources().getString(R.string.dialog_save_btn_msg), Toast.LENGTH_LONG).show();
				
				// Go Back
				dialog.dismiss();
			}
		});
		
		Button removeBtn = (Button) dialog.findViewById(R.id.RemoveBtn);
		if(c.getId() == 0) {
			removeBtn.setEnabled(false);
		} else {
			removeBtn.setEnabled(true);
		}
		removeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// Get Edited Values
				ThingWhy tc = new ThingWhy(); 
				tc.setId(Long.parseLong(Id.getText()+""));
				tc.setThingId(Long.parseLong(ThingId.getText()+""));
				tc.setWhyId(Long.parseLong(WhyId.getText()+""));
				tc.setWhyDetails(WhyDetails.getText()+"");
				
				// Remove ThingWhy
				MyStashSession.getInstance().getThingService().getThingWhyDao().delete(tc);
				
				// Refresh Activity
				((ThingMetadataActivity)activity).RefreshWhyList();
				
				// Tell User
				Toast.makeText(activity, activity.getResources().getString(R.string.dialog_remove_btn_msg), Toast.LENGTH_LONG).show();
				
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

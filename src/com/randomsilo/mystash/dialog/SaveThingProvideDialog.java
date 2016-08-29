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
import com.randomsilo.mystash.ThingResourceActivity;
import com.randomsilo.mystash.db.pojo.Resource;
import com.randomsilo.mystash.db.pojo.ThingProvide;
import com.randomsilo.mystash.model.ThingProvideModel;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.ResourceSpinnerAdapter;

public class SaveThingProvideDialog {

	public static void SetResourceSpinnerItemByResourceid(Spinner spnr, Long id)
	{
		ResourceSpinnerAdapter adapter = (ResourceSpinnerAdapter) spnr.getAdapter();
	    for (int position = 0; position < adapter.getCount(); position++)
	    {
	    	Resource r = (Resource)adapter.getItem(position);
	        if(r.getId() == id)
	        {
	            spnr.setSelection(position);
	            return;
	        }
	    }
	}
	
	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_save_thing_provide);
		dialog.setTitle(R.string.dialog_save_thing_provide_title);
		
		final TextView Id = (TextView) dialog.findViewById(R.id.Id);
		final TextView ThingId = (TextView) dialog.findViewById(R.id.ThingId);
		final TextView ResourceId = (TextView) dialog.findViewById(R.id.ResourceId);
		final Spinner ResourceSpinner = (Spinner) dialog.findViewById(R.id.ResourceSpinner);
		final EditText ResourceQuantity = (EditText) dialog.findViewById(R.id.ResourceQuantity);
		final EditText ResourceDetails = (EditText) dialog.findViewById(R.id.ResourceDetails);
		
		ThingProvideModel c = MyStashSession.getInstance().getActiveProvideModel();
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		
		// resource list
		List<Resource> resourceList = MyStashSession.getInstance().getThingService().getResourceTags();
		if( resourceList.size() < 1) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_to_define_resources), Toast.LENGTH_LONG).show();
			return;
		}
		
		// setup spinner
		ResourceSpinner.setAdapter( new ResourceSpinnerAdapter(activity, resourceList));
		ResourceSpinner.setSelection( 0);
		Resource r = resourceList.get( 0);
		
		// leave if thing not selected
		if( m == null) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_a_thing), Toast.LENGTH_LONG).show();
			dialog.dismiss();
		}
		
		// set default model
		if( c == null) {
			c = new ThingProvideModel();
			c.setId(0L);
			c.setThingId(m.getThingId());
			c.setResourceId(r.getId());
			c.setResource(r);
			c.setResourceQuantity(1);
			c.setResourceDetails("");
			
		} else {
			SetResourceSpinnerItemByResourceid(ResourceSpinner, c.getResourceId());
			r = (Resource)ResourceSpinner.getSelectedItem();
		}
		
		// dialog controls
		Id.setText(c.getId()+"");
		ThingId.setText(c.getThingId()+"");
		ResourceId.setText(c.getResourceId()+"");
		ResourceQuantity.setText(c.getResourceQuantity()+"");
		ResourceDetails.setText(c.getResourceDetails());

		// buttons
		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				// Get Edited Values
				Resource r = (Resource)ResourceSpinner.getSelectedItem();
				
				ThingProvide tc = new ThingProvide(); 
				tc.setId(Long.parseLong(Id.getText()+""));
				tc.setThingId(Long.parseLong(ThingId.getText()+""));
				tc.setResourceId(r.getId());
				tc.setResourceQuantity(Integer.parseInt(ResourceQuantity.getText()+""));
				tc.setResourceDetails(ResourceDetails.getText()+"");
				
				// Save ThingProvide
				MyStashSession.getInstance().getThingService().getThingProvideDao().save(tc);
				
				// Refresh Activity
				((ThingResourceActivity)activity).RefreshProvideList();
				
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
				ThingProvide tc = new ThingProvide(); 
				tc.setId(Long.parseLong(Id.getText()+""));
				tc.setThingId(Long.parseLong(ThingId.getText()+""));
				tc.setResourceId(Long.parseLong(ResourceId.getText()+""));
				tc.setResourceQuantity(Integer.parseInt(ResourceQuantity.getText()+""));
				tc.setResourceDetails(ResourceDetails.getText()+"");
				
				// Remove ThingProvide
				MyStashSession.getInstance().getThingService().getThingProvideDao().delete(tc);
				
				// Refresh Activity
				((ThingResourceActivity)activity).RefreshProvideList();
				
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

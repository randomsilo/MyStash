package com.randomsilo.mystash.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.randomsilo.mystash.BrowseActivity;
import com.randomsilo.mystash.R;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.service.ThingSaveResponse;
import com.randomsilo.mystash.service.ThingService;
import com.randomsilo.mystash.session.MyStashSession;

public class AddThingDialog {

	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		
		dialog.setContentView(R.layout.dialog_add_thing);
		dialog.setTitle(R.string.dialog_add_thing_title);

		final EditText itemName = (EditText) dialog.findViewById(R.id.ItemName);

		Button addBtn = (Button) dialog.findViewById(R.id.AddBtn);
		addBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				ThingModel currentThing = MyStashSession.getInstance().getActiveThing();
				Long parentId = null;
				if(currentThing != null 
						&& currentThing.getThingId() != null 
						&& currentThing.getThingId() > 0) {
					parentId = currentThing.getThingId();
				}
				
				ThingService service = MyStashSession.getInstance().getThingService();
				ThingModel item = new ThingModel();
				
				item.getThing().setTag(itemName.getText().toString());
				item.getThing().setDescription("");
				item.getThingBelongsTo().setParentId(parentId);
				
				ThingSaveResponse itemSave = service.save(item);
				if(itemSave.wasSaved()) {
					dialog.dismiss();
					Intent intent = new Intent(activity, BrowseActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        
					activity.startActivity(intent);
					activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				} else {
					Toast.makeText(activity, activity.getResources().getString(R.string.dialog_add_thing_error), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		Button cancelBtn = (Button) dialog.findViewById(R.id.CancelBtn);
		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		
		dialog.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.dialog_header);
		dialog.show();
		
		return;
	}
}

package com.randomsilo.mystash.dialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.randomsilo.mystash.R;
import com.randomsilo.mystash.ThingScheduleActivity;
import com.randomsilo.mystash.db.pojo.AttentionDate;
import com.randomsilo.mystash.db.pojo.ThingAttentionDate;
import com.randomsilo.mystash.model.ThingAttentionDateModel;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.AttentionDateSpinnerAdapter;

public class SaveThingAttentionDateDialog {

	public static void SetAttentionDateSpinnerItemByAttentionDateid(Spinner spnr, Long id)
	{
		AttentionDateSpinnerAdapter adapter = (AttentionDateSpinnerAdapter) spnr.getAdapter();
	    for (int position = 0; position < adapter.getCount(); position++)
	    {
	    	AttentionDate p = (AttentionDate)adapter.getItem(position);
	        if(p.getId() == id)
	        {
	            spnr.setSelection(position);
	            return;
	        }
	    }
	}
	
	public static void show(final Activity activity) {
		final Dialog dialog = new Dialog(activity, R.style.DialogMyStash);
		dialog.setContentView(R.layout.dialog_save_thing_attention_date);
		dialog.setTitle(R.string.dialog_save_thing_attention_date_title);
		
		final TextView Id = (TextView) dialog.findViewById(R.id.Id);
		final TextView ThingId = (TextView) dialog.findViewById(R.id.ThingId);
		final TextView AttentionDateId = (TextView) dialog.findViewById(R.id.AttentionDateId);
		final TextView AttentionDateValue = (TextView) dialog.findViewById(R.id.AttentionDateValue);
		final Spinner AttentionDateSpinner = (Spinner) dialog.findViewById(R.id.AttentionDateSpinner);
		
		ThingAttentionDateModel tpm = MyStashSession.getInstance().getActiveAttentionDateModel();
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		
		// list
		List<AttentionDate> attentionDateList = MyStashSession.getInstance().getThingService().getAttentionDateTags();
		if( attentionDateList.size() < 1) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_to_define_properties), Toast.LENGTH_LONG).show();
			return;
		}
		
		// setup spinner
		AttentionDateSpinner.setAdapter( new AttentionDateSpinnerAdapter(activity, attentionDateList));
		AttentionDateSpinner.setSelection( 0);
		AttentionDate p = attentionDateList.get( 0);
		
		// leave if thing not selected
		if( m == null) {
			Toast.makeText(activity, activity.getResources().getString(R.string.dialog_need_a_thing), Toast.LENGTH_LONG).show();
			dialog.dismiss();
		}
		
		// set default model
		if( tpm == null) {
			tpm = new ThingAttentionDateModel();
			tpm.setId(0L);
			tpm.setThingId(m.getThingId());
			tpm.setAttentionDateId(p.getId());
			tpm.setValue("");
			tpm.setAttentionDate(p);

			
		} else {
			SetAttentionDateSpinnerItemByAttentionDateid(AttentionDateSpinner, tpm.getAttentionDateId());
			p = (AttentionDate)AttentionDateSpinner.getSelectedItem();
		}
		
		// dialog controls
		Id.setText(tpm.getId()+"");
		ThingId.setText(tpm.getThingId()+"");
		AttentionDateId.setText(tpm.getAttentionDateId()+"");
		AttentionDateValue.setText(tpm.getValue()+"");

		// buttons
		Button saveBtn = (Button) dialog.findViewById(R.id.SaveBtn);
		saveBtn.setOnClickListener(new OnClickListener() {
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {

				// Get Edited Values
				AttentionDate p = (AttentionDate)AttentionDateSpinner.getSelectedItem();
				
				DateFormat localeDateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
				String localDateFormatPattern = localeDateFormat.format(Calendar.getInstance(Locale.getDefault()).getTime());
				
				SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				
				ThingAttentionDate tp = new ThingAttentionDate(); 
				tp.setId(Long.parseLong(Id.getText()+""));
				tp.setThingId(Long.parseLong(ThingId.getText()+""));
				tp.setAttentionDateId(p.getId());
				tp.setValue(AttentionDateValue.getText()+"");
				
				try {
					Date attentionDate = localeDateFormat.parse(AttentionDateValue.getText()+"");
					tp.setValueDate(isoDateFormat.format(attentionDate));
				} catch (ParseException e) {
					Toast.makeText(activity, activity.getResources().getString(R.string.user_msg_bad_date, localDateFormatPattern), Toast.LENGTH_LONG).show();
					return;
				}
				
				
				
				
				// Save ThingAttentionDate
				MyStashSession.getInstance().getThingService().getThingAttentionDateDao().save(tp);
				
				// Refresh Activity
				((ThingScheduleActivity)activity).RefreshAttentionDateList();
				
				// Tell User
				Toast.makeText(activity, activity.getResources().getString(R.string.dialog_save_btn_msg), Toast.LENGTH_LONG).show();
				
				// Go Back
				dialog.dismiss();
			}
		});
		
		Button removeBtn = (Button) dialog.findViewById(R.id.RemoveBtn);
		if(tpm.getId() == 0) {
			removeBtn.setEnabled(false);
		} else {
			removeBtn.setEnabled(true);
		}
		removeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				// Get Edited Values
				ThingAttentionDate tp = new ThingAttentionDate(); 
				tp.setId(Long.parseLong(Id.getText()+""));
				tp.setThingId(Long.parseLong(ThingId.getText()+""));
				tp.setAttentionDateId(Long.parseLong(AttentionDateId.getText()+""));
				
				// Remove ThingAttentionDate
				MyStashSession.getInstance().getThingService().getThingAttentionDateDao().delete(tp);
				
				// Refresh Activity
				((ThingScheduleActivity)activity).RefreshAttentionDateList();
				
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

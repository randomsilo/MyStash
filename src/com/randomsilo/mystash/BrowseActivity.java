package com.randomsilo.mystash;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.randomsilo.mystash.db.pojo.ThingTotalCost;
import com.randomsilo.mystash.dialog.AddThingDialog;
import com.randomsilo.mystash.dialog.ChangeThingParentDialog;
import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.ThingListArrayAdapter;
import com.randomsilo.mystash.util.MoneyHelper;

public class BrowseActivity extends BaseThingNavigationActivity {
	private TextView itemBelongsTo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_browse);
		
		RefreshList();
	}
	
	public void RefreshList() {
		itemBelongsTo = (TextView)findViewById(R.id.ItemBelongsTo);
		
		List<ThingModel> things = new ArrayList<ThingModel>();
		
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		if(m == null) {
			// No Selected Thing
			itemBelongsTo.setText(R.string.app_name);
			things = MyStashSession.getInstance().getThingService().getAllThings(null);
		} else {
			// Selected Thing
			things = MyStashSession.getInstance().getThingService().getAllThings(m.getThingId());
			
			if(m.getThingBelongsTo().getParentId() != null && m.getThingBelongsTo().getParentId() > 0 ) {
				// Selected Thing has Parent
				ThingModel parent = MyStashSession.getInstance().getThingService().get(m.getThingBelongsTo().getParentId());
				itemBelongsTo.setText(parent.getThing().getTag());
			} else {
				// Selected Thing's Parent is Root
				itemBelongsTo.setText(R.string.app_name);
			}
		}
		
		ListView thingListView = (ListView)findViewById(R.id.ThingListView);
		ThingListArrayAdapter adapter = new ThingListArrayAdapter(this, R.layout.list_thing_item, things);
		thingListView.setAdapter(adapter);
	}
	
	public void addThing(View view) {
		AddThingDialog.show(this);
	}
	
	public void changeParent(View view) {
		ChangeThingParentDialog.show(this);
	}
	
	public void costDetails(View view) {
		
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		ThingTotalCost ttc = null;
		String name = "";
		if(m == null || (m != null && m.getThing() == null)) {
			ttc = MyStashSession.getInstance().getThingService().getThingTotalCost( (long)0);
			name = getResources().getString(R.string.app_name);
		} else {
			ttc = MyStashSession.getInstance().getThingService().getThingTotalCost( m.getThingId());
			name = m.getThing().getTag();
		}
	
		LinearLayout viewGroup = (LinearLayout) findViewById(R.id.CostPopup);
		LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View popupView = layoutInflater.inflate(R.layout.cost_popup, viewGroup);
		
		final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		TextView TotalActualCost = (TextView) popupView.findViewById(R.id.TotalActualCost);
		TextView TotalReplacementCost = (TextView) popupView.findViewById(R.id.TotalReplacementCost);
		TextView ThingName = (TextView) popupView.findViewById(R.id.ThingName);
		
		if(ttc != null) {
			TotalActualCost.setText( MoneyHelper.convertDouble(ttc.getTotalActualCost()));
			TotalReplacementCost.setText( MoneyHelper.convertDouble(ttc.getTotalReplacementCost()));
			ThingName.setText(name);
		}
		
		Button btnDismiss = (Button) popupView.findViewById(R.id.dismiss);
		btnDismiss.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});

		
		popupWindow.showAtLocation( popupView, Gravity.CENTER, 0, 0);
	}

}

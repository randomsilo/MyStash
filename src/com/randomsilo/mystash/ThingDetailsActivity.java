package com.randomsilo.mystash;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.randomsilo.mystash.model.ThingModel;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.CurrencyTextWatcher;
import com.randomsilo.mystash.util.MoneyHelper;

public class ThingDetailsActivity extends BaseThingNavigationActivity {
	private EditText thingTag;
	private EditText thingDescription;
	private EditText thingActualCost;
	private EditText thingReplacementCost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thing_details);

		SetupComponents();
	}

	private void SetupComponents() {
		thingTag = (EditText) findViewById(R.id.ThingTag);
		thingDescription = (EditText) findViewById(R.id.ThingDescription);
		thingActualCost = (EditText) findViewById(R.id.ThingActualCost);
		thingReplacementCost = (EditText) findViewById(R.id.ThingReplacementCost);

		thingActualCost.addTextChangedListener(new CurrencyTextWatcher());
		thingReplacementCost.addTextChangedListener(new CurrencyTextWatcher());

		ThingModel m = MyStashSession.getInstance().getActiveThing();
		thingTag.setText(m.getThing().getTag());
		thingDescription.setText(m.getThing().getDescription());
		thingActualCost.setText(MoneyHelper.convertDouble(m.getThing()
				.getActualCost()));
		thingReplacementCost.setText(MoneyHelper.convertDouble(m.getThing()
				.getReplacementCost()));
	}

	public void saveDetails(View view) {
		ThingModel m = MyStashSession.getInstance().getActiveThing();
		m.getThing().setTag(thingTag.getText().toString());
		m.getThing().setDescription(thingDescription.getText().toString());
		m.getThing().setActualCost(	MoneyHelper.convertString(thingActualCost.getText().toString()));
		m.getThing().setReplacementCost( MoneyHelper.convertString(thingReplacementCost.getText().toString()));

		boolean success = MyStashSession.getInstance().save();
		if (success) {
			Toast.makeText(this,
					getResources().getString(R.string.thing_save_sucess),
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this,
					getResources().getString(R.string.thing_save_failure),
					Toast.LENGTH_LONG).show();
		}

	}

}

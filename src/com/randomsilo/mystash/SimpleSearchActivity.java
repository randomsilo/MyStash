package com.randomsilo.mystash;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.randomsilo.mystash.db.pojo.FoundItem;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.FoundItemListArrayAdapter;
import com.randomsilo.mystash.ui.listener.OnClickFoundListItem;

public class SimpleSearchActivity extends Activity {
	Button GetItemsBtn;
	EditText SearchText;
	private TextView FoundItemListHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_search);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.up_only, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_up) {
			finish();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	public void GetItemsBtn(View view) {
		GetItemsBtn = (Button)findViewById(R.id.GetItemsBtn);
		SearchText = (EditText)findViewById(R.id.SearchText);
		FoundItemListHeader = (TextView)findViewById(R.id.FoundItemListHeader);
		
		GetItemsBtn.setEnabled(false);
		
		try {
			
			List<FoundItem> objects = MyStashSession.getInstance().getThingService().findItems(SearchText.getText()+"");
			
			ListView listView = (ListView)findViewById(R.id.ItemListView);
			FoundItemListArrayAdapter adapter = new FoundItemListArrayAdapter(this, R.layout.list_found_items, objects);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnClickFoundListItem());
			FoundItemListHeader.setText( this.getResources().getString(R.string.header_found_list_with_count, objects.size()));
			
		} catch( Exception e) {
			Toast.makeText(this, this.getResources().getString(R.string.error_finding_items), Toast.LENGTH_LONG).show();
		}
		
		InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		GetItemsBtn.setEnabled(true);
	}
}

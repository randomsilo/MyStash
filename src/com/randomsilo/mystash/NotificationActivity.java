package com.randomsilo.mystash;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.randomsilo.mystash.db.pojo.ExpiringItem;
import com.randomsilo.mystash.session.MyStashSession;
import com.randomsilo.mystash.ui.adapter.ExpiringItemListArrayAdapter;
import com.randomsilo.mystash.ui.listener.OnClickExpiredListItem;

public class NotificationActivity extends Activity {

	EditText DaysBeforeExpire;
	Button GetExpiringBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		DaysBeforeExpire = (EditText)findViewById(R.id.DaysBeforeExpire);
		DaysBeforeExpire.setText(MyStashSession.getInstance().getNotificationService().getDaysBeforeExpire()+"");
	}

	public void GetExpiring(View view) {
		GetExpiringBtn = (Button)findViewById(R.id.GetExpiringBtn);
		DaysBeforeExpire = (EditText)findViewById(R.id.DaysBeforeExpire);
		
		GetExpiringBtn.setEnabled(false);
		
		try {
			int days = Integer.parseInt(DaysBeforeExpire.getText()+"");
			
			if( days != MyStashSession.getInstance().getNotificationService().getDaysBeforeExpire()) {
				MyStashSession.getInstance().getNotificationService().setDaysBeforeExpire(days);
				MyStashSession.getInstance().getNotificationService().save();
			}
			
			List<ExpiringItem> objects = MyStashSession.getInstance().getNotificationService().getExpiringItems();
			
			ListView listView = (ListView)findViewById(R.id.ExpiringListView);
			ExpiringItemListArrayAdapter adapter = new ExpiringItemListArrayAdapter(this, R.layout.list_expiring_items, objects);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnClickExpiredListItem());
			
		} catch( Exception e) {
			Toast.makeText(this, this.getResources().getString(R.string.error_getting_expiring_items), Toast.LENGTH_LONG).show();
		}
		
		GetExpiringBtn.setEnabled(true);
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

}

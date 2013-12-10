package com.example.tasklist;

import java.sql.SQLException;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class PriorityListActivity extends ListActivity {

	private DBHelper db;
	private List<Priority> priorityList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new DBHelper();
		loadPriorities();
		showList();
	}

	private void loadPriorities() {
		try {
			priorityList = db.getAllPriorities(this);
		} catch (SQLException e) {
			Log.e("PriorityListActivity.loadPriorities", e.toString());
		}
	}

	private void showList() {
		ListViewPriorityAdapter adapter = new ListViewPriorityAdapter(this, priorityList);
		setListAdapter(adapter);
		getListView().setDividerHeight(MainActivity.DIVIDER);
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		editPriority(position);
	}

	//Ruft neue Activity zum bearbeiten auf
	private void editPriority(int priorityPosition) {
		Intent intent = new Intent(this, PriorityActivity.class);
		intent.putExtra(MainActivity.KEY_PRIORITY, priorityList.get(priorityPosition).getId());
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.priority, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_add_priority:
				startActivity(new Intent(this, PriorityActivity.class).putExtra(MainActivity.KEY_PRIORITY, -1));
				break;
			default:
				break;
		}
		return true;
	}

}

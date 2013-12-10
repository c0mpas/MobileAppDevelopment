package com.example.tasklist;



import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadTasks();
		showList();
	}

	private void loadTasks() {
		// #####
	}

	private void showList() {
		// #####
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		// #####
	}

	private void handleTodos() {
				
		}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_settings:
				startActivity(new Intent(this, SettingsActivity.class));
				break;
			case R.id.action_priorities:
				startActivity(new Intent(this, PriorityActivity.class));
				break;
			case R.id.action_categories:
				startActivity(new Intent(this, CategoryActivity.class));
				break;
			case R.id.action_add_task:
				// #####
				break;
			default:
				break;
		}
		return true;
	}

}

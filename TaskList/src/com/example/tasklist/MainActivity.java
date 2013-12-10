package com.example.tasklist;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Element;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	private static final int DIVIDER = 2;

	private ArrayList<ToDoTask> taskList;
	
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
		ToDoDBHelper db = new ToDoDBHelper();
	
		ListViewTaskAdapter adapter;
		try {
			adapter = new ListViewTaskAdapter(this, db.getAll(this));
			setListAdapter(adapter);
			getListView().setDividerHeight(DIVIDER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		// #####
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
				startActivity(new Intent(this, PriorityListActivity.class));
				break;
			case R.id.action_categories:
				startActivity(new Intent(this, CategoryListActivity.class));
				break;
			case R.id.action_add_task:
				startActivity(new Intent(this, TaskActivity.class));
				break;
			default:
				break;
		}
		return true;
	}

}

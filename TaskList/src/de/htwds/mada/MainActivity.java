package de.htwds.mada;

import java.sql.SQLException;
import java.util.List;

import com.example.tasklist.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ListActivity {

	public static final int DIVIDER = 2;
	public static final String KEY_TASK = "key_task";
	public static final String KEY_PRIORITY = "key_priority";
	public static final String KEY_CATEGORY = "key_category";

	private List<Task> taskList;

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
		DBHelper db = new DBHelper();

		ListViewTaskAdapter adapter;
		try {
			taskList = db.getAllTasks(this);
			adapter = new ListViewTaskAdapter(this, taskList);
			setListAdapter(adapter);
			getListView().setDividerHeight(DIVIDER);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {
		editTask(position);
	}

	// Ruft neue Activity zum bearbeiten auf
	private void editTask(int taskPosition) {
		Intent intent = new Intent(this, TaskActivity.class);
		intent.putExtra(KEY_TASK, taskList.get(taskPosition).getID());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onResume() {
		showList();
		super.onResume();
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
			startActivity(new Intent(this, TaskActivity.class).putExtra(
					KEY_TASK, -1));
			break;
		default:
			break;
		}
		return true;
	}
}

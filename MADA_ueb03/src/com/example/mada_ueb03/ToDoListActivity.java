package com.example.mada_ueb03;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ToDoListActivity extends ListActivity {

	public static final String CALL_TYPE = "callType";
	private static final String WRONG_ID = "wrongID";
	public final static int REQUEST_CODE_EDIT = 1;
	public final static int REQUEST_CODE_NEW = 2;

	public final static String SEND_CODE_SERIALIZE = "task";
	public final static String RECIEVE_CODE_TITLE = "title";
	public final static String RECIEVE_CODE_DESCRIPTION = "description";
	public final static String RECIEVE_CODE_PRIORITY = "priority";
	public final static String RECIEVE_CODE_ID = "id";
	private static final String value = null;

	private ArrayList<ToDoTask> taskList;
	private int IDCounter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		loadTasks();
		taskList.add(new ToDoTask("Titel1", "Coole Beschreibung1,  Oh yeah!",
				2, 1));
		taskList.add(new ToDoTask("Titel2", "Coole Beschreibung2,  Oh yeah!",
				3, 1));
		taskList.add(new ToDoTask("Titel3", "Coole Beschreibung3,  Oh yeah!",
				1, 1));
		taskList.add(new ToDoTask("Titel4", "Coole Beschreibung4,  Oh yeah!",
				3, 1));
		taskList.add(new ToDoTask("Titel5", "Coole Beschreibung5,  Oh yeah!",
				2, 1));
		initIDCounter();

		showListView();

	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position,
			long id) {

		editTask(position);

	}

	private void editTask(int taskPosition) {

		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(SEND_CODE_SERIALIZE, taskList.get(taskPosition));
		intent.putExtra(CALL_TYPE, REQUEST_CODE_EDIT);
		startActivityForResult(intent, REQUEST_CODE_EDIT);

	}

	private void createNewTask() {

		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(CALL_TYPE, REQUEST_CODE_NEW);
		startActivityForResult(intent, REQUEST_CODE_NEW);
	}

	private void initIDCounter() {
		IDCounter = 0;

		for (ToDoTask task : taskList) {
			if (task.getID() >= IDCounter)
				IDCounter = task.getID() + 1;
		}
	}

	private void showListView() {
		Collections.sort(taskList);

		ListViewAdapterStd adapter = new ListViewAdapterStd(this, taskList);
		setListAdapter(adapter);

	}

	private void loadTasks() {

		taskList = new ArrayList<ToDoTask>();

	}

	private void saveChanges(Intent data) {

		for (ToDoTask task : taskList) {

			if (task.getID() == data.getIntExtra(RECIEVE_CODE_ID, -1)) {
				try {
					task.setTitle(data.getStringExtra(RECIEVE_CODE_TITLE));
					task.setDescription(data
							.getStringExtra(RECIEVE_CODE_DESCRIPTION));
					task.setPriority(data
							.getIntExtra(RECIEVE_CODE_PRIORITY, -1));
					break;
				} catch (InvalidPrioException e) {
					Log.wtf(WRONG_ID, e);
				}
			}
		}

		showListView();

	}

	private void addNewTask(Intent data) {

		taskList.add(new ToDoTask(data.getStringExtra(RECIEVE_CODE_TITLE), data
				.getStringExtra(RECIEVE_CODE_DESCRIPTION), data.getIntExtra(
				RECIEVE_CODE_PRIORITY, -1), IDCounter));
		
		IDCounter++;
		
		showListView();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == REQUEST_CODE_EDIT) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {

				saveChanges(data);
			}
		}
		if (requestCode == REQUEST_CODE_NEW) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) {

				addNewTask(data);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_addTask:
			createNewTask();
			break;
		case R.id.menu_preference:

			break;
		default:
			break;
		}
		return true;
	}

}

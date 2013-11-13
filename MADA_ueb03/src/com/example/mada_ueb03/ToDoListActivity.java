package com.example.mada_ueb03;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ToDoListActivity extends ListActivity {

	private static final String FILE_NAME = "/todo.list";
	private static final String DIR_NAME = "/ToDoList";
	private static final String SAVE_FILE = "saveFile";
	private static final String LOAD_FILE = "loadFile";
	private static final String ORIENTATION_CHANGED = "orientationChanged";
	public static final String CALL_TYPE = "callType";
	private static final String WRONG_ID = "wrongID";
	public final static int REQUEST_CODE_EDIT = 1;
	public final static int REQUEST_CODE_NEW = 2;
	public final static int REQUEST_CODE_SETTINGS = 3;
	public final static int RESULT_DELETE = 5;
	private static final int DIVIDER = 2;

	public final static String SEND_CODE_SERIALIZE = "task";
	public final static String RECIEVE_TASK = "taskToSave";
	public final static String RECIEVE_CODE_ID = "id";

	private ArrayList<ToDoTask> taskList;
	private int IDCounter;
	private String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		filePath = Environment.getExternalStorageDirectory().toString();

		if (savedInstanceState == null) {
			loadTasks(null);
		} else {
			loadTasks((ArrayList<ToDoTask>) savedInstanceState.getSerializable(ORIENTATION_CHANGED));
		}

		initIDCounter();
		showListView();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(ORIENTATION_CHANGED, taskList);
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		editTask(position);
	}

	//Ruft neue Activity zum bearbeiten auf
	private void editTask(int taskPosition) {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(SEND_CODE_SERIALIZE, taskList.get(taskPosition));
		intent.putExtra(CALL_TYPE, REQUEST_CODE_EDIT);
		startActivityForResult(intent, REQUEST_CODE_EDIT);
	}

	//Ruft neue Activity zum erstellen eines Items auf
	private void createNewTask() {
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(CALL_TYPE, REQUEST_CODE_NEW);
		intent.putExtra(RECIEVE_CODE_ID, IDCounter);
		startActivityForResult(intent, REQUEST_CODE_NEW);
	}

	
	//setzt den counter fuer die ids zu setzen
	private void initIDCounter() {
		IDCounter = 0;
		for (ToDoTask task : taskList) {
			if (task.getID() >= IDCounter) IDCounter = task.getID() + 1;
		}
	}

	
	// zeigt die liste mit dem ausgewaeltem theme und sortiert sie
	private void showListView() {
		Collections.sort(taskList);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		int selectedTheme = Integer.valueOf(((
				prefs.getString(SettingsActivity.THEME, SettingsActivity.DEFAULT_THEME))));

		if (selectedTheme == SettingsActivity.THEME_VALUE_ANDROID) {
			ListViewAdapterStd adapter = new ListViewAdapterStd(this, taskList);
			getListView().setBackgroundColor(ListViewAdapterStd.BACK);
			setListAdapter(adapter);
		} else if (selectedTheme == SettingsActivity.THEME_VALUE_SIMPLE) {
			ListViewAdapterSimple adapter = new ListViewAdapterSimple(this, taskList);
			getListView().setBackgroundColor(ListViewAdapterSimple.BACK);
			setListAdapter(adapter);
		} else if (selectedTheme == SettingsActivity.THEME_VALUE_KITTY) {
			ListViewAdapterKitty adapter = new ListViewAdapterKitty(this, taskList);
			getListView().setBackgroundColor(ListViewAdapterKitty.BACK);
			setListAdapter(adapter);
		} else {
			ListViewAdapterStd adapter = new ListViewAdapterStd(this, taskList);
			getListView().setBackgroundColor(ListViewAdapterStd.BACK);
			setListAdapter(adapter);
		}
		
		getListView().setDividerHeight(DIVIDER);
	}

	
	//laed die listview
	private void loadTasks(ArrayList<ToDoTask> taskList) {

		if (taskList != null) {
			this.taskList = taskList;
		} else if (loadFromFile() != null) {
			this.taskList = loadFromFile();
		} else {
			this.taskList = new ArrayList<ToDoTask>();
		}
	}

	/*
	 * speichert Aenderungen
	 */
	private void saveChanges(Intent data) {
		taskList.remove(data.getSerializableExtra(RECIEVE_TASK));
		taskList.add((ToDoTask) data.getSerializableExtra(RECIEVE_TASK));

		saveToFile();
		showListView();
	}

	//Laed die listview aus der datei
	private ArrayList<ToDoTask> loadFromFile() {

		ArrayList<ToDoTask> tasks = null;

		try {
			FileInputStream fs = new FileInputStream(filePath + DIR_NAME + FILE_NAME);
			ObjectInputStream is = new ObjectInputStream(fs);
			tasks = (ArrayList<ToDoTask>) is.readObject();
			is.close();
		} catch (Exception e) {
			Log.wtf(LOAD_FILE, e);
		}

		return tasks;
	}

	
	/*
	 * Speichetr die ListView in eine Datei
	 */
	private void saveToFile() {

		File dir = new File(filePath, DIR_NAME);
		dir.mkdirs();

		try {
			FileOutputStream fs = new FileOutputStream(filePath + DIR_NAME + FILE_NAME);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(taskList);
			os.close();
		} catch (Exception e) {
			Log.wtf(SAVE_FILE, e);
		}
	}

	
	//Fuegt einen neuen Task in die Liste hinzu
	private void addNewTask(Intent data) {
		taskList.add((ToDoTask) data.getSerializableExtra(RECIEVE_TASK));
		IDCounter++;
		saveToFile();
		showListView();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
		if (requestCode == REQUEST_CODE_EDIT) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) saveChanges(data);
			if (resultCode == RESULT_DELETE) deleteTask(data);
		}
		if (requestCode == REQUEST_CODE_NEW) {
			// Make sure the request was successful
			if (resultCode == RESULT_OK) addNewTask(data);
		}
		if (requestCode == REQUEST_CODE_SETTINGS) {
			if (resultCode == RESULT_OK) showListView();
		}
	}

	
	//Loscht einen Task aus der Liste
	private void deleteTask(Intent data) {
		taskList.remove(data.getSerializableExtra(RECIEVE_TASK));
		saveToFile();
		showListView();
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
			startActivityForResult(new Intent(this, SettingsActivity.class), REQUEST_CODE_SETTINGS);
			break;
		default:
			break;
		}
		return true;
	}

}

package com.example.tasklist;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TaskActivity extends Activity {

	private EditText title;
	private EditText description;
	private Spinner prioSpinner;
	private Spinner categorySpinner;
	private Button save;
	private Button cancel;
	private Button delete;
	private DatePicker datePicker;
	private DBHelper dbHelper;

	private List<Priority> prioList;
	private List<Category> categoryList;
	private int taskID;
	private Task task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task);

		initViews();
		setListeners();



		dbHelper = new DBHelper();

		loadPrioList();
		loadCatList();
		
		taskID = getIntent().getExtras().getInt(MainActivity.KEY_TASK, -1);

		if (taskID != -1)
			updateViews();


	}

	private void loadPrioList() {

		try {
			prioList = dbHelper.getAllPriorities(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (prioList.size() == 0) {
			Toast.makeText(this, R.string.noPrio, Toast.LENGTH_SHORT).show();
			finish();
		} else {

			String[] array = new String[prioList.size()];

			for (int i = 0; i < prioList.size(); i++) {
				array[i] = prioList.get(i).getName();
			}

			ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
					this, android.R.layout.simple_spinner_dropdown_item, array);

			prioSpinner.setAdapter(spinnerArrayAdapter);

		}

	}

	private void loadCatList() {

		try {
			categoryList = dbHelper.getAllCategories(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (categoryList.size() == 0) {
			Toast.makeText(this, R.string.noCategory, Toast.LENGTH_SHORT)
					.show();
			finish();
		} else {

			String[] array = new String[categoryList.size()];

			for (int i = 0; i < categoryList.size(); i++) {
				array[i] = categoryList.get(i).getName();
			}

			ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
					this, android.R.layout.simple_spinner_dropdown_item, array);

			categorySpinner.setAdapter(spinnerArrayAdapter);

		}

	}

	private void updateTask() {

		DBHelper db = new DBHelper();
		task.setPriority(prioList.get(prioSpinner.getSelectedItemPosition()));
		task.setKategorie(categoryList.get(categorySpinner.getSelectedItemPosition()));
		task.setTitle(title.getText().toString());
		task.setDescription(description.getText().toString());
		task.setAblaufJahr(datePicker.getYear());
		task.setAblaufMonat(datePicker.getMonth());
		task.setAblaufTag(datePicker.getDayOfMonth());

		// Setze noch Prio und Kategorie

		try {
			db.update(this, task);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.e("Update Task Error", "Konnte Task nicht updaten", e);
		}
		finish();
	}

	// Listener werden definiert
	private void setListeners() {

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				DBHelper db = new DBHelper();
				try {
					db.delete(getApplicationContext(), task);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}
		});

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (taskID != -1)
					updateTask();
				else
					save();
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancel();
			}
		});
	}

	private void save() {

		String taskTitle;
		// Save title
		try {
			taskTitle = title.getText().toString();
		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.error_title_empty), Toast.LENGTH_SHORT)
					.show();
			return;
		}

		String taskDes;
		// Save description
		try {
			taskDes = description.getText().toString();
		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.error_description_null),
					Toast.LENGTH_SHORT).show();
			return;
		}

		Category category = categoryList.get(categorySpinner
				.getSelectedItemPosition());
		Priority prio = prioList.get(prioSpinner.getSelectedItemPosition());

		Task task = new Task(taskTitle, taskDes, prio, category,
				datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth());

		try {

			dbHelper.insert(this, task);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.e("SQL-Error", "Konnte Task nicht ablegen", e);
		}

		finish();
	}

	/*
	 * Wird nach DetailActivity abbrechen aufgerufen
	 */
	private void cancel() {
		finish();
	}

	// Reference views
	private void initViews() {
		save = (Button) findViewById(R.id.detail_bt_save);
		cancel = (Button) findViewById(R.id.detail_bt_cancel);
		title = (EditText) findViewById(R.id.edit_title);
		description = (EditText) findViewById(R.id.edit_description);
		prioSpinner = (Spinner) findViewById(R.id.spinner_priority);
		categorySpinner = (Spinner) findViewById(R.id.spinner_category);
		datePicker = (DatePicker) findViewById(R.id.datePicker1);
		delete = (Button) findViewById(R.id.detail_bt_delete);

	}

	// Update views with new task
	private void updateViews() {
		List<Task> list = null;
		DBHelper db = new DBHelper();
		try {
			list = db.getTaskList(this, "id", taskID);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.e("Could not get Single Task", "Bam", e);
		}

		task = list.get(0);

		int prioPos = 0;

		for (int i = 0; i < prioList.size(); i++) {

			if (prioList.get(i).getId() == task.getPriority().getId()) {
				prioPos = i;
				break;
			}

		}
		prioSpinner.setSelection(prioPos);

		int catPos = 0;

		for (int i = 0; i < categoryList.size(); i++) {

			if (categoryList.get(i).getId() == task.getCategory().getId()) {
				catPos = i;
				break;
			}

		}
		categorySpinner.setSelection(catPos);

		title.setText(task.getTitle());
		description.setText(task.getDescription());
		datePicker.updateDate(task.getAblaufJahr(), task.getAblaufMonat(),
				task.getAblaufTag());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.detail_menu, menu);
		return true;
	}

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle item selection
	// switch (item.getItemId()) {
	// case R.id.menu_delete:
	// Intent intent = new Intent();
	// intent.putExtra(ToDoListActivity.RECIEVE_TASK, task);
	// // Go back to main activity
	// setResult(ToDoListActivity.RESULT_DELETE, intent);
	// finish();
	// break;
	//
	// default:
	// break;
	// }
	// return true;
	// }

}

package com.example.tasklist;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PriorityActivity extends Activity {
	
	private EditText name;
	private EditText value;
	private Button save;
	private Button cancel;
	private Button delete;
	
	private Priority priority;
	private Boolean newItem = false;
	private DBHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_priority);
		db = new DBHelper();
		
		referenceViews();
		setListeners();
		initFields();
	}

	private void referenceViews() {
		name = (EditText) findViewById(R.id.editTextName);
		value = (EditText) findViewById(R.id.editTextValue);
		save = (Button) findViewById(R.id.buttonSave);
		cancel = (Button) findViewById(R.id.buttonCancel);
		delete = (Button) findViewById(R.id.buttonDelete);
	}
	
	private void setListeners() {
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				save();
				finish();
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				delete();
				finish();
			}
		});
		
	}
	
	private void save() {

		String priorityName;
		try {
			priorityName = name.getText().toString();
		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.error_title_empty), Toast.LENGTH_SHORT)
					.show();
			return;
		}

		int priorityValue;
		// Save description
		try {
			priorityValue = Integer.parseInt(value.getText().toString());
		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.error_description_null),
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (newItem) {
			Priority newPriority = new Priority(priorityName, priorityValue);
			try {
				db.insert(this, newPriority);
			} catch (SQLException e) {
				Log.e("SQL-Error", "Konnte Priorität nicht ablegen", e);
			}
		} else {
			priority.setName(priorityName);
			priority.setValue(priorityValue);
			try {
				db.update(this, priority);
			} catch (SQLException e) {
				Log.e("SQL-Error", "Konnte Priorität nicht speichern", e);
			}
		}
	}

	private void delete() {
		List<Task> taskList = null;
		try {
			taskList = db.getAllTasks(this);
		} catch (SQLException e) {
			Log.e("SQL-Error", "Unable to load task list", e);
		}
		
		if (exists(taskList, priority)) {
			Toast.makeText(this, R.string.message_delete_priority, Toast.LENGTH_LONG).show();
		} else {
			try {
				db.delete(this, priority);
			} catch (SQLException e) {
				Log.e("SQL-Error", "Unable to delete priority", e);
			}
		}
	}
	
	private Boolean exists(List<Task> taskList, Priority priority) {
		if (taskList==null || priority==null) return false;
		int priorityID = priority.getId();
		for (Task task : taskList) {
			if (task.getPriority().getId()==priorityID) return true;
		}
		return false;
	}
	
	private void initFields() {
		int priorityID = getIntent().getExtras().getInt(MainActivity.KEY_PRIORITY, -1);
		if (priorityID < 0) {
			newItem = true;
		} else {
			try {
				priority = db.getPriorityList(this, "id", priorityID).get(0);
			} catch (SQLException e) {
				Log.e("PriorityActivity.initFields", e.toString());
			}
			name.setText(priority.getName());
			value.setText(String.valueOf(priority.getValue()));
		}
	}
	
}

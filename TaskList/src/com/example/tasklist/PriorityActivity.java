package com.example.tasklist;

import java.sql.SQLException;

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
				// #####
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

		Priority priority = new Priority(priorityName, priorityValue);
		
		try {
			db.insert(this, priority);
		} catch (SQLException e) {
			Log.e("SQL-Error", "Konnte Priorität nicht ablegen", e);
		}

		finish();
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
			value.setText(priority.getValue());
		}
	}
	
}

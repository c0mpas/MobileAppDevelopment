package com.example.tasklist;

import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
				// #####
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// #####
			}
		});

		delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// #####
			}
		});
		
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.priority, menu);
		return true;
	}

}

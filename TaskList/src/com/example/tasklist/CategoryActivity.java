package com.example.tasklist;

import java.sql.SQLException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CategoryActivity extends Activity {

	private EditText name;
	private Button save;
	private Button cancel;
	private Button delete;
	
	private Category category;
	private Boolean newItem = false;
	private DBHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);
		
		referenceViews();
		setListeners();
		initFields();
	}

	private void referenceViews() {
		name = (EditText) findViewById(R.id.editTextName);
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
		int categoryID = getIntent().getExtras().getInt(MainActivity.KEY_PRIORITY, -1);
		if (categoryID < 0) {
			newItem = true;
		} else {
			try {
				category = db.getCategoryList(this, "id", categoryID).get(0);
			} catch (SQLException e) {
				Log.e("PriorityActivity.initFields", e.toString());
			}
			name.setText(category.getName());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}

}

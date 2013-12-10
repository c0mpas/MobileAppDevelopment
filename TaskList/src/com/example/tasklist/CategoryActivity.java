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

	private int categoryID;
	private Category category;
	private Boolean newItem = false;
	private DBHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_category);

		referenceViews();
		setListeners();
		tryLoadingEdit();
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
				DBHelper db = new DBHelper();
				if (newItem) {
					try {
						category = new Category(name.getText().toString());
						db.insert(getApplicationContext(), category);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {

					try {
						category.setName(name.getText().toString());
						db.update(getApplicationContext(), category);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
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
				DBHelper db = new DBHelper();
				try {
					db.delete(getApplicationContext(), category);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}
		});

	}


	private void tryLoadingEdit() {
		categoryID = getIntent().getExtras().getInt(MainActivity.KEY_CATEGORY,
				-1);
		if (categoryID != -1) {

			DBHelper db = new DBHelper();
			try {
				category = db.getCategoryList(this, "id", categoryID).get(0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newItem = false;

			name.setText(category.getName());
		} else {

			newItem = true;
			
		}
	}

}

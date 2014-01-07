package de.htwds.mada;

import java.sql.SQLException;
import java.util.List;

import com.example.tasklist.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
		db = new DBHelper();

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
						e.printStackTrace();
					}
				} else {

					try {
						category.setName(name.getText().toString());
						db.update(getApplicationContext(), category);
					} catch (SQLException e) {
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
				delete();
				finish();
			}
		});

	}

	private void delete() {
		List<Task> taskList = null;
		try {
			taskList = db.getAllTasks(this);
		} catch (SQLException e) {
			Log.e("SQL-Error", "Unable to load task list", e);
		}
		
		if (exists(taskList, category)) {
			Toast.makeText(this, R.string.message_delete_category, Toast.LENGTH_LONG).show();
		} else {
			try {
				db.delete(getApplicationContext(), category);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private Boolean exists(List<Task> taskList, Category category) {
		if (taskList==null || category==null) return false;
		int categoryID = category.getId();
		for (Task task : taskList) {
			if (task.getCategory().getId()==categoryID) return true;
		}
		return false;
	}
	
	private void tryLoadingEdit() {
		categoryID = getIntent().getExtras().getInt(MainActivity.KEY_CATEGORY, -1);
		if (categoryID != -1) {
			try {
				category = db.getCategoryList(this, Category.COLUMN_ID, categoryID).get(0);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			newItem = false;

			name.setText(category.getName());
		} else {

			newItem = true;
			
		}
	}

}

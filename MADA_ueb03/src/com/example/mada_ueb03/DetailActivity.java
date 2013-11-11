package com.example.mada_ueb03;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class DetailActivity extends Activity {

	private static final String SAVE_EDIT_TASK = "SaveEditTask";
	private EditText title;
	private EditText description;
	private Spinner priority;
	private Button save;
	private Button cancel;
	private ToDoTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		initViews();
		setListeners();

		if (savedInstanceState == null) {
			if (getIntent().getIntExtra(ToDoListActivity.CALL_TYPE, -1) == ToDoListActivity.REQUEST_CODE_EDIT) {
				updateViews((ToDoTask) getIntent().getExtras().getSerializable(
						ToDoListActivity.SEND_CODE_SERIALIZE));
			}else{
				task = new ToDoTask("", "", 1, getIntent().getIntExtra(ToDoListActivity.RECIEVE_CODE_ID, -1));
			}
		}
	}

	private void setListeners() {

		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				try {
					save();
				} catch (InvalidPrioException e) {
					Log.wtf(SAVE_EDIT_TASK, e);
				}
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				cancel();

			}
		});

		Integer[] prioArray = { 1, 2, 3 };

		priority.setAdapter(new PrioSpinnerAdapter(this,
				R.layout.prio_spinner_std, prioArray));

	}

	private void save() throws InvalidPrioException {
		Intent intent = new Intent();
		
		task.setTitle(title.getText().toString());
		task.setDescription(description.getText().toString());
		task.setPriority(priority.getSelectedItemPosition() + 1);
		
		intent.putExtra(ToDoListActivity.RECIEVE_TASK, task);
		setResult(RESULT_OK, intent);
		finish();
	}

	private void cancel() {
		setResult(RESULT_CANCELED);
		finish();
	}

	private void initViews() {

		save = (Button) findViewById(R.id.detail_bt_save);
		cancel = (Button) findViewById(R.id.detail_bt_cancel);
		title = (EditText) findViewById(R.id.edit_title);
		description = (EditText) findViewById(R.id.edit_description);
		priority = (Spinner) findViewById(R.id.spinner_priority);

	}

	private void updateViews(ToDoTask task) {

		this.task = task;
		
		title.setText(task.getTitle());
		description.setText(task.getDescription());
		priority.setSelection(task.getPriority() - 1);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_delete:
			Intent intent = new Intent();
			intent.putExtra(ToDoListActivity.RECIEVE_TASK, task);
			setResult(ToDoListActivity.RESULT_DELETE, intent);
			finish();
			
			break;
		default:
			break;
		}
		return true;
	}

}

package com.example.tasklist;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class CategoryActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadGroups();
		showList();
	}

	private void loadGroups() {
		// #####
	}

	private void showList() {
		// #####
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		// #####
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}

}

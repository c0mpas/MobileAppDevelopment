package com.example.tasklist;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class PriorityListActivity extends ListActivity {

	private static final int DIVIDER = 2;

	private ArrayList<Priority> priorityList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadPriorities();
		showList();
	}

	private void loadPriorities() {
		// #####
	}

	private void showList() {
		ListViewPriorityAdapter adapter = new ListViewPriorityAdapter(this, priorityList);
		setListAdapter(adapter);
		getListView().setDividerHeight(DIVIDER);
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		// #####
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.priority, menu);
		return true;
	}

}

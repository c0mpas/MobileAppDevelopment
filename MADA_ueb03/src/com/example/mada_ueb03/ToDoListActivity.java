package com.example.mada_ueb03;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class ToDoListActivity extends ListActivity {
	
	
	private ArrayList<ToDoTask> taskList;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		
		initComponents();
		showListView();
		
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
			
		
		
	}
	
	private void showListView(){
		Collections.sort(taskList);
		
		ListViewAdapterStd adapter = new ListViewAdapterStd(this ,taskList);
		setListAdapter(adapter);
		
		
	}
	
	
	private void initComponents(){
		
		taskList = new ArrayList<ToDoTask>();
		
		taskList.add(new ToDoTask("MyTitle", "Das ist eine ganz tolle Beschreibung.", 3));
		taskList.add(new ToDoTask("MyTitle2", "Das ist eine ganz tolle Beschreibung2.", 1));
		taskList.add(new ToDoTask("MyTitle3", "Das ist eine ganz tolle Beschreibung3.", 2));
		
		
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}

}


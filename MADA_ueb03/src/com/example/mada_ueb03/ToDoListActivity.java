package com.example.mada_ueb03;

import java.util.ArrayList;
import java.util.Collections;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class ToDoListActivity extends ListActivity {
	
	
	public final static String TODO_KEY = "toDoTask";
	private final static int RESULT_KEY = 666;
	
	private ArrayList<ToDoTask> taskList;
	private int IDCounter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loadTasks();
		taskList.add(new ToDoTask("Titel1", "Coole Beschreibung1,  Oh yeah!",2, 1));
		taskList.add(new ToDoTask("Titel2", "Coole Beschreibung2,  Oh yeah!",3, 1));
		taskList.add(new ToDoTask("Titel3", "Coole Beschreibung3,  Oh yeah!",1, 1));
		taskList.add(new ToDoTask("Titel4", "Coole Beschreibung4,  Oh yeah!",3, 1));
		taskList.add(new ToDoTask("Titel5", "Coole Beschreibung5,  Oh yeah!",2, 1));
		initIDCounter();

		
		showListView();
		
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
			
		editTask(position);
		
	}
	
	private void editTask(int taskPosition){
		
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(TODO_KEY, taskList.get(taskPosition));
		
		startActivityForResult(intent, RESULT_KEY);
		
	}
	
	private void initIDCounter(){
		IDCounter = 0;
		
		for(ToDoTask task : taskList){
			if(task.getID() >= IDCounter) 
				IDCounter = task.getID() + 1;
		}
	}
	
	private void showListView(){
		Collections.sort(taskList);
		
		ListViewAdapterStd adapter = new ListViewAdapterStd(this ,taskList);
		setListAdapter(adapter);
		
		
	}
	
	
	private void loadTasks(){
		
		taskList = new ArrayList<ToDoTask>();
		
		
		
	}
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}

}


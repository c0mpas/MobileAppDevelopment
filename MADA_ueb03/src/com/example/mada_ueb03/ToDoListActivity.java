package com.example.mada_ueb03;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class ToDoListActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
			
	}
	
	
	
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.to_do_list, menu);
		return true;
	}

}

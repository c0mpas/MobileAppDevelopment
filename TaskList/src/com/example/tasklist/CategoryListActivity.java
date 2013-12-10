package com.example.tasklist;

import java.sql.SQLException;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class CategoryListActivity extends ListActivity {

	private DBHelper db;
	private List<Category> categoryList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new DBHelper();
		loadCategories();
		showList();
	}

	private void loadCategories() {
		try {
			categoryList = db.getAllCategories(this);
		} catch (SQLException e) {
			Log.e("loadCategories", e.toString());
		}
	}

	private void showList() {
		ListViewCategoryAdapter adapter = new ListViewCategoryAdapter(this, categoryList);
		setListAdapter(adapter);
		getListView().setDividerHeight(MainActivity.DIVIDER);
	}

	@Override
	protected void onListItemClick(ListView listView, View view, int position, long id) {
		editCategory(position);
	}

	//Ruft neue Activity zum bearbeiten auf
	private void editCategory(int CategoryPosition) {
		Intent intent = new Intent(this, CategoryActivity.class);
		intent.putExtra(MainActivity.KEY_CATEGORY, categoryList.get(CategoryPosition).getId());
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.category, menu);
		return true;
	}

}

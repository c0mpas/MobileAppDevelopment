package com.example.tasklist;

import java.sql.SQLException;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class CategoryListActivity extends ListActivity {

	private List<Category> categoryList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadCategories();
		showList();
	}

	private void loadCategories() {
		// #####
	}

	private void showList() {
		DBHelper db = new DBHelper();
		try {
			categoryList = db.getAllCategories(this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

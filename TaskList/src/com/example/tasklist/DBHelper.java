
package com.example.tasklist;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class DBHelper {

	private OrmDbHelper databaseHelper = null;

	public OrmDbHelper getHelper(Context context) {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(context,
					OrmDbHelper.class);
		}
		return databaseHelper;
	}

	public void close() {
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	public List<Task> getAllTasks(Context context) throws SQLException {
		Log.i(Category.class.getName(), "Show list again");
		Dao<Task, Integer> dao = getHelper(context).createTodoDAO();
		QueryBuilder<Task, Integer> builder = dao.queryBuilder();
		List<Task> list = dao.query(builder.prepare());
		Log.d("CategoryDBHelper",
				"Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}

	
	public void insert(Context context, Task task) throws SQLException {
		Dao<Task, Integer> dao = getHelper(context).createTodoDAO();
		dao.create(task);
	}
	public void insert(Context context, Priority priority) throws SQLException {
		Dao<Priority, Integer> dao = getHelper(context).createPriorityDAO();
		dao.create(priority);
	}
	public void insert(Context context, Category category) throws SQLException {
		Dao<Category, Integer> dao = getHelper(context).createCategoryDAO();
		dao.create(category);
	}
	
	
	public void update(Context context, Task task) throws SQLException {
		Dao<Task, Integer> dao = getHelper(context).createTodoDAO();
		dao.update(task);
	}
	public void update(Context context, Priority priority) throws SQLException {
		Dao<Priority, Integer> dao = getHelper(context).createPriorityDAO();
		dao.update(priority);
	}
	public void update(Context context, Category category) throws SQLException {
		Dao<Category, Integer> dao = getHelper(context).createCategoryDAO();
		dao.update(category);
	}
	
	
	public void delete(Context context, Task task) throws SQLException {
		Dao<Task, Integer> dao = getHelper(context).createTodoDAO();
		dao.delete(task);
	}

	public List<Task> where(Context context, String column, Object value) throws SQLException {
		Dao<Task, Integer> dao = getHelper(context).createTodoDAO();
		List<Task> tasks = dao.queryBuilder().where().eq(column, value).query();
		return tasks;
	}

}

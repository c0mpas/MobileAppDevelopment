
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
			databaseHelper = OpenHelperManager.getHelper(context, OrmDbHelper.class);
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
		Dao<Task, Integer> dao = getHelper(context).createTaskDAO();
		QueryBuilder<Task, Integer> builder = dao.queryBuilder();
		List<Task> list = dao.query(builder.prepare());
		Log.d("TaskDBHelper", "Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}
	public List<Priority> getAllPriorities(Context context) throws SQLException {
		Log.i(Priority.class.getName(), "Show list again");
		Dao<Priority, Integer> dao = getHelper(context).createPriorityDAO();
		QueryBuilder<Priority, Integer> builder = dao.queryBuilder();
		List<Priority> list = dao.query(builder.prepare());
		Log.d("PriorityDBHelper", "Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}
	public List<Category> getAllCategories(Context context) throws SQLException {
		Log.i(Category.class.getName(), "Show list again");
		Dao<Category, Integer> dao = getHelper(context).createCategoryDAO();
		QueryBuilder<Category, Integer> builder = dao.queryBuilder();
		List<Category> list = dao.query(builder.prepare());
		Log.d("PriorityDBHelper", "Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}

	
	public void insert(Context context, Task task) throws SQLException {
		Dao<Task, Integer> dao = getHelper(context).createTaskDAO();
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
		Dao<Task, Integer> dao = getHelper(context).createTaskDAO();
		dao.createOrUpdate(task);
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
		Dao<Task, Integer> dao = getHelper(context).createTaskDAO();
		dao.delete(task);
	}
	public void delete(Context context, Priority priority) throws SQLException {
		Dao<Priority, Integer> dao = getHelper(context).createPriorityDAO();
		dao.delete(priority);
	}
	public void delete(Context context, Category category) throws SQLException {
		Dao<Category, Integer> dao = getHelper(context).createCategoryDAO();
		dao.delete(category);
	}
	

	public List<Task> getTaskList(Context context, String column, Object value) throws SQLException {
		Dao<Task, Integer> dao = getHelper(context).createTaskDAO();
		List<Task> tasks = dao.queryBuilder().where().eq(column, value).query();
		return tasks;
	}
	public List<Priority> getPriorityList(Context context, String column, Object value) throws SQLException {
		Dao<Priority, Integer> dao = getHelper(context).createPriorityDAO();
		List<Priority> priorities = dao.queryBuilder().where().eq(column, value).query();
		return priorities;
	}
	public List<Category> getCategoryList(Context context, String column, Object value) throws SQLException {
		Dao<Category, Integer> dao = getHelper(context).createCategoryDAO();
		List<Category> categories = dao.queryBuilder().where().eq(column, value).query();
		return categories;
	}

}

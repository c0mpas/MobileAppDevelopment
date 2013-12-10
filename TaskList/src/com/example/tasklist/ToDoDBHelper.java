
package com.example.tasklist;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

public class ToDoDBHelper {

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

	public List<ToDoTask> getAll(Context context) throws SQLException {
		Log.i(Category.class.getName(), "Show list again");
		Dao<ToDoTask, Integer> dao = getHelper(context).createTodoDAO();
		QueryBuilder<ToDoTask, Integer> builder = dao.queryBuilder();
		List<ToDoTask> list = dao.query(builder.prepare());
		Log.d("CategoryDBHelper",
				"Liste geladen, Anzahl Elemente: " + list.size());
		return list;
	}

	public void insert(Context context, ToDoTask task) throws SQLException {
		Dao<ToDoTask, Integer> dao = getHelper(context).createTodoDAO();
		dao.create(task);
	}
	
	public void update(Context context, ToDoTask task) throws SQLException {
		Dao<ToDoTask, Integer> dao = getHelper(context).createTodoDAO();
		dao.update(task);
	}

	
	public void delete(Context context, ToDoTask task) throws SQLException {
		Dao<ToDoTask, Integer> dao = getHelper(context).createTodoDAO();
		dao.delete(task);
	}

	public List<ToDoTask> get(Context context, String column, Object value)
			throws SQLException {
		Dao<ToDoTask, Integer> dao = getHelper(context).createTodoDAO();
		List<ToDoTask> tasks = dao.queryBuilder().where()
				.eq(column, value).query();
		return tasks;
	}

}

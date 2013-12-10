package com.example.tasklist;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class OrmDbHelper extends OrmLiteSqliteOpenHelper {

	public static final String LOG = OrmDbHelper.class.getName();
	public static final String DB_NAME = "todo.db";
	public static final int DB_VERSION = 1;

	public OrmDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource source) {
		// Registrierung der Klassen beim ORM Framework
		try { 
			TableUtils.createTable(source, Priorität.class);
			TableUtils.createTable(source, Kategorie.class);
			TableUtils.createTable(source, ToDoTask.class);
			TableUtils.createTable(source, TaskBuffer.class);
		} catch (SQLException ex) {
			Log.e(LOG, "error creating tables", ex);
		}

	}

	public Dao<ToDoTask, Integer> createTodoDAO() {
		try {
			return DaoManager.createDao(connectionSource, ToDoTask.class);
		} catch (SQLException ex) {
			Log.e(LOG, "error creating DAO for ToDoTask class", ex);
		}
		return null;
	}

	public Dao<Kategorie, Integer> createKategorieDAO() {
		try {
			return DaoManager.createDao(connectionSource, Kategorie.class);
		} catch (SQLException ex) {
			Log.e(LOG, "error creating DAO for Kategorie class", ex);
		}
		return null;
	}

	public Dao<Priorität, Integer> createPrioritätDAO() {
		try {
			return DaoManager.createDao(connectionSource, Priorität.class);
		} catch (SQLException ex) {
			Log.e(LOG, "error creating DAO for Priorität class", ex);
		}
		return null;
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
	
	}

}

package com.example.tasklist;

import java.util.Arrays;
import java.util.HashSet;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class ToDoContentProvider extends ContentProvider {

	// database
	private OrmDbHelper database;

	// used for the UriMacher
	private static final int TASK = 10;
	private static final int TASK_ID = 20;

	private static final String AUTHORITY = "de.vogella.android.todos.contentprovider";

	private static final String BASE_PATH = "todos";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/todos";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/todo";

	private static final UriMatcher sURIMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, TASK);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TASK_ID);
	}

	@Override
	public boolean onCreate() {
		database = new OrmDbHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		
		Cursor cursor = null;
		SQLiteDatabase db = database.getWritableDatabase();
		
		// Uisng SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// check if the caller has requested a column which does not exists
		checkColumns(projection);

		// Set the table

		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case TASK:
			cursor = db.query(Task.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case TASK_ID:
			//Task für id
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

	
		// make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		long id = 0;
		switch (uriType) {
		case TASK:
			id = sqlDB.insert(TodoTable.TABLE_TODO, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case TASK:
			rowsDeleted = sqlDB.delete(TodoTable.TABLE_TODO, selection,
					selectionArgs);
			break;
		case TASK_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(TodoTable.TABLE_TODO,
						TodoTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(TodoTable.TABLE_TODO,
						TodoTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case TASK:
			rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO, values, selection,
					selectionArgs);
			break;
		case TASK_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO, values,
						TodoTable.COLUMN_ID + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(TodoTable.TABLE_TODO, values,
						TodoTable.COLUMN_ID + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
		String[] available = { Task.COLUMN_DAY, Task.COLUMN_DESCRIPTION,
				Task.COLUMN_ID, Task.COLUMN_MONTH, Task.COLUMN_PRIORITY,
				Task.COLUMN_TITLE, Task.COLUMN_YEAR };
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(available));
			// check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(
						getContext().getString(
								R.string.unknown_columns_in_projection));
			}
		}
	}

}

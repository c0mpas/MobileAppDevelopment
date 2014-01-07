package de.htwds.mada;

import java.util.Arrays;
import java.util.HashSet;

import com.example.tasklist.R;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class ToDoContentProvider extends ContentProvider {

	// database
	private OrmDbHelper database;

	// used for the UriMacher
	private static final int TASK = 10;
	private static final int TASK_ID = 20;
	private static final int PRIORITY = 30;
	private static final int PRIORITY_ID = 40;

	private static final String AUTHORITY = "de.htwds.mada.todo";

	private static final String BASE_PATH_TODO = "todos";
	private static final String BASE_PATH_PRIO = "priorities";

	
	public static final Uri CONTENT_URI_TODO = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_TODO);
	public static final Uri CONTENT_URI_PRIO = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_PRIO);


	private static UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_PRIO + "/#", PRIORITY_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_PRIO, PRIORITY);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TODO + "/#", TASK_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TODO, TASK);
	}

	/* Contract Klasse für die Todo Tabelle */
	public static final class Todos implements BaseColumns {
		// Erweiterung der Contract Klasse um MIME Type Konstanten "
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.de.htwds.mada.todo";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.de.htwds.mada.todo";

	}

	public static final class Priorityies implements BaseColumns {
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.de.htwds.mada.priority";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.de.htwds.mada.priority";
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
		SQLiteDatabase db = database.getReadableDatabase();

		// check if the caller has requested a column which does not exists
		checkColumns(projection, uri);

		String selCommand;

		int uriType = sURIMatcher.match(uri);
		
		Log.d("TEST", "query():uriType:"+String.valueOf(uriType));
		
		switch (uriType) {
		case TASK:
			cursor = db.query(Task.TABLE_NAME, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		case TASK_ID:
			selCommand = Task.COLUMN_ID + "=" + ContentUris.parseId(uri);
			if (selection != null)
				selCommand += " AND " + selection;
			cursor = db.query(Task.TABLE_NAME, projection, selCommand,
					selectionArgs, null, null, sortOrder);
			break;
		case PRIORITY:
			cursor = db.query(Priority.TABLE_NAME, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		case PRIORITY_ID:
			
			Log.d("TEST","URI-Id: "+ String.valueOf(ContentUris.parseId(uri)));
			selCommand = Task.COLUMN_ID + "=" + ContentUris.parseId(uri);
			if (selection != null)
				selCommand += " AND " + selection;
			cursor = db.query(Priority.TABLE_NAME, projection, selCommand,
					selectionArgs, null, null, sortOrder);
			Log.d("TEST","CURSER-ID: "+ cursor.toString());

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
		// URI Muster auswerten"
		int uriCode = sURIMatcher.match(uri);
		
		Log.d("TEST", "getType():uriCode:"+String.valueOf(uriCode));
		
		// entsprechend dem URI MIME type zurückgeben"
		switch (uriCode) {
		case TASK:
			return Todos.CONTENT_TYPE;
		case TASK_ID:
			return Todos.CONTENT_ITEM_TYPE;
		case PRIORITY:
			return Priorityies.CONTENT_TYPE;
		case PRIORITY_ID:
			return Priorityies.CONTENT_ITEM_TYPE;
		default:
			return null;
		}

	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		String basePath = "failure";
		long id = -1;
		switch (uriType) {
		case TASK:
			id = sqlDB.insert(Task.TABLE_NAME, null, values);
			basePath = BASE_PATH_TODO;
			break;
		case PRIORITY:
			id = sqlDB.insert(Priority.TABLE_NAME, null, values);
			basePath = BASE_PATH_PRIO;

			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(basePath + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		String id;
		switch (uriType) {
		case TASK:
			rowsDeleted = sqlDB.delete(Task.TABLE_NAME, selection,
					selectionArgs);
			break;
		case TASK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(Task.TABLE_NAME, Task.TABLE_NAME
						+ "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(Task.TABLE_NAME, Task.TABLE_NAME
						+ "=" + id + " and " + selection, selectionArgs);
			}
			break;
		case PRIORITY:
			rowsDeleted = sqlDB.delete(Priority.TABLE_NAME, selection,
					selectionArgs);
			break;
		case PRIORITY_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(Priority.TABLE_NAME,
						Priority.TABLE_NAME + "=" + id, null);
			} else {
				rowsDeleted = sqlDB.delete(Priority.TABLE_NAME,
						Priority.TABLE_NAME + "=" + id + " and " + selection,
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
		String id;
		switch (uriType) {
		case TASK:
			rowsUpdated = sqlDB.update(Task.TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case TASK_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(Task.TABLE_NAME, values,
						Task.TABLE_NAME + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(Task.TABLE_NAME, values,
						Task.TABLE_NAME + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		case PRIORITY:
			rowsUpdated = sqlDB.update(Priority.TABLE_NAME, values, selection,
					selectionArgs);
			break;
		case PRIORITY_ID:
			id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(Priority.TABLE_NAME, values,
						Priority.TABLE_NAME + "=" + id, null);
			} else {
				rowsUpdated = sqlDB.update(Task.TABLE_NAME, values,
						Priority.TABLE_NAME + "=" + id + " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection, Uri uri) {

		int uriType = sURIMatcher.match(uri);
		String[] available = { "Shit happens" };
		
		Log.d("TEST", "checkColumns():uri:"+uri.toString());
		Log.d("TEST", "checkColumns():uriType:"+String.valueOf(uriType));

		switch (uriType) {
		case TASK:
			String[] temp1 = { Task.COLUMN_DAY, Task.COLUMN_DESCRIPTION,
					Task.COLUMN_ID, Task.COLUMN_MONTH, Task.COLUMN_PRIORITY,
					Task.COLUMN_TITLE, Task.COLUMN_YEAR };
			available = temp1;
			break;
		case TASK_ID:
			String[] temp2 = { Task.COLUMN_DAY, Task.COLUMN_DESCRIPTION,
					Task.COLUMN_ID, Task.COLUMN_MONTH, Task.COLUMN_PRIORITY,
					Task.COLUMN_TITLE, Task.COLUMN_YEAR };
			available = temp2;
			break;
		case PRIORITY:
			String[] temp3 = { Priority.COLUMN_NAME, Priority.COLUMN_ID,
					Priority.COLUMN_VALUE };
			available = temp3;
			break;
		case PRIORITY_ID:
			String[] temp4 = { Priority.COLUMN_NAME, Priority.COLUMN_ID,
					Priority.COLUMN_VALUE };
			available = temp4;
			break;
		default:
			break;
		}

		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(
					Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(
					Arrays.asList(available));
			// check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException(getContext().getString(
						R.string.unknown_columns_in_projection));
			}
		}
	}

}

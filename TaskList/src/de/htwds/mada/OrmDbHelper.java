package de.htwds.mada;

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
	public static final int DB_VERSION = 3;

	private Dao<Category, Integer> categoryDao = null;
	private Dao<Task, Integer> toDoDao = null;
	private Dao<Priority, Integer> prioDao = null;

	public OrmDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource source) {
		// Registrierung der Klassen beim ORM Framework

		try {
			TableUtils.createTable(source, Priority.class);
			TableUtils.createTable(source, Category.class);
			TableUtils.createTable(source, Task.class);
		} catch (SQLException ex) {
			Log.e(LOG, "error creating tables", ex);
		}

	}

	public Dao<Task, Integer> createTaskDAO() throws SQLException {
		if (toDoDao == null) {
			toDoDao = getDao(Task.class);
		}
		return toDoDao;
	}

	public Dao<Category, Integer> createCategoryDAO() throws SQLException {
		if (categoryDao == null) {
			categoryDao = getDao(Category.class);
		}
		return categoryDao;
	}

	public Dao<Priority, Integer> createPriorityDAO() throws SQLException {
		if (prioDao == null) {
			prioDao = getDao(Priority.class);
		}
		return prioDao;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVer, int newVer) {

		try {

			TableUtils.dropTable(connectionSource, Category.class, true);
			TableUtils.dropTable(connectionSource, Task.class, true);
			TableUtils.dropTable(connectionSource, Priority.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e("fuckit", "error upgrading db " + newVer + "from ver "
					+ oldVer);
			throw new RuntimeException(e);
		}

	}

	@Override
    public void close() {
        super.close();
        categoryDao = null;
    	toDoDao = null;
    	prioDao = null;
    }

}

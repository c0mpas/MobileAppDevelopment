package com.example.tasklist;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DBManager {
	 
    private OrmDbHelper DBHelper = null;
 
    // Gets a helper once one is created ensures it doesnt create a new one
    public OrmDbHelper getHelper(Context context) {
        if (DBHelper == null) DBHelper = OpenHelperManager.getHelper(context, OrmDbHelper.class);
        return DBHelper;
    }
 
    // Releases the helper once usages has ended
    public void releaseHelper(OrmDbHelper helper) {
        if (DBHelper != null) {
            OpenHelperManager.releaseHelper();
            DBHelper = null;
        }
    }
 
}
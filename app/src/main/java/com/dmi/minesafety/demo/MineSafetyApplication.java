package com.dmi.minesafety.demo;

import com.dmi.minesafety.demo.greendao.DaoMaster;
import com.dmi.minesafety.demo.greendao.DaoSession;
import com.splunk.mint.Mint;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import de.greenrobot.dao.query.QueryBuilder;

public class MineSafetyApplication extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Mint.initAndStartSession(this, "90254e95");
        setupDatabase();

    }


    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,
                "MineSafetyDemo-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        if (BuildConfig.DEBUG) {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        }

    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


}

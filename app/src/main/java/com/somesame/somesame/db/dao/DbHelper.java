package com.somesame.somesame.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.somesame.somesame.base.MyApplication;
import com.somesame.somesame.db.greendao.DaoMaster;
import com.somesame.somesame.db.greendao.DaoSession;

/**
 * 数据库辅助类
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/8/16
 */
public class DbHelper {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public DbHelper(){
        mHelper = new DaoMaster.DevOpenHelper(MyApplication.getApplication(), "veer-db");
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
    public void closeDb() {
        if (db != null) {
            db.close();
        }
    }

}

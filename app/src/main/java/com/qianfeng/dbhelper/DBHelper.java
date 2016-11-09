package com.qianfeng.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/18.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "content.db";
    private static final int DBVERSION = 1;

    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists contents(_id Integer primary key autoincrement," +
                "title text," +
                "other text," +
                "url text," +
                "image text" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<newVersion){
            db.execSQL("drop table contents");
            onCreate(db);
        }
    }
}

package com.qianfeng.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.qianfeng.dbhelper.DBHelper;

/**
 * Created by Administrator on 2016/10/20.
 */

public class Del {
    private DBHelper helper;
    private SQLiteDatabase db;
    public void utilDel(String id, Context context){
         helper = new DBHelper(context);
        db = helper.getReadableDatabase();
        db.delete("contents","_id=?",new String[]{id});
    }
}

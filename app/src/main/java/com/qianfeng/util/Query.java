package com.qianfeng.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.qianfeng.dbhelper.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/18.
 */

public class Query {

    public List<Map<String ,String>> utilQuery(Context context){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("contents",null,null,null,null,null,null);
        List<Map<String ,String>> list = new ArrayList<>();
        while (cursor.moveToNext()){
            Map<String,String> map = new HashMap<>();
            map.put("id",cursor.getInt(cursor.getColumnIndex("_id"))+"");
            map.put("title",cursor.getString(cursor.getColumnIndex("title")));
            map.put("other",cursor.getString(cursor.getColumnIndex("other")));
            map.put("url",cursor.getString(cursor.getColumnIndex("url")));
            map.put("image",cursor.getString(cursor.getColumnIndex("image")));
            list.add(map);
        }
        Log.i("-=-=-=-=",list.toString());
        return list;
    }
}

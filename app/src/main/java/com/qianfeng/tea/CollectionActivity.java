package com.qianfeng.tea;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.qianfeng.adapter.CollectionAdapter;
import com.qianfeng.dbhelper.DBHelper;
import com.qianfeng.util.Query;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionActivity extends AppCompatActivity {
    private ListView listView;
    private CollectionAdapter adapter;
    private List<Map<String,String>> list;
    private DBHelper helper;
    private SQLiteDatabase db;
    private Query query;
    //判断是否checkbox是否显示出来
    private boolean isMultiSelect = false;
    private TextView tvDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        tvDel = (TextView) findViewById(R.id.tvDel);
        listView = (ListView) findViewById(R.id.lvCollection);
        list = new ArrayList<>();
        //工具查询
        query = new Query();
        list.addAll(query.utilQuery(this));
        for (int i =0;i<list.size();i++){
            list.get(i).put("isMultiSelect",isMultiSelect+"");
        }
        //
        adapter = new CollectionAdapter(list,this,isMultiSelect);
        listView.setAdapter(adapter);
        //监听收藏
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> list2 = new ArrayList<String>();
                Map<String,String> map = list.get(position);
                for (int i= 0;i<map.size();i++){
                    list2.add(map.get("title"));
                    list2.add(map.get("url"));
                    list2.add(map.get("image"));
                    list2.add(map.get("other"));
                }
                Intent intent = new Intent(CollectionActivity.this,WebViewActivity.class);
                intent.putStringArrayListExtra("list",list2);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                /*Dialog dialog = new AlertDialog.Builder(CollectionActivity.this).setTitle("确定么")
                        .setNegativeButton("确定删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String id = list.get(position).get("id");
                                helper = new DBHelper(CollectionActivity.this);
                                db = helper.getReadableDatabase();
                                db.delete("contents","_id=?",new String[]{id});
                                list.clear();
                                list.addAll(query.utilQuery(CollectionActivity.this));
                                adapter.notifyDataSetChanged();
                            }
                        }).show();*/
                tvDel.setVisibility(View.VISIBLE);
                isMultiSelect = true;
                for (int i =0;i<list.size();i++){
                    list.get(i).put("isMultiSelect",isMultiSelect+"");
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });

    }
    public void backHome(View view){
        //点到就返回
        this.finish();
    }
    public void del(View view){
        tvDel.setVisibility(View.GONE);
        isMultiSelect = false;
        for (int i =0;i<list.size();i++){
            list.get(i).put("isMultiSelect",isMultiSelect+"");
        }
        adapter.notifyDataSetChanged();

        query = new Query();
        Log.i("sql",query.utilQuery(this).toString());
        list.clear();
        list.addAll(query.utilQuery(this));
        for (int i =0;i<list.size();i++){
            list.get(i).put("isMultiSelect",isMultiSelect+"");
        }
        adapter.notifyDataSetChanged();
    }
}

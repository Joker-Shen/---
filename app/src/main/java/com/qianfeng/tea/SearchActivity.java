package com.qianfeng.tea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qianfeng.callbackinterface.CallBackInterface;
import com.qianfeng.adapter.LVAdapter1;
import com.qianfeng.asynctask.ListViewAsynvTask;
import com.qianfeng.fragment.SecondFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements CallBackInterface {
    private ListView listView;
    private LVAdapter1 lvAdapter;
    private TextView textView;
    private ImageView ivfoot;
    private List<Map<String,String>> listLv;
    private int count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.lvSearch);
        textView = (TextView) findViewById(R.id.tvSearchTitle);
        //
        View vpfoot = LayoutInflater.from(this).inflate(R.layout.listview_foot,null);
        ivfoot = (ImageView) vpfoot.findViewById(R.id.ivfoot);
        ivfoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListViewAsynvTask(SearchActivity.this).execute("http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&type=16&rows=15&page="+(count+1));
            }
        });
        listView.addFooterView(vpfoot);
        //

        listLv = new ArrayList<>();
        lvAdapter = new LVAdapter1(listLv,this);
        listView.setAdapter(lvAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, WebViewActivity.class);
                intent.putExtra("url",listLv.get(position).get("id"));
                Map<String,String> map = listLv.get(position);
                ArrayList<String> listWeb = new ArrayList<>();

                for (int i=0;i<map.size();i++){
                    listWeb.add(map.get("title"));
                    listWeb.add(map.get("id"));
                    listWeb.add(map.get("image"));
                    listWeb.add(map.get("other"));
                }
                intent.putStringArrayListExtra("list",listWeb);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        textView.setText("您搜索的《"+intent.getStringExtra("search")+"》结果如下：");
        new ListViewAsynvTask(this).execute("http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.searcListByTitle&search="+intent.getStringExtra("search"));



    }

    @Override
    public void getCallBack(List<Map<String, String>> list) {
        Log.i("33333",list.toString());
        listLv.addAll(list);
        lvAdapter.notifyDataSetChanged();
    }
    public void backHome(View view){
        //点到就返回
        this.finish();
    }

}

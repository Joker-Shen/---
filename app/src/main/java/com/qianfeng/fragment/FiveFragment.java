package com.qianfeng.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.qianfeng.callbackinterface.CallBackInterface;
import com.qianfeng.adapter.LVAdapter1;
import com.qianfeng.asynctask.ListViewAsynvTask;
import com.qianfeng.tea.R;
import com.qianfeng.tea.WebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FiveFragment extends Fragment implements CallBackInterface {
    private LVAdapter1 lvAdapter;
    private List<Map<String,String>> listLv;
    private ImageView ivfoot;
    private int count = 1;
    private View view;
    public FiveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            return view;
        }

        view = inflater.inflate(R.layout.fragment_second,container,false);
        //
        View vpfoot = LayoutInflater.from(getActivity()).inflate(R.layout.listview_foot,null);
        ivfoot = (ImageView) vpfoot.findViewById(R.id.ivfoot);
        ivfoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListViewAsynvTask(FiveFragment.this,getActivity()).execute("http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&type=52&rows=15&page="+(count+1));
            }
        });
        //
        ListView listView = (ListView) view.findViewById(R.id.LvAll);
        listView.addFooterView(vpfoot);
        listLv = new ArrayList<>();
        lvAdapter = new LVAdapter1(listLv,getContext());
        listView.setAdapter(lvAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
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
        new ListViewAsynvTask(this).execute("http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getListByType&type=54");

        return view;
    }
    @Override
    public void getCallBack(List<Map<String, String>> list) {
        listLv.addAll(list);
        lvAdapter.notifyDataSetChanged();
    }
}

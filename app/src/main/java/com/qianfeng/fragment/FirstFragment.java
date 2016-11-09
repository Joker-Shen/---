package com.qianfeng.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.qianfeng.callbackinterface.CallBackInterface;
import com.qianfeng.adapter.AdapterFragmentAdapter;
import com.qianfeng.adapter.LVAdapter1;
import com.qianfeng.asynctask.ListViewAsynvTask;
import com.qianfeng.tea.R;
import com.qianfeng.tea.WebViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment implements CallBackInterface{
    private ViewPager viewPager;
    private ListView listView;
    private LinearLayout linearLayout;
    private List<ImageView> listPic;
    private LVAdapter1 lvAdapter;
    private ImageView ivfoot;
    private List<Fragment> listAdert;
    private List<Map<String,String>> listLv;
    private AdapterFragmentAdapter adapterFragmentAdapter;
    private ProgressDialog dialog;
    private int count = 1;
    private float left;
    private float right;
    private View view;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    dialog.dismiss();
                    List<Map<String,String>> list = (List<Map<String, String>>) msg.obj;
                    for (int i = 0 ; i<list.size();i++){
                        String utlPic = list.get(i).get("urlPic");
                        String title = list.get(i).get("title");
                        String id = list.get(i).get("id");
                        AderFragment aderFragment = new AderFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("urlPic",utlPic);
                        bundle.putString("title",title);
                        bundle.putString("id",id);
                        Log.i("---------2 ",bundle.toString());
                        aderFragment.setArguments(bundle);
                        listAdert.add(aderFragment);
                    }
                    adapterFragmentAdapter.notifyDataSetChanged();
                    break;
            }
            return false;
        }
    });

    public FirstFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //不重复刷新
        if (view != null) {
            return view;
        }
        //
        view = inflater.inflate(R.layout.fragment_first,container,false);
        //listview头布局渲染
        View vphead = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_layout,null);
        //
        View vpfoot = LayoutInflater.from(getActivity()).inflate(R.layout.listview_foot,null);
        ivfoot = (ImageView) vpfoot.findViewById(R.id.ivfoot);
        ivfoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListViewAsynvTask(FirstFragment.this,getActivity()).execute("http://sns.maimai" +
                        "cha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&meth" +
                        "od=news.getHeadlines&rows=15&page="+(count+1));
            }
        });
        //
        linearLayout = (LinearLayout) vphead.findViewById(R.id.llAdertDot);
        viewPager = (ViewPager) vphead.findViewById(R.id.vpFirst);
        listView = (ListView) view.findViewById(R.id.lvFirst);
        //adapter之前添加头布局
        listView.addHeaderView(vphead);
        listView.addFooterView(vpfoot);
        //广告：
        listPic = new ArrayList<>();
        listAdert = new ArrayList<>();
        adapterFragmentAdapter = new AdapterFragmentAdapter(getFragmentManager(),listAdert);
        viewPager.setAdapter(adapterFragmentAdapter);
        for (int i =0;i<3;i++){
            final ImageView ivdot = new ImageView(getActivity());
            ivdot.setImageResource(R.drawable.dot_item);
            ivdot.setEnabled(true);
            ivdot.setTag(i);
            ivdot.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           viewPager.setCurrentItem(Integer.parseInt(v.getTag().toString()));
                        }
                    }
            );
            ivdot.setLayoutParams(new LinearLayout.LayoutParams(70,70));
            /*ViewGroup.LayoutParams params = ivdot.getLayoutParams();
            params.height = 20;
            params.width = 20;*/

            linearLayout.addView(ivdot);
            linearLayout.getChildAt(0).setEnabled(false);
        }
        //linearLayout.getChildAt(0).setEnabled(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                for (int i =0;i<linearLayout.getChildCount();i++){
                    linearLayout.getChildAt(i).setEnabled(true);
                }
                linearLayout.getChildAt(position).setEnabled(false);
                /*viewPager.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction()==MotionEvent.ACTION_DOWN){
                            left = event.getX();
                        }else{
                            right = event.getX();
                            if (position==2&&right-left>50){
                                viewPager.setCurrentItem(0);
                            }
                        }
                        return false;
                    }
                });*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //listView
        listLv = new ArrayList<>();
        lvAdapter = new LVAdapter1(listLv,getActivity());
        listView.setAdapter(lvAdapter);
        //监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                Log.i("22222244",listLv.get(position-1).get("id"));
                intent.putExtra("url",listLv.get(position-1).get("id"));
                Map<String,String> map = listLv.get(position-1);
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
        //从网络获取listview
        new ListViewAsynvTask(this).execute("http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getHeadlines");
        //从网络获取广告1
        //
        dialog = new ProgressDialog(getActivity());
        dialog.setTitle("正在加载广告");
        dialog.setMessage("不要慌_(:з」∠)_");
        dialog.setProgressStyle(dialog.STYLE_SPINNER);
        dialog.show();
        //
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Map<String,String>> listAdert1 = new ArrayList<Map<String, String>>();
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getSlideshow").build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONObject object = new JSONObject(response.body().string());
                    JSONArray array = object.optJSONArray("data");
                    for (int i = 0; i < array.length(); i++){
                        JSONObject object1 = array.optJSONObject(i);
                        String urlPic = object1.optString("image");
                        String title = object1.optString("title");
                        String id = object1.optString("id");
                        Map<String,String> map = new HashMap<String, String>();
                        map.put("urlPic",urlPic);
                        map.put("title",title);
                        map.put("id",id);
                        listAdert1.add(map);
                    }
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = listAdert1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return view;
    }


    @Override
    public void getCallBack(List<Map<String, String>> list) {
        Log.i("33333",list.toString());
        listLv.addAll(list);
        lvAdapter.notifyDataSetChanged();
        //listView.addHeaderView(viewPager);
    }
}

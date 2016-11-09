package com.qianfeng.tea;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.dbhelper.DBHelper;
import com.qianfeng.util.Query;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private String url;
    private ProgressDialog dialog;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            webView.loadUrl(msg.obj.toString());
            //利用handler关闭进度弹窗_(:з」∠)_ 好像哪里不太对
            dialog.dismiss();
            return false;
        }
    });
    private ArrayList<String> list;
    private SQLiteDatabase db;
    private DBHelper helper;
    private TextView tvtitle;
    private TextView tvother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        helper = new DBHelper(this);
        db=helper.getReadableDatabase();
        //
        tvtitle = (TextView) findViewById(R.id.webTitle);
        tvother = (TextView) findViewById(R.id.webOther);

        //

        webView = (WebView) findViewById(R.id.web);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);


        list = new ArrayList<>();
        Intent intent = getIntent();
        list.clear();
        list.addAll(intent.getStringArrayListExtra("list"));
        tvother.setText(list.get(3));
        tvtitle.setText(list.get(0));
        if(!list.get(1).equals("")){
            url = "http://sns.maimaicha.com/api?apikey=b4f4ee31a8b9acc866ef2afb754c33e6&format=json&method=news.getNewsContent&id="+list.get(1);
            Log.i("2244222244",url);
        }else{
            url = "http://sns.maimaicha.com/news/detail/5695";
            Toast.makeText(this, "您点击的页面不可用或正在维护", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        //
        dialog = new ProgressDialog(this);
        dialog.setTitle("正在加载网页");
        dialog.setMessage("不要慌_(:з」∠)_");
        dialog.setProgressStyle(dialog.STYLE_SPINNER);
        dialog.show();
        //
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = client.newCall(request).execute();
                    JSONObject object = new JSONObject(response.body().string());
                    JSONObject object1 = object.optJSONObject("data");
                    String url1 = object1.getString("weiboUrl");
                    Log.i("2244222244",url1);
                    //貌似这里直接用webview？
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = url1;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void ivback(View view){
        this.finish();
    }
    public void ivshare(View view){
        Toast.makeText(this, "不会没学", Toast.LENGTH_SHORT).show();
    }
    public void ivcontent(View view){
        Query query = new Query();
        int flag = 0;
        List<Map<String,String>> listQuery = query.utilQuery(this);
        for (int i = 0;i<listQuery.size();i++){
            if(listQuery.get(i).get("title").equals(list.get(0))){
                flag=1;
            }
        }
        if (flag==0){
            db = helper.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("title",list.get(0));
            values.put("url",list.get(1));
            values.put("image",list.get(2));
            values.put("other",list.get(3));
            long result = db.insert("contents",null,values);
            if(result>0){
                Toast.makeText(this, "成功收藏", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "已经收藏过了", Toast.LENGTH_SHORT).show();
        }
    }
}

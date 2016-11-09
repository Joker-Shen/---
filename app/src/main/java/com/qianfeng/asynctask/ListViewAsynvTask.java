package com.qianfeng.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.qianfeng.callbackinterface.CallBackInterface;

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
 * Created by Administrator on 2016/10/18.
 */

public class ListViewAsynvTask extends AsyncTask<String,Integer,List<Map<String,String>>> {
    private CallBackInterface callBackInterface;
    private Context context = null;
    private ProgressDialog dialog;
    public ListViewAsynvTask(CallBackInterface callBackInterface,Context context) {
        this.callBackInterface = callBackInterface;
        this.context = context;
    }

    public ListViewAsynvTask(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    @Override
    protected List<Map<String, String>> doInBackground(String... params) {
        List<Map<String, String>> list= new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(params[0]).build();
        try {
            Response response = client.newCall(request).execute();
            JSONObject obj1 = new JSONObject(response.body().string());
            JSONArray array1 = obj1.optJSONArray("data");
            for (int i = 0;i < array1.length();i++){
                JSONObject obj2 = array1.getJSONObject(i);
                String title = obj2.optString("title");
                String other = obj2.optString("source")+"   "+obj2.optString("nickname")+" "+obj2.optString("create_time");
                String url = obj2.optString("wap_thumb");
                String id = obj2.optString("id");
                Map<String,String> map = new HashMap<>();
                map.put("title",title);
                map.put("other",other);
                map.put("image",url);
                map.put("id",id);
                list.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("11111",list.toString());
        return list;
    }

    @Override
    protected void onPreExecute() {
        if(context!=null){

            dialog = new ProgressDialog(context);
            dialog.setTitle("_(:з」∠)_");
            dialog.setMessage("正在下载 不要慌");
            dialog.setProgressStyle(dialog.STYLE_SPINNER);
            dialog.show();
        }
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        dialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(List<Map<String, String>> maps) {
        if (context!=null){

            dialog.dismiss();
        }
        Log.i("111112",maps.toString());
        callBackInterface.getCallBack(maps);
        super.onPostExecute(maps);
    }
}

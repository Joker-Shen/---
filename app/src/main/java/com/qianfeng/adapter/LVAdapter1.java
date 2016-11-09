package com.qianfeng.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianfeng.tea.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/18.
 */

public class LVAdapter1 extends BaseAdapter {
    private List<Map<String,String>> list;
    private Context context;


    public LVAdapter1(List<Map<String, String>> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item,null);
            holder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            holder.tvOther = (TextView) convertView.findViewById(R.id.tvOther);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
            Map<String,String> map = list.get(position);
            holder.tvTitle.setText(map.get("title"));
            holder.tvOther.setText(map.get("other"));
        Log.i("路径是不是空",map.get("image"));
           // Picasso.with(context).load(map.get("image")).into(holder.ivImage);
        if (!map.get("image").isEmpty()) {
            Picasso.with(context).load(map.get("image")).into(holder.ivImage);

        }
        return convertView;

    }
    class ViewHolder {
        TextView tvTitle;
        TextView tvOther;
        ImageView ivImage;
    }

}

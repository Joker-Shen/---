package com.qianfeng.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.qianfeng.tea.R;
import com.qianfeng.util.Del;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/18.
 */

public class CollectionAdapter extends BaseAdapter {
    private List<Map<String,String>> list;
    private Boolean isMultiSelect;
    private Context context;
    private String id;
    private Del del;

    public CollectionAdapter(List<Map<String,String>> list,Context context, Boolean isMultiSelect) {
        this.list = list;
        this.context=context;
        this.isMultiSelect=isMultiSelect;
        del = new Del();
    }

    public CollectionAdapter(List<Map<String, String>> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder =  null;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.collection_item,null);
            viewHolder.tvOther = (TextView) convertView.findViewById(R.id.collectionOther);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.collectionTitle);
            viewHolder.cbSelect = (CheckBox) convertView.findViewById(R.id.cbSelect);
            convertView.setTag(viewHolder);
        }else {
           viewHolder = (ViewHolder) convertView.getTag();
        }
        if(list.get(position).get("isMultiSelect").equals("true")){
            Log.i("是否可以被点击1",list.get(position).get("isMultiSelect")+"");
            viewHolder.cbSelect.setVisibility(View.VISIBLE);
        }else{
            Log.i("是否可以被点击2",list.get(position).get("isMultiSelect")+"");
            viewHolder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i("ischeck===",isChecked+"");
                    if(isChecked){
                        id = list.get(position).get("id");
                        del.utilDel(id,context);
                    }
                }
            });

            viewHolder.cbSelect.setVisibility(View.GONE);
        }
        viewHolder.tvOther.setText(list.get(position).get("other"));
        viewHolder.tvTitle.setText(list.get(position).get("title"));
        return convertView;
    }
    class ViewHolder{
        TextView tvTitle;
        TextView tvOther;
        CheckBox cbSelect;
    }

}

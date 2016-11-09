package com.qianfeng.fragment;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.tea.R;
import com.qianfeng.tea.WebViewActivity;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AderFragment extends Fragment {

    private ImageView ivAdertPic;
    private TextView tvAderTitle;

    public AderFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Bundle bundle = getArguments();
        Log.i("---------1 ",bundle.toString());
        View view = inflater.inflate(R.layout.fragment_ader,container,false);
        ivAdertPic = (ImageView) view.findViewById(R.id.ivAderPic);
        tvAderTitle = (TextView) view.findViewById(R.id.tvAderTitle);
        Picasso.with(getActivity()).load(bundle.getString("urlPic")).into(ivAdertPic);
        tvAderTitle.setText(bundle.getString("title"));
        ivAdertPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = bundle.getString("id");
                Toast.makeText(getActivity(), "url"+url, Toast.LENGTH_SHORT).show();
                ArrayList<String> list = new ArrayList<String>();
                list.add(bundle.get("title").toString());
                list.add(bundle.get("id").toString());
                list.add(bundle.get("urlPic").toString());
                list.add("广告");
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putStringArrayListExtra("list",list);
                startActivity(intent);
            }
        });
        return view;
    }

}

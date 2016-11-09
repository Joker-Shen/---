package com.qianfeng.tea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CopyRightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_right);
    }
    public void backHome(View view){
        //点到就返回
        this.finish();
    }
}

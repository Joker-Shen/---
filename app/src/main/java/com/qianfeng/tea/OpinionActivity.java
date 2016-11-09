package com.qianfeng.tea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class OpinionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        
    }
    public void backHome(View view){
        //点到就返回
        Toast.makeText(this, "假的_(:з」∠)_", Toast.LENGTH_SHORT).show();
        this.finish();
    }

}

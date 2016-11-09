package com.qianfeng.tea;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/*
* 首页
* */
public class MainActivity extends AppCompatActivity {
    private TextView tvtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //控件
        tvtime = (TextView) findViewById(R.id.tvtime);
        //把数据以键值对形式持久保存
        final SharedPreferences sharedPreferences = getSharedPreferences("Flag", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        //执行
        editor.commit();
        //倒计时3000ms减少1000ms
        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                //开始
                tvtime.setText("剩余时间："+millisUntilFinished/1000+"s");
            }

            @Override
            public void onFinish() {
                tvtime.setText("剩余时间："+0+"s");
                //结束 取出flag并判断是否是第一次
                if(sharedPreferences.getInt("flag",0)==0){
                    editor.putInt("flag",1);
                    editor.commit();
                    Intent intent = new Intent(MainActivity.this,GuideActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }else{
                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
            }
        }.start();
    }
}

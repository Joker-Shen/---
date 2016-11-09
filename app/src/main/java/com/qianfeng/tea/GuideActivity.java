package com.qianfeng.tea;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.adapter.GuideAdapter;

/*
* 引导页面
* */
public class GuideActivity extends AppCompatActivity {
    private ViewPager vpguide;
    private LinearLayout lldot;
    private TextView guidego;
    //存放 dot/pic/imageview
    private ImageView ivdot;
    private ImageView ivpic;
    private ImageView[] dot;
    private ImageView[] pic;
    private GuideAdapter adapter;
    private int [] arrpic = {R.mipmap.slide1,R.mipmap.slide2,R.mipmap.slide3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //控件
        guidego = (TextView) findViewById(R.id.guideGo);
        vpguide = (ViewPager) findViewById(R.id.vpGuide);
        lldot = (LinearLayout) findViewById(R.id.llDot);
        ivdot = new ImageView(this);
        ivpic = new ImageView(this);
        //把图片放入控件&动态生成dot
        dot = new ImageView[arrpic.length];
        pic = new ImageView[arrpic.length];
        for (int i = 0;i < arrpic.length;i ++){
            ImageView ivpic = new ImageView(this);
            ivpic.setScaleType(ImageView.ScaleType.FIT_XY);
            ivpic.setImageResource(arrpic[i]);
            pic[i]=ivpic;
            ImageView ivdot = new ImageView(this);
            ivdot.setImageResource(R.drawable.dot_item);
            //动态添加小圆点 并设置宽高
            ivdot.setTag(i);
            ivdot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vpguide.setCurrentItem(Integer.parseInt(v.getTag().toString()));
                }
            });
            lldot.addView(ivdot);
            ViewGroup.LayoutParams params = ivdot.getLayoutParams();
            params.height = 100;
            params.width = 100;
            //ivdot.setLayoutParams(new LinearLayout.LayoutParams(100,100,1.0f));


            dot[i]=ivdot;
            dot[i].setEnabled(true);
        }
       // dot[1].setEnabled(false);
        //adapter
        adapter = new GuideAdapter(pic);
        vpguide.setAdapter(adapter);
        //监听viewpager
        vpguide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //dot是否可点击&变色
                for (int i = 0;i < arrpic.length;i ++){
                    lldot.getChildAt(i).setEnabled(true);
                }
                lldot.getChildAt(position).setEnabled(false);
                //最后一页 立即体验可点击
                if(position==2){
                    guidego.setVisibility(View.VISIBLE);
                    guidego.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(GuideActivity.this, "立即体验", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(GuideActivity.this,HomeActivity.class);
                            startActivity(intent);
                            GuideActivity.this.finish();
                        }
                    });
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

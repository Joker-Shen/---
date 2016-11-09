package com.qianfeng.tea;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qianfeng.adapter.CollectionAdapter;
import com.qianfeng.adapter.HomeFragmentAdapter;
import com.qianfeng.fragment.FirstFragment;
import com.qianfeng.fragment.FiveFragment;
import com.qianfeng.fragment.ForthFragment;
import com.qianfeng.fragment.SecondFragment;
import com.qianfeng.fragment.SixFragment;
import com.qianfeng.fragment.ThirdFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity{

    private String[] menu = {"头条","百科","咨询","经营","数据"};
    private TabLayout tabLayout;
    private ViewPager vpMain;
    private DrawerLayout drawerLayout;
    private List<Fragment> listFragment;
    private List<String> listTitle;
    private HomeFragmentAdapter adapter;
    private EditText editText;
    //用来判断是否确认退出
    private int flag1 = 1;
    private int flag2 = 1;
    private long timeOnce = 0;
    private long timeSeconds = 0;
    //判断是否继续滑动
    private int vpPosition;
    private float leftX;
    private float rightX;
    //动画
    private RelativeLayout searchAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        listFragment = new ArrayList<>();
        listTitle = new ArrayList<>();

        //控件
        editText = (EditText) findViewById(R.id.etSearch);
        vpMain = (ViewPager) findViewById(R.id.vpMain);
        tabLayout = (TabLayout) findViewById(R.id.tabmenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_first);
        searchAnimation = (RelativeLayout) findViewById(R.id.searchAnimation);
        //上边的标签
        for (int i = 0;i<menu.length;i++){
            listTitle.add(menu[i]);
            tabLayout.addTab(tabLayout.newTab().setText(menu[i]));
        }
        /*tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.more));
        listTitle.add("menu");*/
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //
        listFragment.add(new FirstFragment());
        listFragment.add(new SecondFragment());
        listFragment.add(new ThirdFragment());
        listFragment.add(new ForthFragment());
        listFragment.add(new FiveFragment());
        listFragment.add(new SixFragment());
        //
        adapter = new HomeFragmentAdapter(getSupportFragmentManager(),listFragment,listTitle);
        vpMain.setAdapter(adapter);
        tabLayout.setupWithViewPager(vpMain);
        //

        //监听点击判断是否是侧滑
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //判断是否点的是侧滑菜单
                if (tab.getPosition()<menu.length){
                    Toast.makeText(HomeActivity.this, ""+menu[tab.getPosition()], Toast.LENGTH_SHORT).show();
                    vpMain.setCurrentItem(tab.getPosition());
                }/*else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }*/
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("position====",position+"");
                Log.i("positionOffset====",positionOffset+"");
                Log.i("positionOffsetPixels=",positionOffsetPixels+"");
                vpPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                //循环滑动
                /*if(position==5){
                    vpMain.setCurrentItem(0);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i("state=",state+"");
            }
        });
        vpMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    leftX = event.getX();
                }else{
                    rightX = event.getX();
                    if (vpPosition==4&&rightX-leftX>50){
                      drawerLayout.openDrawer(Gravity.RIGHT);
                    }
                }
                return false;
            }
        });
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.i("_(:з」∠)_",slideOffset+"");
                if (vpPosition==5){
                   vpMain.setCurrentItem(4);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }
    //监听侧边栏
    public void menu(View view){
        drawerLayout.openDrawer(Gravity.RIGHT);
    }
    public void backHome(View view){
        //两个钮点到就关闭侧边栏
        drawerLayout.closeDrawers();
    }
    public void copyRight(View view){
        //跳到版权
        Intent intent = new Intent(this,CopyRightActivity.class);
        startActivity(intent);
    }
    public void collection(View view){
        //跳到收藏夹
        Intent intent = new Intent(this,CollectionActivity.class);
        startActivity(intent);
    }
    public void opinion(View view){
        //跳到提供建议
        Intent intent = new Intent(this,OpinionActivity.class);
        startActivity(intent);
    }
    public void search(View view){
        //跳到搜索
        if(TextUtils.isEmpty(editText.getText())){
            TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF,-0.01f,
                    Animation.RELATIVE_TO_SELF,0.01f,
                    Animation.RELATIVE_TO_SELF,-0.02f,
                    Animation.RELATIVE_TO_SELF,0.02f
                    );
            animation.setDuration(100);
            searchAnimation.startAnimation(animation);
        }else {

            Intent intent = new Intent(this,SearchActivity.class);
            intent.putExtra("search",editText.getText().toString());
            startActivity(intent);
        }
    }
    public void hot(View view){
        Intent intent = new Intent(this,SearchActivity.class);
        intent.putExtra("search","茶");
        startActivity(intent);
    }

    //完结撒花 ~\(≧▽≦)/~啦啦啦
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(drawerLayout.isDrawerOpen(findViewById(R.id.llMenu))){
            drawerLayout.closeDrawers();
            return true;
        }else{

            if(keyCode==event.KEYCODE_BACK){
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //监听两次点击之间的系统时间差  用Math.abs 获取绝对值 因为不一定哪个是先点哪个是后点
                if(flag1==1){
                    timeOnce = System.currentTimeMillis();
                    flag1=2;
                    if(Math.abs(timeSeconds-timeOnce)<1000&&flag2==3){
                        this.finish();
                    }
                    //flag2=3表示不是第一次点击返回键 bu
                    flag2=3;
                    return true;
                }else if(flag1==2){
                    timeSeconds = System.currentTimeMillis();
                    flag1=1;
                    if(Math.abs(timeSeconds-timeOnce)<1000&&flag2==3){
                        this.finish();
                    }
                    return true;
                }

            }
        }
        return super.onKeyDown(keyCode, event);
    }
}

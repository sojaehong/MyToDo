package com.ssostudio.mytodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.ssostudio.mytodo.adapter.MainViewPagerAdapter;
import com.ssostudio.mytodo.utility.AppUtility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Activity _activity;
    public static int deviceWidth;
    public static int deviceHeight;
    private static int tabPosition = 0;

    private ViewPager viewPager;
    private FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        _activity = this;

        getDeviceSize();
        setViewPager();
        setTabLayout();
        setAddBtn();
    }

    private void setAddBtn() {
        addBtn = findViewById(R.id.add_button);
        addBtn.setOnClickListener(this);
    }

    private void setTabLayout() {
        TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
                addBtnHideShowCheck();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setViewPager() {
        viewPager = findViewById(R.id.main_viewpager);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void getDeviceSize() {
        int[] deviceSize = AppUtility.getDeviceSize(getApplicationContext());
        deviceWidth = deviceSize[0];
        deviceHeight = deviceSize[1];
    }

    private void addBtnHideShowCheck(){
        if (tabPosition == 4) {
            onAddBtnHide();
        }else{
            onAddBtnShow();
        }
    }

    private void onAddBtnHide(){
        addBtn.hide();
    }

    private void onAddBtnShow(){
        addBtn.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

        }
    }
}

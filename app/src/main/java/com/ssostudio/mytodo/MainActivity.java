package com.ssostudio.mytodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.ssostudio.mytodo.adapter.MainViewPagerAdapter;
import com.ssostudio.mytodo.utility.AppUtility;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Activity _activity;
    public static int deviceWidth;
    public static int deviceHeight;

    private static int tabPosition = 0;
    public static boolean isPremium = false;
    public static boolean isFree = false;

    private ViewPager viewPager;
    private AdView mAdView;
    private FrameLayout adContainerView;


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
        setAdMob();
    }

    private void setTabLayout() {
        TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
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
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
    }

    private void getDeviceSize() {
        int[] deviceSize = AppUtility.getDeviceSize(getApplicationContext());
        deviceWidth = deviceSize[0];
        deviceHeight = deviceSize[1];
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

        }
    }

    private void setAdMob() {
        //        if (isPremium || isFree)
        //            return;
        setAdMobLoad();
        setAdMobUI();
    }

    private void setAdMobUI() {
        LinearLayout mainView = findViewById(R.id.main_view);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(deviceWidth, deviceHeight);
        lp.addRule(RelativeLayout.ABOVE, R.id.adFrame);
        mainView.setLayoutParams(lp);
    }

    private void setAdMobLoad() {
        //admob setting
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adContainerView = findViewById(R.id.adFrame);

        mAdView = new AdView(this);
        // banner test
        mAdView.setAdUnitId(getString(R.string.test_banner_ad_unit_id));

        adContainerView.addView(mAdView);

        // Ad Test Device setting
        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("BDEFD70AD95EF10E88425F412EEC574E"))
                        .build());
        // 앱 등록시 제거 필요

        AdRequest adRequest = new AdRequest.Builder().build();

        AdSize adSize = getAdSize();
        mAdView.setAdSize(adSize);
        mAdView.loadAd(adRequest);
    }

    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }
}

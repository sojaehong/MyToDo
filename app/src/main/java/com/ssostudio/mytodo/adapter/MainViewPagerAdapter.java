package com.ssostudio.mytodo.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ssostudio.mytodo.fragment.BucketListFragment;
import com.ssostudio.mytodo.fragment.MonthlyFragment;
import com.ssostudio.mytodo.fragment.SettingFragment;
import com.ssostudio.mytodo.fragment.TodayFragment;
import com.ssostudio.mytodo.fragment.WeeklyFragment;
import com.ssostudio.mytodo.fragment.YearsFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    public MainViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public String[] itemTitle = {"오늘", "주간", "월간", "연간", "설정"};

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TodayFragment();
            case 1:
                return new WeeklyFragment();
            case 2:
                return new MonthlyFragment();
            case 3:
                return new YearsFragment();
            case 4:
                return new SettingFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return itemTitle.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return itemTitle[position];
    }
}

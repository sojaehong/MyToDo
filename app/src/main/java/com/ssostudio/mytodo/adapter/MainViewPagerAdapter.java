package com.ssostudio.mytodo.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.fragment.MonthlyFragment;
import com.ssostudio.mytodo.fragment.SettingFragment;
import com.ssostudio.mytodo.fragment.TodayFragment;
import com.ssostudio.mytodo.fragment.WeeklyFragment;
import com.ssostudio.mytodo.fragment.YearsFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    private Context _context;

    public MainViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        _context = context;
    }

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
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return _context.getString(R.string.today);
            case 1:
                return _context.getString(R.string.this_week);
            case 2:
                return _context.getString(R.string.this_month);
            case 3:
                return _context.getString(R.string.this_year);
            case 4:
                return "설정";

        }
        return super.getPageTitle(position);
    }

}

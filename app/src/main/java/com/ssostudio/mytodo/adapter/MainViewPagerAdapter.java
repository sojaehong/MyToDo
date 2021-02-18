package com.ssostudio.mytodo.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.fragment.BucketListFragment;
import com.ssostudio.mytodo.fragment.CalendarFragment;
import com.ssostudio.mytodo.fragment.MonthFragment;
import com.ssostudio.mytodo.fragment.SettingFragment;
import com.ssostudio.mytodo.fragment.TodayFragment;
import com.ssostudio.mytodo.fragment.YearFragment;

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
                return new CalendarFragment();
            case 2:
                return new MonthFragment();
            case 3:
                return new YearFragment();
            case 4:
                return new BucketListFragment();
            case 5:
                return new SettingFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return _context.getString(R.string.today);
            case 1:
                return "달력";
            case 2:
                return "월간계획";
            case 3:
                return "연간계획";
            case 4:
                return "버킷리스트";
            case 5:
                return "설정";
        }
        return super.getPageTitle(position);
    }

}

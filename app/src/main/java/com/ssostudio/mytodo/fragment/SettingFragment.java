package com.ssostudio.mytodo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ssostudio.mytodo.R;

public class SettingFragment extends Fragment {

    private static View view;
    private Context _context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        _context = getContext();

        return view;
    }

}

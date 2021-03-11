package com.ssostudio.mytodo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.utility.OtherApps;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private static View view;
    private Context _context;
    private MaterialButton otherAppsButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        _context = getContext();

        init();

        return view;
    }

    private void init() {
        otherAppsButton = view.findViewById(R.id.other_apps_button);
        otherAppsButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.other_apps_button:
                onOtherAppsBtnClick();
                break;
        }
    }

    private void onOtherAppsBtnClick(){
        OtherApps.onOtherAppsShow(_context);
    }
}

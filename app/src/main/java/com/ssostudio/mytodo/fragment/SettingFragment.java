package com.ssostudio.mytodo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.ssostudio.mytodo.BucketListActivity;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.ToDoActivity;
import com.ssostudio.mytodo.utility.OtherApps;

public class SettingFragment extends Fragment implements View.OnClickListener {
    private static View view;
    private Context _context;
    private MaterialButton otherAppsButton, bucketListButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        _context = getContext();

        init();

        return view;
    }

    private void init() {
        otherAppsButton = view.findViewById(R.id.other_apps_button);
        otherAppsButton.setOnClickListener(this);

        bucketListButton = view.findViewById(R.id.bucket_list_button);
        bucketListButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.other_apps_button:
                onOtherAppsBtnClick();
                break;
            case R.id.bucket_list_button:
                onBucketListBtnClick();
        }
    }

    private void onBucketListBtnClick() {
        Intent intent = new Intent(getActivity(), BucketListActivity.class);
        startActivity(intent);
    }

    private void onOtherAppsBtnClick(){
        OtherApps.onOtherAppsShow(_context);
    }
}

package com.ssostudio.mytodo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.dialog.ToDoAddDialog;

public class TodayFragment extends Fragment implements View.OnClickListener {

    private static View view;
    private Context _context;
    private FloatingActionButton addBtn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, container, false);

        init();

        return view;
    }

    private void init() {
        _context = getContext();

        setAddBtn();
    }

    private void setAddBtn() {
        addBtn = view.findViewById(R.id.add_button);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_button:
                onAddButtonClick();
                break;
        }
    }

    private void onAddButtonClick() {
        new ToDoAddDialog(_context).onShowDialog(0);
    }
}

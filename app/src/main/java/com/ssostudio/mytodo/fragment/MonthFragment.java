package com.ssostudio.mytodo.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssostudio.mytodo.R;
import com.ssostudio.mytodo.adapter.ToDoListVIewAdapter;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.dialog.ToDoAddDialog;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.model.ToDoModelList;
import com.ssostudio.mytodo.utility.DateManager;

import java.util.ArrayList;
import java.util.Map;

public class MonthFragment extends Fragment implements View.OnClickListener {
    private static View view;
    private static Context _context;
    private FloatingActionButton addBtn;
    private ListView listView;
    private TextView statisticsTitleTextView, contentTextView;
    private ProgressBar toDoProgressBar;
    private ImageView beforeImageView, nextImageView;
    private ToDoListVIewAdapter adapter;

    private static int _year;
    private static int _month;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_month, container, false);
        _context = getContext();
        init();

        return view;
    }

    private void init() {
        setThisMonth();
        setListVIew();
        setAddBtn();
        setBeforeImageView();
        setNextImageView();
        setStatisticsView();
    }

    private void setThisMonth() {
        int[] date = DateManager.timestampToIntArray(DateManager.getTimestamp());
        _year = date[0];
        _month = date[1];
    }

    private void setAddBtn() {
        addBtn = view.findViewById(R.id.add_button);
        addBtn.setOnClickListener(this);
    }

    private void setBeforeImageView() {
        beforeImageView = view.findViewById(R.id.before_image);
        beforeImageView.setOnClickListener(this);
        beforeImageView.setVisibility(View.VISIBLE);
    }

    private void setNextImageView() {
        nextImageView = view.findViewById(R.id.next_image);
        nextImageView.setOnClickListener(this);
        nextImageView.setVisibility(View.VISIBLE);
    }

    private void setListVIew() {
        if (view == null)
            return;

        int lastPosition = 0;
        int top = 0;

        new DBManager(_context).selectMonthToDo(_year, _month);

        listView = view.findViewById(R.id.to_do_list);

        lastPosition = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());

        adapter = new ToDoListVIewAdapter(_context, ToDoModelList.monthToDoModels);
        listView.setAdapter(adapter);

        listView.setSelectionFromTop(lastPosition, top);
    }

    public void listRefresh() {
        setListVIew();
    }

    public void setStatisticsView() {
        if (view == null)
            return;

        Map<String, ArrayList<ToDoModel>> toDoModels = ToDoModelList.monthToDoModels;
        int completed = toDoModels.get("completedList").size();
        int incompleted = toDoModels.get("failList").size();
        int total = completed + incompleted;
        double percent = (double) completed / (double) total * 100.0;

        String titleText = _month + "/" + _year;

        String contentText = _context.getString(R.string.total) + ": " + total + " | " + _context.getString(R.string.in_progress) + ": "
                + incompleted + " | " + _context.getString(R.string.completed_text) + ": " + completed;

        toDoProgressBar = view.findViewById(R.id.to_do_progress_Bar);

        int color = 0;
        if (completed == total) {
            color = _context.getResources().getColor(R.color.orientarBlue);
        } else {
            color = _context.getResources().getColor(R.color.bRed);
        }

        toDoProgressBar.setProgressTintList(ColorStateList.valueOf(color));
        toDoProgressBar.setProgress((int) percent);

        statisticsTitleTextView = view.findViewById(R.id.statistics_title_text);
        statisticsTitleTextView.setText(titleText);

        contentTextView = view.findViewById(R.id.content_text);
        contentTextView.setText(contentText);
    }

    private void viewRefresh() {
        listRefresh();
        setStatisticsView();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_button:
                onAddButtonClick();
                break;
            case R.id.before_image:
                onBeforeImageClick();
                break;
            case R.id.next_image:
                onNextImageClick();
                break;
        }

    }

    private void onAddButtonClick() {
        new ToDoAddDialog(_context).onShowDialog(3, _year, _month);
    }

    private void onBeforeImageClick() {
        if (_month == 1) {
            _year = --_year;
            _month = 12;
        } else {
            _month = --_month;
        }
        viewRefresh();
    }

    private void onNextImageClick() {
        if (_month == 12) {
            _year = ++_year;
            _month = 1;
        } else {
            _month = ++_month;
        }
        viewRefresh();
    }

    @Override
    public void onResume() {
        viewRefresh();
        super.onResume();
    }
}

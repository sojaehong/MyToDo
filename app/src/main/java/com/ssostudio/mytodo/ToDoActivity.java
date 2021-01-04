package com.ssostudio.mytodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssostudio.mytodo.adapter.ToDoListVIewAdapter;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.dialog.ToDoAddDialog;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.model.ToDoModelList;
import com.ssostudio.mytodo.utility.DateManager;

import java.util.ArrayList;
import java.util.Map;

public class ToDoActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] _dates;
    private ListView listView;
    private ToDoListVIewAdapter adapter;
    private MaterialButton closeBtn, beforeBtn, nextBtn;
    private FloatingActionButton addBtn;
    private TextView statisticsTitleTextView, contentTextView;
    private ProgressBar toDoProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        _dates = intent.getIntArrayExtra("dates");

        new DBManager(getApplicationContext()).selectToDo(DateManager.intArrayToTimestamp(_dates));

        setAddBtn();
        setToDoListView();
        setCloseBtn();
        setBeforeBtn();
        setNextBtn();
        setSimpleStatisticsVIew();
    }

    private void setAddBtn() {
        addBtn = findViewById(R.id.add_button);
        addBtn.setOnClickListener(this);
    }

    public void refresh() {
        if (getApplicationContext() == null)
            return;

        new DBManager(getApplicationContext()).selectToDo(DateManager.intArrayToTimestamp(_dates));
        setToDoListView();
    }

    public void setSimpleStatisticsVIew() {
        long todayTimestamp = DateManager.getTimestamp();
        long selectTimestamp = DateManager.intArrayToTimestamp(_dates);

        Map<String, ArrayList<ToDoModel>> toDoModels = ToDoModelList.selectToDoModels;
        int completed = toDoModels.get("completedList").size();
        int incompleted = toDoModels.get("failList").size();
        int total = completed + incompleted;
        double percent = (double) completed / (double) total * 100.0;

        long calDate = DateManager.calDateBetweenAandB(todayTimestamp, selectTimestamp);

        String titleText = DateManager.dateTimeZoneFullFormat(_dates);

        String contentText = getString(R.string.total) + ": " + total + " | " + getString(R.string.in_progress) + ": "
                + incompleted + " | " + getString(R.string.completed_text) + ": " + completed;

        toDoProgressBar = findViewById(R.id.to_do_progress_Bar);

        int color = 0;
        if (completed == total) {
            color = getResources().getColor(R.color.orientarBlue);
        } else {
            color = getResources().getColor(R.color.bRed);
        }

        toDoProgressBar.setProgressTintList(ColorStateList.valueOf(color));
        toDoProgressBar.setProgress((int) percent);

        contentTextView = findViewById(R.id.content_text);
        contentTextView.setText(contentText);

        statisticsTitleTextView = findViewById(R.id.statistics_title_text);

        String calText = "";

        if (calDate == 0) {
            calText = getString(R.string.today);
        } else {
            if (todayTimestamp > selectTimestamp) {
                if (calDate == 1)
                    calText = getString(R.string.yesterday);
                else
                    calText = calDate + getString(R.string.days_ago);
            } else {
                if (calDate == 1)
                    calText = getString(R.string.tomorrow);
                else
                    calText = calDate + getString(R.string.days_later);
            }
        }

        calText = "(" + calText + ")";

        titleText = titleText + " " + calText;

        statisticsTitleTextView.setText(titleText);

    }

    private void setNextBtn() {
        nextBtn = findViewById(R.id.next_date_button);
        nextBtn.setOnClickListener(this);
    }

    private void setBeforeBtn() {
        beforeBtn = findViewById(R.id.before_date_button);
        beforeBtn.setOnClickListener(this);
    }

    private void setCloseBtn() {
        closeBtn = findViewById(R.id.close_button);
        closeBtn.setOnClickListener(this);
    }

    private void setToDoListView() {
        int lastPosition = 0;
        int top = 0;

        listView = findViewById(R.id.to_do_list);

        lastPosition = listView.getFirstVisiblePosition();
        View v = listView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - listView.getPaddingTop());

        adapter = new ToDoListVIewAdapter(this, ToDoModelList.selectToDoModels);
        listView.setAdapter(adapter);

        listView.setSelectionFromTop(lastPosition, top);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_button:
                onCloseImageViewClick();
                break;
            case R.id.before_date_button:
                onBeforeImageViewClick();
                break;
            case R.id.next_date_button:
                onNextImageViewClick();
                break;
            case R.id.add_button:
                onAddBtnClick();
                break;
        }
    }

    private void onAddBtnClick() {
        new ToDoAddDialog(this).onShowDialog(0, DateManager.intArrayToTimestamp(_dates), false);
    }

    private void onNextImageViewClick() {
        dateChange(1);
    }

    private void onBeforeImageViewClick() {
        dateChange(-1);
    }

    private void onCloseImageViewClick() {
        finish();
    }

    private void dateChange(int changeDate) {
        long date = DateManager.changeDate(_dates, changeDate);
        _dates = DateManager.timestampToIntArray(date);
        refresh();
        setSimpleStatisticsVIew();
    }

    @Override
    protected void onResume() {
        refresh();
        setSimpleStatisticsVIew();
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
    }
}

package com.ssostudio.mytodo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ssostudio.mytodo.adapter.ToDoListVIewAdapter;
import com.ssostudio.mytodo.dbhelper.DBManager;
import com.ssostudio.mytodo.dialog.ToDoAddDialog;
import com.ssostudio.mytodo.fragment.CalendarFragment;
import com.ssostudio.mytodo.fragment.TodayFragment;
import com.ssostudio.mytodo.model.ToDoModel;
import com.ssostudio.mytodo.model.ToDoModelList;
import com.ssostudio.mytodo.utility.DateManager;

import java.util.ArrayList;
import java.util.Map;

public class ToDoActivity extends AppCompatActivity implements View.OnClickListener {

    private int[] _dates;
    private TextView dateTextView;
    private ListView listView;
    private ToDoListVIewAdapter adapter;
    private ImageView closeImageView, beforeImageView, nextImageView;
    private FloatingActionButton addBtn;
    private TextView statisticsTitleTextView, contentTextView;
    private ProgressBar toDoProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        init();
    }

    private void init(){
        Intent intent = getIntent();
        _dates = intent.getIntArrayExtra("dates");

        new DBManager(getApplicationContext()).selectToDo(DateManager.intArrayToTimestamp(_dates));

        setAddBtn();
        setDateTextView();
        setToDoListView();
        setCloseImageView();
        setBeforeImageView();
        setNextImageView();
        setSimpleStatisticsVIew();
    }

    private void setAddBtn() {
        addBtn = findViewById(R.id.add_button);
        addBtn.setOnClickListener(this);
    }

    public void refresh(){
        if (getApplicationContext() == null)
            return;

        new DBManager(getApplicationContext()).selectToDo(DateManager.intArrayToTimestamp(_dates));
        setDateTextView();
        setToDoListView();
    }

    public void setSimpleStatisticsVIew() {
        Map<String, ArrayList<ToDoModel>> toDoModels = ToDoModelList.selectToDoModels;
        int completed = toDoModels.get("completedList").size();
        int incompleted = toDoModels.get("failList").size();
        int total = completed + incompleted;
        double percent = (double) completed / (double) total * 100.0;

        int[] todayDates = DateManager.timestampToIntArray(DateManager.getTimestamp());

        String titleText = "";
        if (_dates[0] == todayDates[0] && _dates[1] == todayDates[1] && _dates[2] == todayDates[2]){
            titleText = getString(R.string.today);
        }else{
            titleText = DateManager.dateTimeZoneFullFormat(_dates);
        }

        String contentText = getString(R.string.total) + ": " + total + " | " + getString(R.string.in_progress) + ": "
                + incompleted + " | " + getString(R.string.completed_text) + ": " + completed;

        toDoProgressBar = findViewById(R.id.to_do_progress_Bar);

        int color = 0;
        if(completed == total){
            color = getResources().getColor(R.color.orientarBlue);
        }else{
            color = getResources().getColor(R.color.bRed);
        }

        toDoProgressBar.setProgressTintList(ColorStateList.valueOf(color));
        toDoProgressBar.setProgress((int) percent);

        statisticsTitleTextView = findViewById(R.id.statistics_title_text);
        statisticsTitleTextView.setText(titleText);

        contentTextView = findViewById(R.id.content_text);
        contentTextView.setText(contentText);
    }

    private void setNextImageView() {
        nextImageView = findViewById(R.id.next_date_image);
        nextImageView.setOnClickListener(this);
    }

    private void setBeforeImageView() {
        beforeImageView = findViewById(R.id.before_date_image);
        beforeImageView.setOnClickListener(this);
    }

    private void setCloseImageView() {
        closeImageView = findViewById(R.id.close_image);
        closeImageView.setOnClickListener(this);
    }

    private void setDateTextView(){
        dateTextView = findViewById(R.id.date_text);
        dateTextView.setText(DateManager.dateTimeZoneFormat(_dates));
    }

    private void setToDoListView(){
        listView = findViewById(R.id.to_do_list);
        adapter = new ToDoListVIewAdapter(this, ToDoModelList.selectToDoModels);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.close_image:
                onCloseImageViewClick();
                break;
            case R.id.before_date_image:
                onBeforeImageViewClick();
                break;
            case R.id.next_date_image:
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

    private void dateChange(int changeDate){
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
